package com.example.currency.dto;

import com.example.currency.dao.model.CurrencyRequestsEntity;
import com.example.currency.dao.model.GeneralStatisticsEntity;

/**
 * Created by sergey on 15.10.2018.
 */
public class CurrencyStatistic {

    private String currencyCode;

    private long currencySuccessCounter;

    private long generalCounter;

    private long generalSuccessCounter;


    public CurrencyStatistic() {
    }

    public CurrencyStatistic(String currencyCode, long currencySuccessCounter, long generalCounter, long generalSuccessCounter) {
        this.currencyCode = currencyCode;
        this.currencySuccessCounter = currencySuccessCounter;
        this.generalCounter = generalCounter;
        this.generalSuccessCounter = generalSuccessCounter;
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getCurrencySuccessCounter() {
        return currencySuccessCounter;
    }

    public void setCurrencySuccessCounter(long currencySuccessCounter) {
        this.currencySuccessCounter = currencySuccessCounter;
    }

    public long getGeneralCounter() {
        return generalCounter;
    }

    public void setGeneralCounter(long generalCounter) {
        this.generalCounter = generalCounter;
    }

    public long getGeneralSuccessCounter() {
        return generalSuccessCounter;
    }

    public void setGeneralSuccessCounter(long generalSuccessCounter) {
        this.generalSuccessCounter = generalSuccessCounter;
    }
}
