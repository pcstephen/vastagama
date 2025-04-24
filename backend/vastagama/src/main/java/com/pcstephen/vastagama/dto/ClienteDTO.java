package com.pcstephen.vastagama.dto;

import java.util.List;

public record ClienteDTO(String nome,EnderecoDTO enderecoDTO, List<TelefoneDTO> telefonesDTO) {
}
