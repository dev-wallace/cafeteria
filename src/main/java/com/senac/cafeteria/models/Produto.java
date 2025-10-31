package com.senac.cafeteria.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;

    @Lob
    private byte[] imagem;

    @Transient
    private String imagemBase64;

    @OneToMany(mappedBy = "produto") // Este mapeamento deve existir
    private List<ItemPedido> itensPedido;
}