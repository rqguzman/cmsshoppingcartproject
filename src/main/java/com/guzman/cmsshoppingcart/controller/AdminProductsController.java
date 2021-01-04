package com.guzman.cmsshoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guzman.cmsshoppingcart.model.ProductRepository;
import com.guzman.cmsshoppingcart.model.data.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public String index(Model theModel) {
		
		List<Product> theProducts = productRepository.findAll();
		
		theModel.addAttribute("products", theProducts);
		
		return "admin/products/index";
	}
}
