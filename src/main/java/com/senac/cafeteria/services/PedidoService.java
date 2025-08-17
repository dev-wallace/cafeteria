package com.senac.cafeteria.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.senac.cafeteria.models.ItemPedido;
import com.senac.cafeteria.models.Pedido;
import com.senac.cafeteria.models.Produto;
import com.senac.cafeteria.models.TipoEntrega;
import com.senac.cafeteria.repositories.PedidoRepository;
import com.senac.cafeteria.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;


    public Pedido CriarPedido(Pedido pedido, Map<Long, Integer> carrinho, String personalizacoes){

         BigDecimal total = BigDecimal.ZERO;
        
        for(Map.Entry<Long, Integer> item : carrinho.entrySet()) {
            Produto produto = produtoRepository.findById(item.getKey()).orElseThrow();
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getValue());
            itemPedido.setPersonalizacao(personalizacoes);
            itemPedido.setPedido(pedido);
            
            pedido.getItens().add(itemPedido);
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getValue())));
        }
        
        if(pedido.getTipoEntrega() == TipoEntrega.ENTREGA) {
            total = total.add(pedido.getTaxaEntrega());
        }
        
        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }
}
