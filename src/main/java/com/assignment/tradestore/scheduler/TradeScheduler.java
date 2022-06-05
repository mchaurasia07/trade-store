package com.assignment.tradestore.scheduler;

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
    private TradeService tradeService;

    /**
     * run the scheduler as per cron expression define in application.properties
     * It will expire the trade in db by updating the expiredFlag for which maturity date is before than today's date
     */
    @Scheduled(cron = "${scheduler.expire.trade.cron}") // current it runs in every 30 sec. but it should ideally run once per day
    public void executeExpireTradeTask() {
        int count = tradeService.expireTrades();
        log.info("total trade expired - {}", count);

    }
}
