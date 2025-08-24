package com.pcstephen.vastagama.dto;

import com.pcstephen.vastagama.entidades.Cliente;
import com.pcstephen.vastagama.entidades.ItemOrdemServico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdemServicoDTO {

    private UUID id;
    private String observacoes;
    private LocalDate dataCadastro;
    private List<ItemOrdemServico> itens;
    private Cliente cliente;
    private LocalDate dataAlteracao;
}
