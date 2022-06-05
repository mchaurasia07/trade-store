package com.assignment.tradestore;

import static org.awaitility.Awaitility.await;

import com.assignment.tradestore.entity.TradeEntity;
import com.assignment.tradestore.repository.TradeRepository;
import com.assignment.tradestore.scheduler.TradeScheduler;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.Optional;

@SpringJUnitConfig(TradestoreApplication.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeStoreSchedulerTest {
    @MockBean
    private TradeRepository tradeRepository;
    @SpyBean
    private TradeScheduler tradeScheduler;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await()
                .atMost(Duration.ONE_MINUTE)
                .untilAsserted(() -> verify(tradeScheduler,atLeast(2)).executeExpireTradeTask());
    }


}
