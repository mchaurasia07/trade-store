package com.assignment.tradestore.controller;

import javax.validation.Valid;
import java.util.Optional;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.exception.InvalidTradeException;
import com.assignment.tradestore.model.Trade;
import com.assignment.tradestore.model.TradeResponse;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    /**
     *  expose the post method to save the trade in db
     *  validate the request and if validate is successful then create/update the record in db
      * @param trade request
     * @return trade response
     * @throws InvalidTradeException if validation fails
     */
    @PostMapping("/trade")
    public ResponseEntity<TradeResponse> saveTrade(@Valid @RequestBody Trade trade) throws InvalidTradeException {
        LOGGER.info("input request => {}", trade);
        tradeService.save(trade);
        return new ResponseEntity(new TradeResponse("SUCCESS"), HttpStatus.CREATED);
    }

    /**
     * expose the get method to retrieve the trade based in trade id
     * @param tradeId path param
     * @return trade from db
     * @throws InvalidTradeException if trade is not found
     */
    @GetMapping("/trade/{id}")
    public ResponseEntity<TradeEntity> findById(@PathVariable("id") String tradeId) throws InvalidTradeException {
        Optional<TradeEntity> oEntity = tradeService.findByTradeId(tradeId);
        if (oEntity.isPresent()) {
            return new ResponseEntity(oEntity.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
