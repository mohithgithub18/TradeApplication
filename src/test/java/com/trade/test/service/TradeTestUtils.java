package com.trade.test.service;

import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

public class TradeTestUtils {
    public static final String TRADE_ID_1 = "T1";
    public static final String TRADE_ID_2 = "T2";

    public static SortedSet<Trade> getTradeSet(TradeIdentifier tradeIdentifier) {
        SortedSet<Trade> tradeSet = new TreeSet();
        tradeSet.add(getTrade(tradeIdentifier));
        return tradeSet;
    }

    public static Trade getTrade(TradeIdentifier tradeIdentifier) {
        Trade trade = new Trade(tradeIdentifier, "CP-1", "B1", LocalDate.now(), LocalDate.now(), false);
        return trade;
    }

}
