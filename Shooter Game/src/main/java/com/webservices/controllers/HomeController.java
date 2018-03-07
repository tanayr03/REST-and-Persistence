package com.webservices.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HomeController {
	String message = "Welcome to cmpe lab2 Shooter Game!!";
	 
	/**
	 * This controller serves the initial request from the user. 
	 * @param name
	 * @return welcome page
	 */
	@RequestMapping("hello")
	public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}
}