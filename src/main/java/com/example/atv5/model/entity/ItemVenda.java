package com.example.atv5.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Data
public class ItemVenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;

    private BigDecimal totalItem;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;


    public String totalFormatado() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "R$ " + df.format(getTotalItem());
    }

    public String precoUnitario() {
        BigDecimal quantidadeTotal = BigDecimal.valueOf(getQuantidade());
        return "R$ " + getTotalItem().divide(quantidadeTotal, BigDecimal.ROUND_HALF_UP);
    }
}
