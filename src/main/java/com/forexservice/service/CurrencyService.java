package com.forexservice.service;

import com.forexservice.Repository.CurrencyRepository;
import com.forexservice.dto.AmountDto;
import com.forexservice.dto.ConvertDto;
import com.forexservice.exception.CurrencyNotFoundException;
import com.forexservice.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrency(){
        return currencyRepository.findAll();
    }
    public Currency createCurrency(Currency currency){

        return currencyRepository.save(currency);

    }
    public void deleteCurrency(int currencyId){
        Optional<Currency>c=currencyRepository.findById(currencyId);
        if(c.isPresent()) {
            currencyRepository.deleteById(currencyId);
        }
        else {
            throw new CurrencyNotFoundException("Currency not Found");
        }
    }
    public void updateCurrency(int currencyId,Currency currency){
        Optional<Currency>currency1=currencyRepository.findById(currencyId);
        if(currency1.isPresent()) {
            Currency currency2=currencyRepository.findById(currencyId).get();
            currency2.setCurrency(currency.getCurrency());
            currency2.setCurrencyName((currency.getCurrencyName()));
            currency2.setExchangeRate(currency.getExchangeRate());
            currencyRepository.save(currency2);

        }
        else {
            throw new CurrencyNotFoundException("Currency not Found");
        }
    }


    public AmountDto convertCurrency(ConvertDto convertDto) {
        AmountDto amountDto =new AmountDto();
        Currency currencyFrom=currencyRepository.findByCurrency(convertDto.getFromCurrency());
        Currency currencyTo=currencyRepository.findByCurrency(convertDto.getToCurrency());
        Double fromRate=currencyFrom.getExchangeRate();
        Double toRate=currencyTo.getExchangeRate();
        Double amount=Double.parseDouble(String.format("%.2f", convertDto.getAmount()*(toRate/fromRate)));
        amountDto.setAmount(amount);
        return amountDto;

    }
}
