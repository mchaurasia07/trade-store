package com.assignment.tradestore.scheduler;

import java.text.SimpleDateFormat;

import com.assignment.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TradeScheduler {

    private static final Logger log = LoggerFactory.getLogger(TradeScheduler.class);

    @Autowired
    TradeService tradeService;

    @Scheduled(cron = "${scheduler.expire.trade.cron}")//currentlly setup 30 sec
    public void executeExpireTradeTask() {
        int count = tradeService.expireTrades();
        log.info("total trade expired - {}", count);

    }
}
