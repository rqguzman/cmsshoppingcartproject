package com.guzman.cmsshoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.guzman.cmsshoppingcart.model.PageRepository;
import com.guzman.cmsshoppingcart.model.data.Page;

@ControllerAdvice
public class Common {

	@Autowired
	private PageRepository pageRepository;
	
	@ModelAttribute
	public void sharedData(Model theModel) {
		
		List<Page> pages = pageRepository.findAllByOrderBySortingAsc();
		
		theModel.addAttribute("cpages", pages);
	}
}
