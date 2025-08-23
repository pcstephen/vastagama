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
    private List<String> telefones;
    private String rua;
    private String complemento;
    private String numeroEndereco;
    private String bairro;
    private String setor;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordemDeServicos = new ArrayList<>();
    @Column(unique = true)
    private String codigoPublico;


    public Cliente() {
    }

    public Cliente(UUID id, String nome, List<String> telefones, String rua, String complemento, String numeroEndereco, String bairro, String setor, List<OrdemServico> ordemDeServicos, String codigoPublico) {
        this.id = id;
        this.nome = nome;
        this.telefones = telefones;
        this.rua = rua;
        this.complemento = complemento;
        this.numeroEndereco = numeroEndereco;
        this.bairro = bairro;
        this.setor = setor;
        this.ordemDeServicos = ordemDeServicos;
        this.codigoPublico = codigoPublico;
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

    @PrePersist
    public void gerarCodigoPublico() {
        UUID uuid = UUID.randomUUID();
        this.codigoPublico = uuid.toString().replace("-", "").substring(0, 8);
    }
}
