package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guzman.cmsshoppingcart.model.CategoryRepository;
import com.guzman.cmsshoppingcart.model.ProductRepository;
import com.guzman.cmsshoppingcart.model.data.Category;
import com.guzman.cmsshoppingcart.model.data.Product;

/*
 * why categories controller?
 * 
 * Despite it is displaying products its URL is '/categories'. 
 * Being so, it makes more sense to call it CategoriesController 
 * instead of ProductsController.
 * 
 */

@Controller
@RequestMapping("/category")
public class CategoriesController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/{slug}")
	public String category(@PathVariable String slug,  
							Model theModel, 
							@RequestParam(value="page", required = false) Integer thePage) {
		
		// Pagination
		int perPage = 6;
		int page = (thePage != null) ? thePage.intValue() : 0;
		Pageable pageable = PageRequest.of(page, perPage);
		
		long count = 0L;
		
		if (slug.equals("all")) {
			
			Page<Product> theProducts = productRepository.findAll(pageable);
		
			count = productRepository.count();
			
			theModel.addAttribute("products", theProducts);
		} else {
			
			Category theCategory = categoryRepository.findBySlug(slug);
			
			if (theCategory == null) {
				return "redirect:/";
			}
			
			int theCategoryId = theCategory.getId();
			String theCategoryName = theCategory.getName();
			
			List<Product> products = productRepository.findAllByCategoryId(Integer.toString(theCategoryId), pageable);
			
			count = productRepository.countByCategoryId(Integer.toString(theCategoryId));
			
			theModel.addAttribute("products", products);
			theModel.addAttribute("categoryName", theCategoryName);
		}

		//pagination
		double pageCount = Math.ceil((double)count/(double)perPage);
		
		theModel.addAttribute("pageCount", (int) pageCount); 
		theModel.addAttribute("perPage", perPage); 
		theModel.addAttribute("count", count); 
		theModel.addAttribute("page", page); 
		
		return "products";
	}
}
