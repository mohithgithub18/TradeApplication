package com.trade.test.service;

import com.trade.dao.TradeRepository;
import com.trade.exception.TradeMaturityDateExpiredException;
import com.trade.exception.TradeRejectException;
import com.trade.model.Trade;
import com.trade.model.TradeIdentifier;
import com.trade.service.TradeServiceImpl;
import com.trade.utils.TradeMessages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.SortedSet;

import static com.trade.test.service.TradeTestUtils.getTrade;
import static com.trade.test.service.TradeTestUtils.getTradeSet;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceImplValidatorTest {

    @Autowired
    private TradeServiceImpl tradeServiceImpl;

    @Mock
    private TradeRepository tradeRepository;

    private TradeIdentifier tradeIdentifier;

    @Before
    public void before() {
        tradeIdentifier = new TradeIdentifier(TradeTestUtils.TRADE_ID_1, 1);
        when(tradeRepository.findTrades(TradeTestUtils.TRADE_ID_1)).thenReturn(getTradeSet(tradeIdentifier));
    }

    @Test
    public void testGetTradeValid() {
        SortedSet<Trade> tradeSet = tradeServiceImpl.getTrade(TradeTestUtils.TRADE_ID_1);
        assertFalse(CollectionUtils.isEmpty(tradeSet));
    }

    @Test
    public void testGetTradeNull() {
        SortedSet<Trade> tradeSet = tradeServiceImpl.getTrade(TradeTestUtils.TRADE_ID_2);
        assertTrue(CollectionUtils.isEmpty(tradeSet));
        assertEquals(null, tradeSet);
    }

    @Test
    public void testCreateTradeValid() throws Exception {
        tradeIdentifier = new TradeIdentifier("T3", 1);
        Trade newTrade = getTrade(tradeIdentifier);
        String returnMsg = tradeServiceImpl.createTrade(newTrade);
        assertEquals(TradeMessages.TRADE_CREATED, returnMsg);
    }

    @Test
    public void testUpdateTradeExistingVersion() throws Exception {
        tradeIdentifier = new TradeIdentifier("T1", 5);
        Trade newTrade = getTrade(tradeIdentifier);
        newTrade.setBookingId("B5");
        newTrade.setCounterPartyId("C1-5");
        String returnMsg = tradeServiceImpl.createTrade(newTrade);
        SortedSet updatedTradeSet = tradeRepository.findTrades(TradeTestUtils.TRADE_ID_1);
        assertEquals(TradeMessages.TRADE_CREATED, returnMsg);
        assertFalse(updatedTradeSet.isEmpty());
    }

    @Test(expected = TradeRejectException.class)
    public void testCreateTradeRejectExceptionValidator() throws Exception {
        Trade newTrade = getTrade(tradeIdentifier);
        tradeServiceImpl.createTrade(newTrade);
    }

    @Test(expected = TradeMaturityDateExpiredException.class)
    public void testMaturityDateExpiredExceptionValidator() throws Exception {
        tradeIdentifier = new TradeIdentifier("T5", 1);
        Trade newTrade = getTrade(tradeIdentifier);
        newTrade.setMaturityDate(LocalDate.of(2021, 03, 01));
        tradeServiceImpl.createTrade(newTrade);
    }
}
