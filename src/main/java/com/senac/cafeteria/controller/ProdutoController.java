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

import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/funcionarios/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

   @PostMapping
    public String criarProduto(@ModelAttribute Produto produto, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        produtoService.salvarProduto(produto, imagem);
        return "redirect:/funcionario/produtos";
    }

    @DeleteMapping("/{id}")
    public String excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return "redirect:/funcionario/produtos";

}
}