package com.trade.test.service;

import com.trade.dao.TradeRepository;
import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;
import com.trade.scheduler.TradeScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.trade.test.service.TradeTestUtils.TRADE_ID_1;
import static com.trade.test.service.TradeTestUtils.getTrade;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeSchedulerTest {

    @Autowired
    private TradeScheduler tradeScheduler;

    @Mock
    TradeRepository tradeRepository;

    SortedSet<Trade> tradeSet = new TreeSet<>();


    public void setup() {
        TradeIdentifier tradeIdentifier1 = new TradeIdentifier(TRADE_ID_1, 1);
        TradeIdentifier tradeIdentifier2 = new TradeIdentifier(TRADE_ID_1, 2);
        Trade trade1 = getTrade(tradeIdentifier1);
        trade1.setMaturityDate(LocalDate.of(2020, 1, 1));
        Trade trade2 = getTrade(tradeIdentifier2);
        trade2.setMaturityDate(LocalDate.of(2021, 1, 11));
        tradeSet.add(trade1);
        tradeSet.add(trade2);
    }

    @Test
    public void testManageExpiredTrades() {
        assertExpiredTrades(tradeSet, false);
        tradeScheduler.updateExpiredFlag(tradeSet);
        assertExpiredTrades(tradeSet, true);
    }

    private void assertExpiredTrades(SortedSet<Trade> tradeSet, boolean isExpired) {
        Iterator<Trade> i = tradeSet.iterator();
        while (i.hasNext()) {
            Trade trade = i.next();
            assertTrue(trade.isExpired());
        }
    }

}
