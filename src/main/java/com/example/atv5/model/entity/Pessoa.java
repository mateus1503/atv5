package com.example.atv5.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @Size(min = 8, max = 11)
    @Pattern(regexp = "\\d+", message = "Telefone deve conter apenas números")
    private String telefone;

    @OneToMany(mappedBy = "pessoa")
    private List<Venda> vendas = new ArrayList<>();

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @Valid
    private Usuario usuario;

    public boolean tipoObjeto(String objeto) {
        return this.getClass().getSimpleName().toLowerCase().equals(objeto.toLowerCase());
    }

    public String nomeClasse() {
        String className = this.getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
