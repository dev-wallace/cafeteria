package com.senac.cafeteria.controller;

import com.senac.cafeteria.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "public/menu";
    }
}