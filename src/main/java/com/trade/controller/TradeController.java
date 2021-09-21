package com.trade.controller;

import com.trade.exception.TradeMaturityDateExpiredException;
import com.trade.exception.TradeRejectException;
import com.trade.model.Trade;
import com.trade.service.TradeServiceImpl;
import com.trade.utils.TradeMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.SortedSet;

@RestController
@RequestMapping(path = "/trade")
public class TradeController {

    @Autowired
    private TradeServiceImpl tradeServiceImpl;

    @GetMapping(value = "/health")
    public String healthCheck() {
        return TradeMessages.HEALTH_CHECK;
    }

    @GetMapping(value = "/{tradeId}")
    public SortedSet<Trade> getTrade(@PathVariable String tradeId) {
        return tradeServiceImpl.getTrade(tradeId);
    }

    @GetMapping(value = "/allTrades")
    public Map<String, SortedSet<Trade>> getAllTrade() {
        return tradeServiceImpl.getTrades();
    }

    @PostMapping(value = "/create")
    public String createTrade(@RequestBody Trade trade) {
        try {
            tradeServiceImpl.createTrade(trade);
        } catch (TradeRejectException tradeRejectException) {
            throw new ResponseStatusException(1001, TradeMessages.TRADE_REJECT, tradeRejectException);
        } catch (TradeMaturityDateExpiredException tradeMaturityDateExpiredException) {
            throw new ResponseStatusException(1002, TradeMessages.TRADE_MATURITY_DATE_EXPIRED, tradeMaturityDateExpiredException);
        } catch (Exception exception) {
            throw new ResponseStatusException(400, TradeMessages.TRADE_BAD_REQUEST, exception);
        }
        return TradeMessages.TRADE_CREATED;
    }
}
