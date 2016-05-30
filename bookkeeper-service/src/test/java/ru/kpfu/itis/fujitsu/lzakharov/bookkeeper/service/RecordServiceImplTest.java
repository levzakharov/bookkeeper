package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import org.junit.Test;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.RecordDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Type;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.RecordServiceImpl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecordServiceImplTest {
    @Test
    public void testCreate() {
        Record record = new Record(0L, 0L, Type.INCOME, 100, "description", Date.valueOf("2016-01-01"));
        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.add(record)).thenReturn(0L);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);

        assertEquals(0L, recordService.create(record));
    }

    @Test
    public void testGetByClientLogin() {
        List<Record> records = new LinkedList<>();
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(0L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getAll(0L)).thenReturn(records);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(records, recordService.get(anyString()));
    }

    @Test
    public void testGetBalance() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(0L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getCurrentBalance(0L)).thenReturn(0L);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(0L, recordService.getBalance(anyString()).longValue());
    }

    @Test
    public void testGetMonthlyIncome() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(0L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getMonthlyIncome(0L, 1)).thenReturn(0L);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(0L, recordService.getMonthlyIncome(anyString(), 1).longValue());
    }

    @Test
    public void testGetIncomeList() {
        List<Record> records = new LinkedList<>();
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(0L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getIncomeList(0L)).thenReturn(records);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(records, recordService.getIncomeList(anyString()));
    }

    @Test
    public void testGetExpenditureList() {
        List<Record> records = new LinkedList<>();
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(1L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getIncomeList(1L)).thenReturn(records);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(records, recordService.getExpenditureList(anyString()));
    }

    @Test
    public void testGetMonthlyExpenditure() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(0L);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(client);

        RecordDao recordDao = mock(RecordDao.class);
        when(recordDao.getMonthlyExpenditure(0L, 1)).thenReturn(0L);

        RecordService recordService = new RecordServiceImpl();
        ((RecordServiceImpl) recordService).setRecordDao(recordDao);
        ((RecordServiceImpl) recordService).setClientDao(clientDao);

        assertEquals(0L, recordService.getMonthlyExpenditure(anyString(), 1).longValue());
    }

    // TODO: add test for remove() and getMonthlyIncomeData()
}
