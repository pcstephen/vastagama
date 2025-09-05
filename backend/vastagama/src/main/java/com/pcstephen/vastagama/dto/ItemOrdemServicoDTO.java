package com.pcstephen.vastagama.dto;

import com.pcstephen.vastagama.entidades.OrdemServico;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemOrdemServicoDTO {

    private UUID id;
    private String descricao;
    private int quantidade;
    private double valorUnitario;
    private OrdemServico ordemServico;
    private Double subTotal;
}
