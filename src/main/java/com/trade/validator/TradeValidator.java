package com.trade.validator;

import com.trade.dao.TradeRepository;
import com.trade.exception.TradeMaturityDateExpiredException;
import com.trade.exception.TradeRejectException;
import com.trade.model.Trade;
import com.trade.utils.TradeMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.SortedSet;

@Component
public class TradeValidator {

    @Autowired
    private TradeRepository tradeRepository;

    public boolean isValid(Trade newTrade) throws Exception {
        if (isTradeObjectInValid(newTrade)) {
            throw new Exception(TradeMessages.TRADE_BAD_REQUEST);
        } else if (!isMaturityDateValid(newTrade)) {
            throw new TradeMaturityDateExpiredException(TradeMessages.TRADE_MATURITY_DATE_EXPIRED);
        }

        SortedSet<Trade> tradeSet = tradeRepository.findTrades(newTrade.getTradeIdentifier().getTradeId());
        if (!CollectionUtils.isEmpty(tradeSet)) {
            Trade previousLatestTrade = tradeSet.last();
            if (!isTradeVersionValid(previousLatestTrade, newTrade)) {
                throw new TradeRejectException(TradeMessages.TRADE_REJECT);
            }
        }
        return true;
    }

    private boolean isTradeObjectInValid(Trade trade) {
        return (trade.getTradeIdentifier() == null ||
                trade.getTradeIdentifier().getTradeId() == null ||
                trade.getTradeIdentifier().getTradeId().isEmpty());
    }

    private boolean isTradeVersionValid(Trade previousLatestTrade, Trade newTrade) {
        return newTrade.getTradeIdentifier().getVersion() >= previousLatestTrade.getTradeIdentifier().getVersion();
    }

    private boolean isMaturityDateValid(Trade newTrade) {
        return (newTrade.getMaturityDate().isAfter(LocalDate.now()) || newTrade.getMaturityDate().equals(LocalDate.now()));
    }
}
