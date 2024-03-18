package com.ena.bank.currency.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyType {
    private String fullName;
    private String title;
    private double description;
    private double quant;
    private String index;

    public CurrencyType(String fullName, String title, double description, double quant, String index) {
        this.fullName = fullName;
        this.title = title;
        this.description = description;
        this.quant = quant;
        this.index = index;
    }
}
