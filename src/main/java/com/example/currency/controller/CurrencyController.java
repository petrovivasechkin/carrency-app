package com.example.currency.controller;

import com.example.currency.dto.Currency;
import com.example.currency.dto.CurrencyStatistic;
import com.example.currency.exception.WebClientException;
import com.example.currency.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by sergey on 15.10.2018.
 */
@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CurrencyService currencyService;

    public CurrencyController(@Autowired CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/action",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity action(@RequestBody @Valid Currency currency) throws WebClientException {
        logger.info("Got new request for currency {}", currency);
        if (currencyService.doCurrencyAction(currency)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @RequestMapping(value = "/stat/{currencyCode}", method = RequestMethod.GET)
    public ResponseEntity<CurrencyStatistic> stat(@PathVariable String currencyCode) {
        logger.info("Got new statistic request {}");
        Optional<CurrencyStatistic> res = currencyService.getCurrencyStatistic(currencyCode);
        return res.map(currencyStatistic -> ResponseEntity.ok().body(currencyStatistic)).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
