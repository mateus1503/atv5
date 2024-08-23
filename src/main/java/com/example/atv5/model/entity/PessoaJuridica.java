package com.example.atv5.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class PessoaJuridica extends Pessoa implements Serializable {
    @CNPJ
    private String cnpj;
}
