package com.guzman.cmsshoppingcart.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guzman.cmsshoppingcart.model.ProductRepository;
import com.guzman.cmsshoppingcart.model.data.Cart;
import com.guzman.cmsshoppingcart.model.data.Product;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private  ProductRepository productRepository;
	
	@GetMapping("/add/{theId}")
	public String add(@PathVariable int theId, 
			HttpSession session, 
			Model theModel, 
			@RequestParam(value="cartPage", required = false) String cartPage) {
		
		// get the product
		Product product = productRepository.getOne(theId);
		
		// check if the session is set
		if (session.getAttribute("cart") == null) {
			
			// session is not set. generate the cart and add products to it
			HashMap<Integer, Cart> cart = new HashMap<>();
			cart.put(theId, new Cart(theId, product.getName(), product.getPrice(), 1, product.getImage()));
			
			// set cart to session
			session.setAttribute("cart", cart);
			
		} else {

			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			
			// Does the product has been added before?
			if (cart.containsKey(theId)) {
				
				// if so, I want to increase its quantity, not add it again
				int quantity = cart.get(theId).getQuantity();
				cart.put(theId, new Cart(theId, product.getName(), product.getPrice(), ++quantity, product.getImage()));
			
			} else {
				
				// Add product to the cart
				cart.put(theId, new Cart(theId, product.getName(), product.getPrice(), 1, product.getImage()));

				// set session
				session.setAttribute("cart", cart);
			}
			
		}
		
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		int size = 0;
		double total = 0.0;
		
		for (Cart cartValue : cart.values()) {
			size += cartValue.getQuantity();
			total += cartValue.getQuantity() * Double.parseDouble(product.getPrice());
		}
		
		theModel.addAttribute("size", size);
		theModel.addAttribute("total", total);
		
		if (cartPage != null) {
			return "redirect:/cart/view";
		}
		
		return "cart_view";
	}
	
	@GetMapping("/view")
	public String view(HttpSession session, Model theModel) {
		
		if (session.getAttribute("cart") == null) {
			
			return "redirect:/";
		}
		
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		theModel.addAttribute("cart", cart);
		theModel.addAttribute("notCartViewPage", true);
		
		return "cart";
	}
	
	@GetMapping("/subtract/{theId}")
	public String subtract(@PathVariable int theId, 
			HttpSession session, 
			Model theModel,
			HttpServletRequest httpServletRequest) {
		
		Product product = productRepository.getOne(theId);
		
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		int qty = cart.get(theId).getQuantity();
		
		// if there's a single unit of a product and user remove it, it must be removed from cart.
		if (qty == 1) {
			
			cart.remove(theId);
			
			// also, if the product was the only product in the cart, it must be cleared out
			if (cart.size() == 0) {
				
				session.removeAttribute("cart");
			}
		} else {
			// decrement one unit of product's quantity 
			cart.put(theId, new Cart(theId, product.getName(), product.getPrice(), --qty, product.getImage()));
		}
		
		// get the request header
		String refererLink = httpServletRequest.getHeader("referer");
		
		// redirect user to where header is pointing
		return "redirect:" + refererLink;
	}
	
	@GetMapping("/remove/{theId}")
	public String remove(@PathVariable int theId, 
			HttpSession session, 
			Model theModel,
			HttpServletRequest httpServletRequest) {
		
		@SuppressWarnings("unchecked")
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		
		cart.remove(theId);
		
		if (cart.size() == 0) {
			
			session.removeAttribute("cart");
		}
		
		String refererLink = httpServletRequest.getHeader("referer");
		
		return "redirect:" + refererLink;
	}
	
	@GetMapping("/clear")
	public String clear(HttpSession session, HttpServletRequest httpServletRequest) {
			
		session.removeAttribute("cart");
		
		String refererLink = httpServletRequest.getHeader("referer");
		
		return "redirect:" + refererLink;
	}
}
