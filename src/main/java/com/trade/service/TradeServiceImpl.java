package com.trade.service;

import com.trade.dao.TradeRepository;
import com.trade.model.Trade;
import com.trade.utils.TradeMessages;
import com.trade.validator.TradeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.SortedSet;

@Service
public class TradeServiceImpl {
    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeValidator tradeValidator;

    public SortedSet<Trade> getTrade(String tradeId) {
        return tradeRepository.findTrades(tradeId);
    }

    public Map<String, SortedSet<Trade>> getTrades() {
        return tradeRepository.findAll();
    }

    public String createTrade(Trade trade) throws Exception {
        String returnMsg = TradeMessages.TRADE_CREATION_ERROR;
        if (tradeValidator.isValid(trade)) {
            tradeRepository.saveOrUpdate(trade);
            returnMsg = TradeMessages.TRADE_CREATED;
        }
        return returnMsg;
    }
}
