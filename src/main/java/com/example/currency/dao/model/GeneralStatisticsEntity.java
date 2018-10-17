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
@Table(name = "general_statistics")
public class GeneralStatisticsEntity {

    @Id
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "general_counter")
    private long generalCounter;

    @NotNull
    @Column(name = "success_counter")
    private long successCounter;

    public GeneralStatisticsEntity() {
    }

    public GeneralStatisticsEntity(@NotNull long generalCounter, @NotNull long successCounter) {
        this.generalCounter = generalCounter;
        this.successCounter = successCounter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGeneralCounter() {
        return generalCounter;
    }

    public void setGeneralCounter(long generalCounter) {
        this.generalCounter = generalCounter;
    }

    public long getSuccessCounter() {
        return successCounter;
    }

    public void setSuccessCounter(long successCounter) {
        this.successCounter = successCounter;
    }

    public void incrementGeneralCounter() {
        generalCounter++;
    }

    public void incrementSuccessCounter() {
        successCounter++;
    }

    @Override
    public String toString() {
        return "GeneralStatisticsEntity{" +
                "id=" + id +
                ", generalCounter=" + generalCounter +
                ", successCounter=" + successCounter +
                '}';
    }
}
