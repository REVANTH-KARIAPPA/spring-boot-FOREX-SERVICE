package com.forexservice.Repository;

import com.forexservice.model.Currency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    Currency findByCurrency(String currency);
}
