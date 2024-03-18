package com.ena.bank.currency.schema;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CurrencyGlobal {
    private String title;
    private String link;
    private String description;
    private String copyright;
    private String date;
    private List<CurrencyType> currencyList;

    public CurrencyGlobal(String title, String link, String description, String copyright, String date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.copyright = copyright;
        this.date = date;
    }

    public CurrencyGlobal(String title, String link, String description, String copyright, String date, List<CurrencyType> currencyList) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.copyright = copyright;
        this.date = date;
        this.currencyList = currencyList;
    }
}
