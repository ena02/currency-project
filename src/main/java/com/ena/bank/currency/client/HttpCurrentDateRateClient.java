package com.ena.bank.currency.client;

import java.time.LocalDate;

public interface HttpCurrentDateRateClient {
    String buildUrlRequest(LocalDate date);
}
