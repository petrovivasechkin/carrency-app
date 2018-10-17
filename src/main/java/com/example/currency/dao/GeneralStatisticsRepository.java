package com.example.currency.dao;

import com.example.currency.dao.model.GeneralStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

/**
 * Created by sergey on 15.10.2018.
 */

public interface GeneralStatisticsRepository extends JpaRepository<GeneralStatisticsEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM GeneralStatisticsEntity a WHERE a.id = 1")
    GeneralStatisticsEntity getFirstGeneralStatisticsForUpdate();

}
