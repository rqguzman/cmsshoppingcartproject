package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	/*
	 * the '@ModelAttribute' it's optional.
	 * It is only useful if, for instance, you need to create an alias for the 
	 * thymeleaf object (th:object) of the Html tag <form> at the respective 
	 * view (add.html, in this case).
	 * It would look like this:
	 * 
	 * public String add(@ModelAttribute("admin_cat") Category theCategory) {
	 * 
	 * and at Html file:
	 * 
	 * <form action="#" th:action="@{/admin/categories/add}" 
	 * 		th:object="${admin_cat}" method="post">
	 * 
	 * There is a much better use for this anottation though:
	 * If you need an attribute to be available throughout the entire class,
	 * you might create a class attribute with this annotation:
	 * 
	 * 	@ModelAttribute("category")
	 * 	private Category getCategory() {
	 * 		return new Category();
	 * 	}
	 * 
	 */
	@GetMapping("/add")
	public String add(@ModelAttribute Category theCategory) {
				
		return "admin/categories/add";
	}
}
