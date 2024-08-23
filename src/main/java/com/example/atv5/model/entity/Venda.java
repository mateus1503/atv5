package com.example.atv5.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Component
@Scope("session")
public class Venda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itensVenda = new ArrayList<>();

    @ManyToOne
    private Pessoa pessoa;

    public String totalFormatado() {
        BigDecimal total = total();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "R$ " + df.format(total);
    }

    public BigDecimal total(){
        BigDecimal totalVenda = BigDecimal.ZERO;
        for(ItemVenda itens : itensVenda) {
            totalVenda = totalVenda.add(itens.getTotalItem());
        }
        return totalVenda;
    }
}
