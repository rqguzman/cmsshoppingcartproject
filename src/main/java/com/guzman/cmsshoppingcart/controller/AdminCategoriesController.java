package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guzman.cmsshoppingcart.model.CategoryRepository;
import com.guzman.cmsshoppingcart.model.data.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public String index(Model theModel) {
		
		List<Category> categories = categoryRepository.findAllByOrderBySortingAsc();
		
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
	
	@PostMapping("/add")
	public String add(@Valid Category theCategory, BindingResult theResult, RedirectAttributes theAttributes, Model theModel) {

		if (theResult.hasErrors()) {
			return "admin/categories/add";
		}

		theAttributes.addFlashAttribute("message", "Category added");
		theAttributes.addFlashAttribute("alertClass", "alert-success");

		String theSlug = theCategory.getName().toLowerCase().replace(" ", "-");
		
		Category categoryExists = categoryRepository.findByName(theCategory.getName());
		
		if (categoryExists != null) {			
			theAttributes.addFlashAttribute("message", "Category exists, please choose another one.");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("categoryInfo", theCategory);
			
		} else {
			theCategory.setSlug(theSlug);
			theCategory.setSorting(100);
			
			categoryRepository.save(theCategory);
		}

		return "redirect:/admin/categories/add";
	}
	
	@GetMapping("/edit/{theId}")
	public String edit(@PathVariable int theId, Model theModel) {
		
		Category theCategory = categoryRepository.getOne(theId);
		
		theModel.addAttribute("category", theCategory);
		
		return "admin/categories/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Category theCategory, BindingResult theResult, RedirectAttributes theAttributes, Model theModel) {
		
		Category categoryCurrent = categoryRepository.getOne(theCategory.getId());
		
		if (theResult.hasErrors()) {
			theModel.addAttribute("categoryName", categoryCurrent.getName());
			return "admin/categories/edit";
		}
		
		theAttributes.addFlashAttribute("message", "Category edited");
		theAttributes.addFlashAttribute("alertClass", "alert-success");
		
		String theSlug = theCategory.getName().toLowerCase().replace(" ", "-");
		
		Category categoryExists = categoryRepository.findByName(theCategory.getName());
		
		if (categoryExists != null) {			
			theAttributes.addFlashAttribute("message", "Category exists, please choose another one");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			
		} else {
			theCategory.setSlug(theSlug);
			
			categoryRepository.save(theCategory);
		}
		
		return "redirect:/admin/categories/edit/" + theCategory.getId();
	}
	
	@GetMapping("/delete/{theId}")
	public String add(@PathVariable int theId, RedirectAttributes theAttributes) {
		
		categoryRepository.deleteById(theId);
		
		theAttributes.addFlashAttribute("message", "Category deleted");
		theAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] theCategoriesIds) {
		
		int count = 1;
		Category theCategory;
		
		for (int theId : theCategoriesIds) {
			theCategory = categoryRepository.getOne(theId);
			theCategory.setSorting(count++);
			categoryRepository.save(theCategory);
		}
		
		return "ok";
	}
}







