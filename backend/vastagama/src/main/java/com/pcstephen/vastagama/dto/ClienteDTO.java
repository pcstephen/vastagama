package com.pcstephen.vastagama.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteDTO {


    private String nome;
    private String rua;
    private String complemento;
    private String setor;
    private String numeroEndereco;
    private List<String> telefones;
    private List<OrdemServicoDTO> ordemDeServicos;
    private String codPublico;
}
