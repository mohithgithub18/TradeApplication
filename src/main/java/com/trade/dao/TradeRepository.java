package com.trade.dao;

import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;
import com.trade.utils.TradeDataStoreUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

@Repository
public class TradeRepository {
    public HashMap<String, SortedSet<Trade>> tradeStore;

    public TradeRepository() {
        tradeStore = new HashMap<>();
        TradeDataStoreUtils.loadTrades(tradeStore);
    }

    public HashMap<String, SortedSet<Trade>> saveOrUpdate(Trade newTrade) {
        TradeIdentifier tradeIdentifier = newTrade.getTradeIdentifier();
        SortedSet<Trade> tradeSet = tradeStore.get(tradeIdentifier.getTradeId());
        if (CollectionUtils.isEmpty(tradeSet)) {
            tradeSet = new TreeSet<>();
            setDefaultTradeObject(newTrade);
        } else {
            Trade currentTrade = findTrade(tradeSet, tradeIdentifier.getVersion());
            if (null != currentTrade) {
                updateTradeObject(currentTrade, newTrade);
                tradeSet.remove(currentTrade);
                newTrade = currentTrade;
            }
        }
        tradeSet.add(newTrade);
        tradeStore.put(newTrade.getTradeIdentifier().getTradeId(), tradeSet);
        return tradeStore;
    }

    private Trade setDefaultTradeObject(Trade trade) {
        trade.setCreatedDate(LocalDate.now());
        trade.setExpired(false);
        return trade;
    }

    private Trade updateTradeObject(Trade currentTrade, Trade newTrade) {
        currentTrade.setMaturityDate(newTrade.getMaturityDate());
        currentTrade.setBookingId(newTrade.getBookingId());
        currentTrade.setCounterPartyId(newTrade.getCounterPartyId());
        currentTrade.setExpired(newTrade.isExpired());
        currentTrade.setCreatedDate(newTrade.getCreatedDate());
        return currentTrade;
    }

    public Trade findTrade(SortedSet<Trade> trades, int version) {
        boolean isPresent = false;
        Trade trade = null;
        Iterator<Trade> i = trades.iterator();
        while (i.hasNext()) {
            trade = i.next();
            if (version == trade.getTradeIdentifier().getVersion()) {
                isPresent = true;
                break;
            }
        }
        return Boolean.TRUE.equals(isPresent) ? trade : null;
    }

    public Map<String, SortedSet<Trade>> findAll() {
        return tradeStore;
    }

    public SortedSet<Trade> findTrades(String tradeId) {
        return tradeStore.get(tradeId);
    }

}
