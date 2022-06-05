package com.assignment.tradestore.service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.exception.InvalidTradeException;
import com.assignment.tradestore.model.Trade;
import com.assignment.tradestore.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService{
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public void save(Trade trade) throws InvalidTradeException {
        validateTrade(trade);
        tradeRepository.save(new TradeEntity(trade));
    }

    @Override
    public Optional<TradeEntity> findByTradeId(String tradeId) {
        return tradeRepository.findById(tradeId);
    }

    @Override
    @Transactional
    public int expireTrades() {
        return tradeRepository.expireTrade("N", LocalDate.now());
    }

    private void validateTrade(Trade trade) throws InvalidTradeException {
        if (!isValidMaturityDate(trade.getMaturityDate())){
            throw new InvalidTradeException("maturity date can not past date: " + trade.getMaturityDate());
        }
        Optional<TradeEntity> oldTrade = findByTradeId(trade.getTradeId());
        if (oldTrade.isPresent()){
            if (isValidVersion(oldTrade.get().getVersion(), trade)){
                throw new InvalidTradeException("version should not be lower than existing version " + oldTrade.get().getVersion() +": " + trade.getVersion());
            }

        }
    }
}
