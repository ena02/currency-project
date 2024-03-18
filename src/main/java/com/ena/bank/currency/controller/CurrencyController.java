package com.ena.bank.currency.controller;

import com.ena.bank.currency.schema.CurrencyType;
import com.ena.bank.currency.service.XMLService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final XMLService xmlService;

    @GetMapping("/rate/{date}")
    public List<CurrencyType> currencyType(@PathVariable("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        formatter = formatter.withLocale( date );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate dt = LocalDate.parse(date, formatter);
        return xmlService.parseCurrencyByDate(dt).getCurrencyList();
    }

    @GetMapping("/title/{title}")
    public String currencyByTitle(@PathVariable("title") String title) {
        return xmlService.parseCurrencyByName(title);
    }
}
