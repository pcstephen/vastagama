package com.pcstephen.vastagama.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum values{
        ADMIN(1L),
        BASIC(2L);

        private Long roleId;

        values(Long roleId) {
            this.roleId = roleId;
        }
    }
}
