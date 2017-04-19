package br.com.chris.cartuchos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String inicio() {
		return "inicio";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}
}
