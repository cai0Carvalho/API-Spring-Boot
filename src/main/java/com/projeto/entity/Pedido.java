package com.projeto.entity;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "Pedido")
public class Pedido {
    public Integer getPedidoId() {
        return PedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        PedidoId = pedidoId;
    }

    public Date getPedidoData() {
        return pedidoData;
    }

    public void setPedidoData(Date pedidoData) {
        this.pedidoData = pedidoData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "Pedido_Id")
    private Integer PedidoId;

    @Column (name = "Pedido_Data")
    private Date pedidoData;
    
    @ManyToMany 
    Set<Produto> Produtos;

}