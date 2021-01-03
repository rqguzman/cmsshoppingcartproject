package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guzman.cmsshoppingcart.model.CategoryRepository;
import com.guzman.cmsshoppingcart.model.data.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model theModel) {
		
		List<Category> categories = categoryRepository.findAll();
		
		theModel.addAttribute("categories", categories);
		
		return "admin/categories/index";
	}
}
