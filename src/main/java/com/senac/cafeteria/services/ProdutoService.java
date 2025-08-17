package com.senac.cafeteria.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto, MultipartFile imagem) throws IOException {
        if(imagem != null && !imagem.isEmpty()) {
            produto.setImagem(imagem.getBytes());
        }
        return produtoRepository.save(produto);
    }
    
    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
