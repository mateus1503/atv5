package com.example.atv5.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class PessoaFisica extends Pessoa implements Serializable {
    @CPF
    private String cpf;
}
