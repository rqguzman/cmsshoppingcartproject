package com.guzman.cmsshoppingcart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/* 
 * THIS CONTROLLER WAS GENERATED WITH EDUCATIONAL PURPOSES ONLY
 * it no longer has any function on the final project.
 * Its was here only to make it possible to compare with the way 
 *  WebMvcConfigurer works.
 *  
 */

@Controller
public class HomeController {

	@GetMapping("/someotherpage")
	public String home() {

		return "home";

	}
}
