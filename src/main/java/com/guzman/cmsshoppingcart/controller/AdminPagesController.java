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

import com.guzman.cmsshoppingcart.model.PageRepository;
import com.guzman.cmsshoppingcart.model.data.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
	
	@Autowired
	private PageRepository pageRepository;

	@GetMapping
	public String index(Model model) {
		
		List<Page> pages = pageRepository.findAllByOrderBySortingAsc();
		
		model.addAttribute("pages", pages);
		
		return "admin/pages/index";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute Page thePage) {
		
		// model.addAttribute("page", new Page());
		
		return "admin/pages/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Page thePage, BindingResult theResult, RedirectAttributes theAttributes, Model theModel) {

		if (theResult.hasErrors()) {
			return "admin/pages/add";
		}

		theAttributes.addFlashAttribute("message", "Page added");
		theAttributes.addFlashAttribute("alertClass", "alert-success");

		String theSlug = thePage.getSlug() == "" ? thePage.getTitle().toLowerCase().replace(" ", "-")
				: thePage.getSlug().toLowerCase().replace(" ", "-");
		
		Page slugExists = pageRepository.findBySlug(theSlug);
		
		if (slugExists != null) {			
			theAttributes.addFlashAttribute("message", "Slug exists, please choose another one.");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("page", thePage);
			
		} else {
			thePage.setSlug(theSlug);
			thePage.setSorting(100);
			
			pageRepository.save(thePage);
		}

		return "redirect:/admin/pages/add";
	}

	@GetMapping("/edit/{theId}")
	public String edit(@PathVariable int theId, Model theModel) {
		
		Page thePage = pageRepository.getOne(theId);
		
		theModel.addAttribute("page", thePage);
		
		return "admin/pages/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid Page page, BindingResult theResult, RedirectAttributes theAttributes, Model theModel) {
		
		Page pageCurrent = pageRepository.getOne(page.getId());
		
		if (theResult.hasErrors()) {
			theModel.addAttribute("pageTitle", pageCurrent.getTitle());
			return "admin/pages/edit";
		}
		
		theAttributes.addFlashAttribute("message", "Page edited");
		theAttributes.addFlashAttribute("alertClass", "alert-success");
		
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
				: page.getSlug().toLowerCase().replace(" ", "-");
		
//		Page slugExists = pageRepository.findBySlugAndIdNot(slug, page.getId());
		Page slugExists = pageRepository.findBySlug(page.getId(), slug);
		
		if (slugExists != null) {			
			theAttributes.addFlashAttribute("message", "Slug exists, please choose another one");
			theAttributes.addFlashAttribute("alertClass", "alert-danger");
			theAttributes.addFlashAttribute("page", page);
			
		} else {
			page.setSlug(slug);
			
			pageRepository.save(page);
		}
		
		return "redirect:/admin/pages/edit/" + page.getId();
	}
	
	@GetMapping("/delete/{theId}")
	public String add(@PathVariable int theId, RedirectAttributes theAttributes) {
		
		pageRepository.deleteById(theId);
		
		theAttributes.addFlashAttribute("message", "Page deleted");
		theAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/pages";
	}
	
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] thePageIds) {
		
		int count = 1;
		Page thePage;
		
		for (int theId : thePageIds) {
			thePage =  pageRepository.getOne(theId);
			thePage.setSorting(count++);
			pageRepository.save(thePage);
		}
		
		return "ok";
	}

}










