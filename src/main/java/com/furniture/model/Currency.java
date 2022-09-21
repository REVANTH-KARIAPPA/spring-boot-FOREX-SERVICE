package com.furniture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int currencyId;
    private String currency;
    private String currencyName;
    private Double exchangeRate;

}
