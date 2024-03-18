package com.ena.bank.currency;

import com.ena.bank.currency.schema.CurrencyGlobal;
import com.ena.bank.currency.schema.CurrencyType;
import com.ena.bank.currency.service.XMLService;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class CurrencyApplication implements CommandLineRunner {

    private XMLService xmlService;

    public CurrencyApplication(XMLService xmlService) {
        this.xmlService = xmlService;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CurrencyApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }



    @Override
    public void run(String... args) throws Exception {

        // load course from XMLService
        CurrencyType currencyType;
        CurrencyGlobal currencyGlobal = xmlService.parseCurrency(LocalDate.now());

        for (int i = 0; i < currencyGlobal.getCurrencyList().toArray().length; i++) {
            List<CurrencyType> currency = currencyGlobal.getCurrencyList();
            /*currencyType = new CurrencyType(
                    currency.get(i).getFullName(),
                    currency.get(i).getTitle(),
                    currency.get(i).getDescription(),
                    currency.get(i).getQuant(),
                    currency.get(i).getIndex());*/

            System.out.println(currency.get(i).getFullName());
            System.out.println(currency.get(i).getTitle());
            System.out.println(currency.get(i).getDescription());
            System.out.println(currency.get(i).getQuant());
            System.out.println(currency.get(i).getIndex());
        }

        // print course details
        System.out.println(currencyGlobal);
    }

    /*public static void main(String[] args) {
         SpringApplication.run(CurrencyApplication.class);
    }*/

}
