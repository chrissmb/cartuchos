package br.com.chris.cartuchos.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String inicio(Model model) {
		UserDetails usuario = this.getUsuarioLogado();
		model.addAttribute("usuario", usuario.getUsername());
		String grupo = usuario.getAuthorities().toString();
		if (grupo.equals("[ADMIN]"))
			return "redirect:/admin";
		return "inicio";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		
		return "admin";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginError")
	public String login(Model modelo) {
		modelo.addAttribute("loginError", true);
		return "login";
	}
	
	private UserDetails getUsuarioLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
}
