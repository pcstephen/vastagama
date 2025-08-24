package com.pcstephen.vastagama.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "itemOrdemServico")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Double getSubTotal() {
        return quantidade * valorUnitario;
    }
}
