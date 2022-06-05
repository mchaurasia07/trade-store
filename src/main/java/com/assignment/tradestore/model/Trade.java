package com.assignment.tradestore.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;


public class Trade {
    @NotNull(message = "tradeId is mandatory")
    @Size(min = 1, max = 255, message = "tradeId length must be between 1 to 255")
    private String tradeId;

    @NotNull(message = "version is mandatory")
    @Min(value = 1, message = "version must be greater than or equal to 1")
    @Max(value = 2147483647, message = "version must be less than or equal to 2147483647")
    private int version;

    @NotNull(message = "counterPartyId is mandatory")
    @Size(min = 1, max = 255, message = "counterPartyId length must be between 1 to 255")
    private String counterPartyId;

    @NotNull(message = "bookId is mandatory")
    @Size(min = 1, max = 255, message = "bookId length must be between 1 to 255")
    private String bookId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "maturityDate is mandatory. supported format is yyyy-MM-dd")
    private LocalDate maturityDate;

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

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterParty='" + counterPartyId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                '}';
    }
}
