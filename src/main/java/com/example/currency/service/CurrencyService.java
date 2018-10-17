package com.example.currency.service;

import com.example.currency.client.CurrencyWebServiceClient;
import com.example.currency.dto.Currency;
import com.example.currency.dto.CurrencyStatistic;
import com.example.currency.exception.WebClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CurrencyWebServiceClient webServiceClient;
    private TransactionalService transactionalService;

    public CurrencyService(@Autowired CurrencyWebServiceClient webServiceClient,
                           @Autowired TransactionalService transactionalService) {

        this.webServiceClient = webServiceClient;

        this.transactionalService = transactionalService;
    }


    public boolean doCurrencyAction(Currency currency) {

        boolean isSuccessful = false;
        try {
            isSuccessful = webServiceClient.requestCurrencyData(currency.getCurrencyCode());
            logger.info("Got result from webServiceClient for currency: {}, result:{}", currency, isSuccessful);
        } catch (WebClientException e) {
            logger.error("Cant get data from webServiceClient for {}", currency, e);
        } finally {
            transactionalService.registerCurrencyRequest(currency);
            transactionalService.incrementCounters(currency, isSuccessful);
        }
        return isSuccessful;
    }

    public Optional<CurrencyStatistic> getCurrencyStatistic(String currencyCode) {
        return transactionalService.getCurrencyStatistic(currencyCode);
    }


}
