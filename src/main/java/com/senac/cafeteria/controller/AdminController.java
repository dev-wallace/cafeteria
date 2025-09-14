package com.senac.cafeteria.controller;

import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // Formulário de novo produto
    @GetMapping("/produtos/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/novo-produto";
    }

    // Salvar novo produto - CORRIGIDO
    @PostMapping("/produtos/novo")
    public String salvarProduto(@RequestParam String nome,
                               @RequestParam String descricao,
                               @RequestParam BigDecimal preco,
                               @RequestParam("imagem") MultipartFile imagem) throws IOException {
        
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        
        produtoService.salvarProduto(produto, imagem);
        return "redirect:/admin/produtos";
    }

    // Listar todos os produtos - CORRIGIDO
    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.listarTodos();
        
        // Converter imagens para Base64
        for (Produto produto : produtos) {
            if (produto.getImagem() != null) {
                String base64Image = Base64.getEncoder().encodeToString(produto.getImagem());
                produto.setImagemBase64(base64Image);
            }
        }
        
        model.addAttribute("produtos", produtos);
        return "admin/listar-produtos";
    }

    // Formulário de edição
    @GetMapping("/produtos/editar/{id}")
    public String editarProdutoForm(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id);
        
        if (produto.getImagem() != null) {
            String base64Image = Base64.getEncoder().encodeToString(produto.getImagem());
            produto.setImagemBase64(base64Image);
        }
        
        model.addAttribute("produto", produto);
        return "admin/editar-produto";
    }

    // Atualizar produto
    @PostMapping("/produtos/editar/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                  @ModelAttribute Produto produto,
                                  @RequestParam("imagem") MultipartFile imagem) throws IOException {
        produtoService.atualizarProduto(id, produto, imagem);
        return "redirect:/admin/produtos";
    }

    // Excluir produto
    @GetMapping("/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return "redirect:/admin/produtos";
    }
}