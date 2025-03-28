package com.pcstephen.vastagama.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String observacoes;
    private LocalDate dataCadastro;
    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("itens")
    private List<ItemOrdemServico> itens = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;


    public OrdemServico() {
    }

    public OrdemServico(UUID id, String observacoes, LocalDate dataCadastro, List<ItemOrdemServico> itens, Cliente cliente) {
        this.id = id;
        this.observacoes = observacoes;
        this.dataCadastro = dataCadastro;
        this.itens = itens;
        this.cliente = cliente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ItemOrdemServico> getItens() {
        return itens;
    }


    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemOrdemServico::getSubTotal).sum();
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionaItem(ItemOrdemServico item) {
        item.setOrdemServico(this);
        this.itens.add(item);
    }

    public void removerItem(UUID itemId) {
        itens.removeIf(item -> item.getId().equals(itemId));
    }

    public double calcularTotal(){
        return itens.stream().mapToDouble(ItemOrdemServico::getSubTotal).sum();
    }

}
