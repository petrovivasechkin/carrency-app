package com.example.currency.dao.model;

import javax.persistence.Entity;

/**
 * Created by sergey on 17.10.2018.
 */

public interface FullStatisticsByCurrency {

    String getCurrencyCode();

    long getSuccessCounter();

    long getGeneralCounter();

    long getSuccessGeneralCounter();

}
