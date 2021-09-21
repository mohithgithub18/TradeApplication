package com.trade.utils;

import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class TradeDataStoreUtils {

    public static HashMap<String, SortedSet<Trade>> loadTrades(HashMap<String, SortedSet<Trade>> tradeStore) {
        TradeIdentifier tradeIdentifier1 = new TradeIdentifier("T1", 1);
        Trade trade1 = new Trade(tradeIdentifier1, "CP-1", "B1", LocalDate.now(), LocalDate.now(), false);
        TradeIdentifier tradeIdentifier2 = new TradeIdentifier("T1", 2);
        Trade trade2 = new Trade(tradeIdentifier2, "CP-1", "B1", LocalDate.of(2020, 1, 8), LocalDate.now(), false);
        TradeIdentifier tradeIdentifier3 = new TradeIdentifier("T1", 3);
        Trade trade3 = new Trade(tradeIdentifier3, "CP-3", "B1", LocalDate.of(2020, 1, 8), LocalDate.now(), true);

        SortedSet<Trade> tradeSet = new TreeSet<>();
        tradeSet.add(trade1);
        tradeSet.add(trade2);
        tradeSet.add(trade3);
        tradeStore.put("T1", tradeSet);
        return tradeStore;
    }
}
