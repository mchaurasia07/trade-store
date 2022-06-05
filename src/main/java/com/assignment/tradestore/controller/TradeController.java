package com.assignment.tradestore.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.exception.InvalidTradeException;
import com.assignment.tradestore.model.Trade;
import com.assignment.tradestore.model.TradeResponse;
import com.assignment.tradestore.scheduler.TradeScheduler;
import com.assignment.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TradeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeScheduler.class);

    @Autowired
        TradeService tradeService;

        @PostMapping("/trade")
        public ResponseEntity<TradeResponse> saveTrade(@Valid @RequestBody Trade trade) throws InvalidTradeException {
            LOGGER.info("input request => {}", trade);
                tradeService.save(trade);
            return new ResponseEntity(new TradeResponse("SUCCESS"), HttpStatus.OK);
        }

        @GetMapping("/trade/{id}")
        public ResponseEntity<TradeEntity> findById(@PathVariable("id") String tradeId) throws InvalidTradeException {
            Optional<TradeEntity> oEntity =  tradeService.findByTradeId(tradeId);
            if (oEntity.isPresent()){
                    return new ResponseEntity(oEntity.get(), HttpStatus.OK);
            }
            return   ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
}
