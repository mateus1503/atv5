package com.example.atv5.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Produto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Este campo NÃO deve estar em branco!")
    private String descricao;

    @Min(1)
    @NotNull(message = "Este campo NÃO pode estar vazio!")
    private BigDecimal valor;

    private Boolean status;


    public String valorFormatado() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "R$ " + df.format(valor);
    }
}
