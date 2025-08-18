package com.senac.cafeteria.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import com.senac.cafeteria.models.enums.TipoEntrega;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id") // Adicionado para melhor controle
    private Usuario cliente;
    
    private LocalDateTime data;
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    private TipoEntrega tipoEntrega;
    
    private BigDecimal taxaEntrega;
    private String comentario;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;
}