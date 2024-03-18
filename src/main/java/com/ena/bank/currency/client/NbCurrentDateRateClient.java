package com.ena.bank.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class NbCurrentDateRateClient implements HttpCurrentDateRateClient {

    private static final String DATE_PATTERN = "dd.mm.yyyy";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public String requestByDate(LocalDate date) throws IOException, InterruptedException {

        String baseUrl = "https://nationalbank.kz/rss/get_rates.cfm?fdate=";
        HttpClient client = HttpClient.newHttpClient();
        String url = buildUrlRequest(baseUrl, date);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return baseUrl;
    }

    private String buildUrlRequest(String baseUrl, LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("date_req", DATE_TIME_FORMATTER.format(date))
                .build().toUriString();
    }
}
