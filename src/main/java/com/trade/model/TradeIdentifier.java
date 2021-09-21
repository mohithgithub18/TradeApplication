package com.trade.model;

import java.io.Serializable;
import java.util.Objects;

public class TradeIdentifier implements Serializable {
    private String tradeId;
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeIdentifier)) return false;
        TradeIdentifier that = (TradeIdentifier) o;
        return getVersion() == that.getVersion() && getTradeId().equals(that.getTradeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTradeId(), getVersion());
    }

    public TradeIdentifier(String tradeId, int version) {
        this.tradeId = tradeId;
        this.version = version;
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

}
