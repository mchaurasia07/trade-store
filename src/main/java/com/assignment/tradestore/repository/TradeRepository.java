package com.assignment.tradestore.repository;

import java.time.LocalDate;

import com.assignment.tradestore.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, String> {
    /**
     * update the expiredFlag to N for the records which has maturity date less than today's date and expiredFlag is 'Y'
     * @param expiredFlag N for expired
     * @param date current date
     * @return num of records updated
     */
    @Modifying
    @Query("update TradeEntity t set t.expiredFlag = 'Y' where t.expiredFlag = :expiredFlag and t.maturityDate < :date")
    int expireTrade(@Param(value = "expiredFlag") String expiredFlag, @Param(value = "date") LocalDate date);


}
