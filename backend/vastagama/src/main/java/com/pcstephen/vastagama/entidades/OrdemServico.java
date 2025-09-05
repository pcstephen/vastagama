package com.pcstephen.vastagama.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordem_servico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String observacoes;
    private LocalDate dataCadastro;
    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("itens")
    private List<ItemOrdemServico> itens;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;
    private LocalDate dataAlteracao;

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

    @PrePersist
    public void prePersist(){
        this.dataAlteracao = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.dataAlteracao = LocalDate.now();
    }

}
