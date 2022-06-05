package com.assignment.tradestore.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.exception.InvalidTradeException;
import com.assignment.tradestore.model.Trade;

public interface TradeService {

    void save(Trade trade) throws InvalidTradeException;

    Optional<TradeEntity> findByTradeId(String tradeId);

    int expireTrades();

    default boolean isValidVersion(int oldVersion, Trade trade) throws InvalidTradeException {
        return trade.getVersion() < oldVersion;
    }

    default boolean isValidMaturityDate(LocalDate date) {
        return date.isAfter(LocalDate.now());


    }
}
