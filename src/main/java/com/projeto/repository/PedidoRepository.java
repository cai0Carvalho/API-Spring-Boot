package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

    
}
