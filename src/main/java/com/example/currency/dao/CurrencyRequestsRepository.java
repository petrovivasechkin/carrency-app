package com.example.currency.dao;

import com.example.currency.dao.model.CurrencyRequestsEntity;
import com.example.currency.dao.model.FullStatisticsByCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Created by sergey on 15.10.2018.
 */


public interface CurrencyRequestsRepository extends JpaRepository<CurrencyRequestsEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM CurrencyRequestsEntity a WHERE a.currencyCode = ?1")
    CurrencyRequestsEntity getCurrencyRequestsForUpdate(String currencyCode);

    @Modifying
    @Query(value = "INSERT INTO currency_requests (currency_code, success_counter) VALUES (?1, ?2)", nativeQuery = true)
    void createCurrencyRequest(String currencyCode, long successCounter);

    @Query(value = "SELECT cr.currency_code as currencyCode, " +
            "cr.success_counter as successCounter, " +
            "gs.general_counter as generalCounter, " +
            "gs.success_counter as successGeneralCounter " +
            "FROM currency_requests as cr, general_statistics gs " +
            "WHERE cr.currency_code = ?1", nativeQuery = true)
    Optional<FullStatisticsByCurrency> getFullStatisticsByCurrencyRequest(String currencyCode);

}