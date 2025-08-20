package com.senac.cafeteria.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/funcionarios/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @PostMapping
    public String criarProduto(
            @ModelAttribute Produto produto,
            @RequestParam("imagem") MultipartFile imagem,
            RedirectAttributes redirectAttributes) throws IOException {
        
        try {
            produtoService.salvarProduto(produto, imagem);
            redirectAttributes.addFlashAttribute("sucesso", "Produto criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar produto: " + e.getMessage());
        }
        
        return "redirect:/funcionarios/produtos";
    }

    @DeleteMapping("/{id}")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.excluirProduto(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir produto: " + e.getMessage());
        }
        
        return "redirect:/funcionarios/produtos";
    }
}