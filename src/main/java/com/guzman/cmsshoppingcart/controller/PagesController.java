package com.guzman.cmsshoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guzman.cmsshoppingcart.model.PageRepository;
import com.guzman.cmsshoppingcart.model.data.Page;

@Controller
@RequestMapping("/")
public class PagesController {

	@Autowired
	private PageRepository pageRepository;

	@GetMapping
	public String home(Model theModel) {

		Page page = pageRepository.findBySlug("home");
		theModel.addAttribute("page", page);

		return "page";
	}

	@GetMapping("/{slug}")
	public String page(@PathVariable String slug, Model theModel) {

		Page page = pageRepository.findBySlug(slug);

		if (page == null) {
			return "redirect:/";
		}

		theModel.addAttribute("page", page);

		return "page";
	}
}
