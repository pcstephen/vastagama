package com.pcstephen.vastagama.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "itemOrdemServico")
public class ItemOrdemServico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String descricao;
    private int quantidade;
    private double valorUnitario;
    @ManyToOne
    @JsonIgnore
    private OrdemServico ordemServico;

    public ItemOrdemServico() {
    }

    public ItemOrdemServico(UUID id, String descricao, int quantidade, double valorUnitario, OrdemServico ordemServico) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.ordemServico = ordemServico;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getSubTotal() {
        return quantidade * valorUnitario;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
}
