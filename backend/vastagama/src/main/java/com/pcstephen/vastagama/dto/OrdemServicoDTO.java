package com.pcstephen.vastagama.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class OrdemServicoDTO {

    private UUID id;
    private String observacoes;
    private LocalDate dataCadastro;
    private List<ItemOrdemServicoDTO> itens;
    private ClienteDTO cliente;
    private LocalDate dataAlteracao;
}
