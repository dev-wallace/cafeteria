package com.senac.cafeteria.services;

import com.senac.cafeteria.models.Pedido;
import com.senac.cafeteria.models.Usuario;
import com.senac.cafeteria.models.enums.StatusPedido;
import com.senac.cafeteria.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAllByOrderByDataCriacaoDesc();
    }

    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatusOrderByDataCriacaoDesc(status);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
    }

    public void atualizarStatus(Long id, StatusPedido status) {
        System.out.println("=== PEDIDO SERVICE - ATUALIZAR STATUS ===");
        System.out.println("Buscando pedido ID: " + id);
        
        Pedido pedido = buscarPorId(id);
        
        System.out.println("Pedido encontrado: " + (pedido != null));
        if (pedido != null) {
            System.out.println("Status atual: " + pedido.getStatus());
            System.out.println("Novo status: " + status);
            
            pedido.setStatus(status);
            pedidoRepository.save(pedido);
            
            System.out.println("Status salvo com sucesso!");
        }
    }

    public void excluirPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }
}