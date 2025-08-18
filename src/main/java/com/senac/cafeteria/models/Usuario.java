package com.senac.cafeteria.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import com.senac.cafeteria.models.enums.Role;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String senha;
    private String endereco;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "cliente") // Corrigido para "cliente"
    private List<Pedido> pedidos;
}