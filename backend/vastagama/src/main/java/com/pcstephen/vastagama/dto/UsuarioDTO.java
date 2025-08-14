package com.pcstephen.vastagama.dto;

import com.pcstephen.vastagama.entidades.Role;

import java.util.List;

public record UsuarioDTO (Long id, String email, String username, String password, List<Role> roles) {
}
