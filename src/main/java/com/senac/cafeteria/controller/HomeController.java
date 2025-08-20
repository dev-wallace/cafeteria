package com.senac.cafeteria.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.senac.cafeteria.models.Usuario;
import com.senac.cafeteria.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/redirecionarPorRole")
    public String redirecionarPorRole(@AuthenticationPrincipal Usuario usuario) {
        if (usuario != null && usuario.getRole() == Role.FUNCIONARIO) {
            return "redirect:/admin/dashboard";
        } else if (usuario != null && usuario.getRole() == Role.CLIENTE) {
            return "redirect:/menu";
        }
        return "redirect:/login";
    }
    
  
}