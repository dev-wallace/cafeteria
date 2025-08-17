package com.senac.cafeteria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senac.cafeteria.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
}