package com.guzman.cmsshoppingcart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guzman.cmsshoppingcart.model.CategoryRepository;
import com.guzman.cmsshoppingcart.model.ProductRepository;
import com.guzman.cmsshoppingcart.model.data.Category;
import com.guzman.cmsshoppingcart.model.data.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(Model theModel, @RequestParam(value="page", required = false) Integer thePage) {
		
		// Pagination
		int perPage = 3;
		int page = (thePage != null) ? thePage.intValue() : 0;
		Pageable pageable = PageRequest.of(page, perPage);
		
		
		Page<Product> theProducts = productRepository.findAll(pageable);
		List<Category> theCategories = categoryRepository.findAll();		
		HashMap<Integer, String> categoriesWithNames = new HashMap<>();
		
		for (Category category : theCategories) {
			categoriesWithNames.put(category.getId(), category.getName());
		}
		
		theModel.addAttribute("products", theProducts);
		theModel.addAttribute("categoriesWithNames", categoriesWithNames);
		
		//pagination
		long count = productRepository.count();// counts how many products
		double pageCount = Math.ceil((double)count/(double)perPage);
		
		theModel.addAttribute("pageCount", (int) pageCount); 
		theModel.addAttribute("perPage", perPage); 
		theModel.addAttribute("count", count); 
		theModel.addAttribute("page", page); 
		
		return "admin/products/index";
	}
	
	@GetMapping("/add")
	public String add(Product theProduct, Model theModel) {
		
		List<Category> theCategories = categoryRepository.findAll();
		theModel.addAttribute("categories", theCategories);
		
		return "admin/products/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Product theProduct,
						BindingResult theResult,
						MultipartFile file , 
						RedirectAttributes theAttributes,
						Model theModel) throws IOException {

		List<Category> theCategories = categoryRepository.findAll();
		
		if (theResult.hasErrors()) {
			theModel.addAttribute("categories", theCategories);
			return "admin/products/add";
		}

		// DEALING WITH THE IMAGE FILE
		boolean theFileOK = false;
		
		byte[] bytes = file.getBytes();
		
		String filename = file.getOriginalFilename();
		
		Path filepath = Paths.get("src/main/resources/static/media/" + filename);
		
		if (filename.endsWith("jpg") || filename.endsWith("png")) {
			theFileOK = true;
		}
		
		theAttributes.addFlashAttribute("message", "Product added");
		theAttributes.addFlashAttribute("alertClass", "alert-success");

		String theSlug = theProduct.getName().toLowerCase().replace(" ", "-");
		
		Product theProductExists = productRepository.findBySlug(theSlug);
		
		if (!theFileOK) {
			
			theAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("product", theProduct);
			
		} else if (theProductExists != null) {			
			
			theAttributes.addFlashAttribute("message", "Product exists, please choose another one.");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("product", theProduct);
			
		} else {
			
			theProduct.setSlug(theSlug);
			theProduct.setImage(filename);
			productRepository.save(theProduct);
			
			Files.write(filepath, bytes);
		}

		return "redirect:/admin/products/add";
	}
	
	@GetMapping("/edit/{theId}")
	public String edit(@PathVariable int theId, Model theModel) {
		
		Product theProduct = productRepository.getOne(theId);
		
		List<Category> theCategories = categoryRepository.findAll();
		
		theModel.addAttribute("product", theProduct);
		theModel.addAttribute("categories", theCategories);
		
		return "admin/products/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Product theProduct,
						BindingResult theResult,
						MultipartFile file , 
						RedirectAttributes theAttributes,
						Model theModel) throws IOException {

		Product currentProduct = productRepository.getOne(theProduct.getId());
		
		List<Category> theCategories = categoryRepository.findAll();
		
		if (theResult.hasErrors()) {
			theModel.addAttribute("productName", currentProduct.getName());
			theModel.addAttribute("categories", theCategories);
			return "admin/products/edit";
		}

		// DEALING WITH THE IMAGE FILE
		boolean theFileOK = false;
		
		byte[] bytes = file.getBytes();
		
		String filename = file.getOriginalFilename();
				
		Path filepath = Paths.get("src/main/resources/static/media/" + filename);
		
		if (!file.isEmpty()) {

			if (filename.endsWith("jpg") || filename.endsWith("png")) {
				theFileOK = true;
			}

		} else {

			theFileOK = true;

		}
		
		
		theAttributes.addFlashAttribute("message", "Product edited");
		theAttributes.addFlashAttribute("alertClass", "alert-success");

		String theSlug = theProduct.getName().toLowerCase().replace(" ", "-");
		
		Product theProductExists = productRepository.findBySlugAndIdNot(theSlug, theProduct.getId());
		
		if (!theFileOK) {
			
			theAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("product", theProduct);
			
		} else if (theProductExists != null) {			
			
			theAttributes.addFlashAttribute("message", "Product exists, please choose another one.");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("product", theProduct);
			
		} else {
			
			theProduct.setSlug(theSlug);
			
			if (!file.isEmpty()) {

				Path filepath2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(filepath2);
				theProduct.setImage(filename);
				Files.write(filepath, bytes);

			} else {

				theProduct.setImage(currentProduct.getImage());

			}
			
			productRepository.save(theProduct);
			
		}

		return "redirect:/admin/products/edit/" + theProduct.getId();
	}
	
	@GetMapping("/delete/{theId}")
	public String delete(@PathVariable int theId, RedirectAttributes theAttributes) throws IOException {
		
		Product theProduct = productRepository.getOne(theId);
		
		Product currentProduct = productRepository.getOne(theProduct.getId());
		
		Path filepath2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
		Files.delete(filepath2);
		productRepository.deleteById(theId);
		
		theAttributes.addFlashAttribute("message", "Product deleted");
		theAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/products";
	}
}
