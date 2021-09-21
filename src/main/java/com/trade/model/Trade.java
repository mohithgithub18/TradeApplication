package com.trade.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Trade implements Serializable,Comparable<Trade> {

    public TradeIdentifier getTradeIdentifier() {
        return tradeIdentifier;
    }

    public void setTradeIdentifier(TradeIdentifier tradeIdentifier) {
        this.tradeIdentifier = tradeIdentifier;
    }

    public Trade(TradeIdentifier tradeIdentifier, String counterPartyId, String bookingId, LocalDate maturityDate, LocalDate createdDate, boolean expired) {
        this.tradeIdentifier = tradeIdentifier;
        this.counterPartyId = counterPartyId;
        this.bookingId = bookingId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    public Trade(TradeIdentifier tradeIdentifier) {
        this.tradeIdentifier = tradeIdentifier;
    }

    private TradeIdentifier tradeIdentifier;
    private String counterPartyId;
    private String bookingId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private boolean expired;

    public Trade() {
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return tradeIdentifier.equals(trade.tradeIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeIdentifier);
    }

    @Override
    public int compareTo(Trade trade) {
        return this.tradeIdentifier.getVersion() - trade.tradeIdentifier.getVersion();
    }
}
