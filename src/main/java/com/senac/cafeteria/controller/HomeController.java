package com.senac.cafeteria.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.senac.cafeteria.models.Usuario;
import com.senac.cafeteria.models.enums.Role;

@Controller

public class HomeController {

    @GetMapping("/")
    public String home() {
        return "auth/login"; // ← Vai direto para o login
    }

    @GetMapping("/redirecionarPorRole")
    public String redirecionarPorRole(@AuthenticationPrincipal Usuario usuario) {
        if (usuario != null) {
            if (usuario.getRole() == Role.FUNCIONARIO) {
                return "redirect:/admin/dashboard";
            } else if (usuario.getRole() == Role.CLIENTE) {
                return "redirect:/menu";
            }
        }
        return "redirect:/login";
    }
}