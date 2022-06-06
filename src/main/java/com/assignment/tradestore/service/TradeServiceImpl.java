package com.assignment.tradestore.service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.exception.InvalidTradeException;
import com.assignment.tradestore.model.Trade;
import com.assignment.tradestore.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * validate and create trade entity save the trade
     * @param trade input
     * @throws InvalidTradeException if validation fails
     */
    @Override
    public void save(Trade trade) throws InvalidTradeException {
        // validate
        validateTrade(trade);
        // create entity
        TradeEntity entity = new TradeEntity(trade);
        LOGGER.info("saving entity => {}", entity);
        // save trade entity
        tradeRepository.save(entity);
    }

    /**
     * returns the trade fom db by given trade id
     * @param tradeId trade identifier
     * @return TradeEntity
     */
    @Override
    public Optional<TradeEntity> findByTradeId(String tradeId) {
        return tradeRepository.findById(tradeId);
    }

    /**
     * expires the trade in db
     * update the expired flag for which maturity date is past date from today's date
     * @return
     */
    @Override
    @Transactional
    public int expireTrades() {
        return tradeRepository.expireTrade("N", LocalDate.now());
    }

    /**
     * perfrom validation on incoming trade
     * @param trade input
     * @throws InvalidTradeException if validation falied
     */
    private void validateTrade(Trade trade) throws InvalidTradeException {
        // validate maturity date
        if (!isValidMaturityDate(trade.getMaturityDate())) {
            throw new InvalidTradeException("maturity date can not past date: " + trade.getMaturityDate());
        }
        // validate version
        Optional<TradeEntity> oldTrade = findByTradeId(trade.getTradeId());
        if (oldTrade.isPresent()) {
            if (isValidVersion(oldTrade.get().getVersion(), trade)) {
                throw new InvalidTradeException("version should not be lower than existing version " + oldTrade.get().getVersion() + ": " + trade.getVersion());
            }

        }
    }
}
