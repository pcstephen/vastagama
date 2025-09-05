package com.pcstephen.vastagama.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
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
    private String setor;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemServico> ordemDeServicos;
    @Column(unique = true)
    private String codigoPublico;

    public void adicionaOrdemServico(OrdemServico ordemServico) {
        this.ordemDeServicos.add(ordemServico);
    }

    public List<OrdemServico> getOrdemDeServicos() {
        if(ordemDeServicos == null){
            ordemDeServicos = new ArrayList<>();
        }
        return ordemDeServicos;
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
        id = uuid;
        this.codigoPublico = uuid.toString().replace("-", "").substring(0, 8);
    }
}
