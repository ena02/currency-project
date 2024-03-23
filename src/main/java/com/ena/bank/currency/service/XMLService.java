package com.ena.bank.currency.service;

import com.ena.bank.currency.client.NbCurrentDateRateClient;
import com.ena.bank.currency.schema.CurrencyGlobal;
import com.ena.bank.currency.schema.CurrencyType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class XMLService {

    private final Cache<LocalDate, Map<String, BigDecimal>> cache;

    private NbCurrentDateRateClient client;

    public XMLService(NbCurrentDateRateClient client) {
        this.cache = CacheBuilder.newBuilder().build();
        this.client = client;
    }

    public CurrencyGlobal parseCurrencyByDate(LocalDate date) {
        return client.parseCurrencyByDate(date);
    }

    public BigDecimal parseCurrencyByCode(String currencyCode) {
        CurrencyGlobal currencyGlobal = client.parseCurrencyByDate(LocalDate.now());
        List<CurrencyType> currencyList = currencyGlobal.getCurrencyList();
        for (CurrencyType currencyType : currencyList) {
            String currencyTitle = currencyType.getTitle();
            if (Objects.equals(currencyTitle, currencyCode)) {
                return BigDecimal.valueOf(currencyType.getDescription());
            }
        }
        throw new IllegalArgumentException("There is no such currency");
    }


}
