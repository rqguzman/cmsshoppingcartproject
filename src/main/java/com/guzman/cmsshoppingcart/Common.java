package com.guzman.cmsshoppingcart;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.guzman.cmsshoppingcart.model.CategoryRepository;
import com.guzman.cmsshoppingcart.model.PageRepository;
import com.guzman.cmsshoppingcart.model.data.Cart;
import com.guzman.cmsshoppingcart.model.data.Category;
import com.guzman.cmsshoppingcart.model.data.Page;

@ControllerAdvice
public class Common {

	@Autowired
	private PageRepository pageRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@ModelAttribute
	public void sharedData(Model theModel, HttpSession session, Principal principal) {
		
		if (principal != null) {
			theModel.addAttribute("principal", principal.getName());
		}
		
		
		List<Page> pages = pageRepository.findAllByOrderBySortingAsc();

		List<Category> categories = categoryRepository.findAllByOrderBySortingAsc();
		
		boolean cartActive = false;
		
		if (session.getAttribute("cart") != null) {
			
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			
			int size = 0;
			double total = 0.0;
			
			for (Cart product : cart.values()) {
				size += product.getQuantity();
				total += product.getQuantity() * Double.parseDouble(product.getPrice());
			}
			
			theModel.addAttribute("csize", size);
			theModel.addAttribute("ctotal", total);
			
			cartActive = true;			
		}
		
		theModel.addAttribute("cpages", pages);
		theModel.addAttribute("ccategories", categories);
		theModel.addAttribute("cartActive", cartActive);
	}
}
