package com.projeto.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_Id")
    private Integer produtoId;

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Column(name = "produto_Nome")
    private String produtoNome;

    @ManyToOne
    @JoinColumn(name = "Categoria_Id", referencedColumnName = "Categoria_Id")
    @JsonBackReference
    private Categoria categoria;

    @ManyToMany
    @JoinTable(name = "item_pedido", 
    joinColumns = @JoinColumn(name = "Produto_Id"),
    inverseJoinColumns = @JoinColumn (name = "Pedido_Id"))
    Set<Pedido> pedidos;
}
