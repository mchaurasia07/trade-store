package com.assignment.tradestore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import com.assignment.tradestore.model.Trade;

/**
 * represent hibernate entity.
 */
@Entity
@Table(name = "Trades")
public class TradeEntity {
    @Id
    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private String expiredFlag;

    //no args constructor
    public TradeEntity() {
        createdDate = LocalDate.now();
    }

    // constructor wto init the entity with trade
    public TradeEntity(Trade trade) {
        this.tradeId = trade.getTradeId();
        this.version = trade.getVersion();
        this.counterPartyId = trade.getCounterPartyId();
        this.bookId = trade.getBookId();
        this.maturityDate = trade.getMaturityDate();
        this.expiredFlag = "N";
        createdDate = LocalDate.now();
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpiredFlag() {
        return expiredFlag;
    }

    public void setExpiredFlag(String expiredFlag) {
        this.expiredFlag = expiredFlag;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + getTradeId() + '\'' +
                ", version=" + getVersion() +
                ", counterParty='" + getCounterPartyId() + '\'' +
                ", bookId='" + getBookId() + '\'' +
                ", maturityDate=" + getMaturityDate() +
                ", createdDate=" + getCreatedDate() +
                ", expiredFlag='" + getExpiredFlag() + '\'' +
                '}';
    }
}
