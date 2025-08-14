package com.pcstephen.vastagama.dto;

import com.pcstephen.vastagama.enumeradores.RoleEnum;

public record CriarUsuarioDTO(String email, String username, String password, RoleEnum role) {
}
