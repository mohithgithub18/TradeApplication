package com.trade.scheduler;

import com.trade.dao.TradeRepository;
import com.trade.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class TradeScheduler {

    private static final Logger LOGGER = Logger.getLogger(TradeScheduler.class.getName());

    @Autowired
    private TradeRepository tradeRepository;

    @Scheduled(cron = "0 0 12 * * ?") //This runs 12PM everyday - can also be configured in application.properties
    public void manageExpiredTrades() {
        LOGGER.info("****** START : Scheduler trade Expiry process ******");
        Map<String, SortedSet<Trade>> allTradeMap = tradeRepository.findAll();
        for (Map.Entry<String, SortedSet<Trade>> entry : allTradeMap.entrySet()) {
            updateExpiredFlag(entry.getValue());
        }
        LOGGER.info("****** END : Scheduler trade Expiry process ******");
    }

    public Set<Trade> updateExpiredFlag(Set<Trade> tradeSet) {
        tradeSet = tradeSet.stream()
                .filter(trade -> trade.isExpired() == false)
                .collect(Collectors.toSet());
        for (Trade trade : tradeSet) {
            if (trade.getMaturityDate().isBefore(LocalDate.now())) {
                trade.setExpired(Boolean.TRUE);
            }
        }
        return tradeSet;
    }
}
