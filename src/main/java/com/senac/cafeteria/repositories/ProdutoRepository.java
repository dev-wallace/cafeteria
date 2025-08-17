package com.senac.cafeteria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senac.cafeteria.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
