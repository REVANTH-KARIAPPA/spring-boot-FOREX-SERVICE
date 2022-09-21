package com.furniture.Repository;

import com.furniture.model.Currency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    Currency findByCurrency(String currency);
}
