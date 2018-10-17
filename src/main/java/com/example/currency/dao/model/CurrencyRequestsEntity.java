package com.example.currency.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by sergey on 15.10.2018.
 */

@Entity
@Table(name = "currency_requests")
public class CurrencyRequestsEntity {

    @Id
    @NotNull
    @Column(name = "currency_code")
    private String currencyCode;

    @NotNull
    @Column(name = "success_counter")
    private long successCounter;


    public CurrencyRequestsEntity() {
    }

    public CurrencyRequestsEntity(long successCounter, String currencyCode) {
        this.successCounter = successCounter;
        this.currencyCode = currencyCode;
    }

    public long getSuccessCounter() {
        return successCounter;
    }

    public void setSuccessCounter(long successCounter) {
        this.successCounter = successCounter;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void incrementSuccessCounter() {
        successCounter++;
    }

    @Override
    public String toString() {
        return "CurrencyRequestsEntity{" +
                ", currencyCode='" + currencyCode + '\'' +
                ", successCounter=" + successCounter +
                '}';
    }


}
