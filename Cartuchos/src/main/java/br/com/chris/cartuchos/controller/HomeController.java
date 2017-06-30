package br.com.chris.cartuchos.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	private UserDetails getUsuarioLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
}
