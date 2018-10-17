package com.example.currency.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sergey on 15.10.2018.
 */
public class Currency implements Serializable {

    @NotBlank
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
