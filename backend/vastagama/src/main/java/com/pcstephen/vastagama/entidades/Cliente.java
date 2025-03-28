package com.pcstephen.vastagama.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String nome;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();
    @OneToOne(mappedBy = "cliente")
    private Endereco endereco;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordemDeServicos = new ArrayList<>();


    public Cliente() {
    }

    public Cliente(UUID id, String nome, List<Telefone> telefones, Endereco endereco, List<OrdemServico> ordemServicosList) {
        this.id = id;
        this.nome = nome;
        this.telefones = telefones;
        this.endereco = endereco;
        this.ordemDeServicos = ordemServicosList;
    }

    public void adicionaTelefone(Telefone telefone){
        telefones.add(telefone);
    }

    public void adicionaOrdemServico(OrdemServico ordemServico) {
        this.ordemDeServicos.add(ordemServico);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
