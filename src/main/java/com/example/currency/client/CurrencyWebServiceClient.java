package com.example.currency.client;

import com.example.currency.exception.WebClientException;

/**
 * Created by sergey on 15.10.2018.
 */
public interface CurrencyWebServiceClient {
      boolean requestCurrencyData(String currencyCode) throws WebClientException;
}
