package com.senac.cafeteria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Pedido pedido;
    
    @ManyToOne
    private Produto produto;
    
    private int quantidade;
    private String personalizacao; // Ex: "Sem açúcar", "Extra chocolate"
}
