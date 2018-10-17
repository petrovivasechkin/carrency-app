package com.example.currency.service;

import com.example.currency.dao.CurrencyRequestsRepository;
import com.example.currency.dao.GeneralStatisticsRepository;
import com.example.currency.dao.model.CurrencyRequestsEntity;
import com.example.currency.dao.model.FullStatisticsByCurrency;
import com.example.currency.dao.model.GeneralStatisticsEntity;
import com.example.currency.dto.Currency;
import com.example.currency.dto.CurrencyStatistic;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by sergey on 15.10.2018.
 */

@Service
public class TransactionalService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private CurrencyRequestsRepository currencyRequestsRepository;
    private GeneralStatisticsRepository generalStatisticsRepository;

    public TransactionalService(@Autowired CurrencyRequestsRepository currencyRequestsRepository,
                                @Autowired GeneralStatisticsRepository generalStatisticsRepository) {
        this.currencyRequestsRepository = currencyRequestsRepository;
        this.generalStatisticsRepository = generalStatisticsRepository;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerCurrencyRequest(Currency currency) {
        Optional<CurrencyRequestsEntity> currencyRequestsOpt
                = currencyRequestsRepository.findById(currency.getCurrencyCode());

        if (!currencyRequestsOpt.isPresent()) {
            try {

                currencyRequestsRepository.createCurrencyRequest(currency.getCurrencyCode(), 0);
            } catch (ConstraintViolationException e) {
                logger.info("Currency request registered by another request {}", currency.getCurrencyCode());
            }

        }
        logger.info("Currency request registered for {}", currency.getCurrencyCode());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incrementCounters(Currency currency, boolean success) {
        GeneralStatisticsEntity generalStatisticsEntity =
                generalStatisticsRepository.getFirstGeneralStatisticsForUpdate();
        generalStatisticsEntity.incrementGeneralCounter();

        if (success) {
            CurrencyRequestsEntity currencyRequestsEntity
                    = currencyRequestsRepository.getCurrencyRequestsForUpdate(currency.getCurrencyCode());

            currencyRequestsEntity.incrementSuccessCounter();
            generalStatisticsEntity.incrementSuccessCounter();

            currencyRequestsRepository.save(currencyRequestsEntity);

        }

        generalStatisticsRepository.save(generalStatisticsEntity);

        logger.info("All counters were updated for currency: {}", currency.getCurrencyCode());
    }

    @Transactional(readOnly = true)
    public Optional<CurrencyStatistic> getCurrencyStatistic(String currencyCode) {

        Optional<FullStatisticsByCurrency> fullStatOpt = currencyRequestsRepository.getFullStatisticsByCurrencyRequest(currencyCode);

        if (fullStatOpt.isPresent()) {
            FullStatisticsByCurrency stat = fullStatOpt.get();
            return Optional.of(new CurrencyStatistic(
                    stat.getCurrencyCode(),
                    stat.getSuccessCounter(),
                    stat.getGeneralCounter(),
                    stat.getSuccessGeneralCounter()));
        }

        return Optional.empty();
    }


}
