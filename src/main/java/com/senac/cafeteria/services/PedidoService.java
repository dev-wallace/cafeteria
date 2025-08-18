package com.senac.cafeteria.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.cafeteria.models.ItemPedido;
import com.senac.cafeteria.models.Pedido;
import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.models.enums.TipoEntrega;
import com.senac.cafeteria.repositories.PedidoRepository;
import com.senac.cafeteria.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public Pedido criarPedido(Pedido pedido, Map<Long, Integer> carrinho, String personalizacoes) {
        validarPedido(pedido, carrinho);
        
        BigDecimal total = BigDecimal.ZERO;
        
        for(Map.Entry<Long, Integer> item : carrinho.entrySet()) {
            Produto produto = produtoRepository.findById(item.getKey())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + item.getKey()));
            
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getValue());
            itemPedido.setPersonalizacao(personalizacoes);
            itemPedido.setPedido(pedido);
            
            pedido.getItens().add(itemPedido);
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getValue())));
        }
        
        if(pedido.getTipoEntrega() == TipoEntrega.ENTREGA) {
            validarTaxaEntrega(pedido.getTaxaEntrega());
            total = total.add(pedido.getTaxaEntrega());
        }
        
        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido, Map<Long, Integer> carrinho) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }
        
        if (carrinho == null || carrinho.isEmpty()) {
            throw new IllegalArgumentException("Carrinho não pode estar vazio");
        }
    }

    private void validarTaxaEntrega(BigDecimal taxa) {
        if (taxa == null || taxa.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega inválida");
        }
    }
}