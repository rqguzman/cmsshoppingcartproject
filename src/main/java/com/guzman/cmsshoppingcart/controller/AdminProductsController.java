package com.guzman.cmsshoppingcart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String index(Model theModel) {
		
		List<Product> theProducts = productRepository.findAll();
		List<Category> theCategories = categoryRepository.findAll();		
		HashMap<Integer, String> categoriesWithNames = new HashMap<>();
		
		for (Category category : theCategories) {
			categoriesWithNames.put(category.getId(), category.getName());
		}
		
		theModel.addAttribute("products", theProducts);
		theModel.addAttribute("categoriesWithNames", categoriesWithNames);
		
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
		
		byte[ ] bytes = file.getBytes();
		
		String filename = file.getOriginalFilename();
		
		File path = new File("src" + File.separator + "main" 
								+ File.separator + "resources" 
								+ File.separator + "static" 
								+ File.separator + "media" 
								+ File.separator + filename);
		
		Path filepath = Paths.get(path.toString());
		
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
}
