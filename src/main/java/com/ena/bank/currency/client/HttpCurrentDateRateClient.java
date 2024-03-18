package com.ena.bank.currency.client;

import java.io.IOException;
import java.time.LocalDate;

public interface HttpCurrentDateRateClient {
    String requestByDate(LocalDate date) throws IOException, InterruptedException;
}
