package com.pcstephen.vastagama.dto;

import java.util.UUID;

public record EnderecoDTO(UUID id ,String rua, String bairro, String complemento, String cidade) {
}
