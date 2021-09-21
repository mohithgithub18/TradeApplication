package com.trade.test.service;

import com.trade.dao.TradeRepository;
import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.SortedSet;

import static com.trade.test.service.TradeTestUtils.getTrade;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeRepositoryTest {

    @Autowired
    TradeRepository tradeRepository;

    private TradeIdentifier tradeIdentifier;

    @Test
    public void testValidNewTradeSaveOrUpdate() {
        HashMap<String, SortedSet<Trade>> tradeStore = new HashMap<>();
        tradeIdentifier = new TradeIdentifier("T10", 1);
        Trade trade = getTrade(tradeIdentifier);
        tradeStore = tradeRepository.saveOrUpdate(trade);

        assertNotNull(tradeStore.get(tradeIdentifier.getTradeId()));
        SortedSet<Trade> tradeSet = tradeStore.get(tradeIdentifier.getTradeId());
        assertFalse(tradeSet.isEmpty());
        assertTrue(tradeSet.contains(trade));
    }

    @Test
    public void testSaveUpdateExistingTradeNewVersion() {
        HashMap<String, SortedSet<Trade>> tradeStore = new HashMap<>();
        tradeIdentifier = new TradeIdentifier("T1", 4);
        Trade trade = getTrade(tradeIdentifier);
        tradeStore = tradeRepository.saveOrUpdate(trade);

        assertNotNull(tradeStore.get(tradeIdentifier.getTradeId()));
        SortedSet<Trade> tradeSet = tradeStore.get(tradeIdentifier.getTradeId());
        assertFalse(tradeSet.isEmpty());
        assertTrue(tradeSet.contains(trade));
        assertEquals(4, tradeSet.size());
    }

    @Test
    public void testSaveUpdateExistingTradeSameOldVersion() {
        HashMap<String, SortedSet<Trade>> tradeStore = new HashMap<>();
        assertTrue(CollectionUtils.isEmpty(tradeStore));
        tradeIdentifier = new TradeIdentifier("T1", 2);
        Trade trade = getTrade(tradeIdentifier);
        tradeStore = tradeRepository.saveOrUpdate(trade);

        assertNotNull(tradeStore.get(tradeIdentifier.getTradeId()));
        SortedSet<Trade> tradeSet = tradeStore.get(tradeIdentifier.getTradeId());
        assertFalse(tradeSet.isEmpty());
        assertTrue(tradeSet.contains(trade));
        assertEquals(3, tradeSet.size());
    }

}
