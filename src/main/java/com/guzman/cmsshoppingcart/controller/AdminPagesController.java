package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guzman.cmsshoppingcart.model.PageRepository;
import com.guzman.cmsshoppingcart.model.data.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
	
	private PageRepository pageRepository;
	
	

	@Autowired
	public AdminPagesController(PageRepository pageRepo) {
		this.pageRepository = pageRepo;
	}

	@GetMapping
	public String index(Model model) {
		
		List<Page> pages = pageRepository.findAll();
		
		model.addAttribute("pages", pages);
		
		return "admin/pages/index";
	}

}
