package com.forexservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int currencyId;
    @Column(unique = true)
    private String currency;
    private String currencyName;
    private Double exchangeRate;

}
