package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import org.junit.Test;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.IncomeDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.IncomeServiceImpl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncomeServiceImplTest {
    @Test
    public void testCreate() {
        IncomeDao incomeDao = mock(IncomeDao.class);
        when(incomeDao.add(anyObject())).thenReturn(0L);

        IncomeService incomeService = new IncomeServiceImpl();
        ((IncomeServiceImpl) incomeService).setIncomeDao(incomeDao);

        assertEquals(0L, incomeService.create(new Income()));
    }

    @Test
    public void testFind() {
        Client client = new Client(0L, "login", "password", Gender.M);
        Category category = new Category("category0");

        List<Income> incomes = new LinkedList<>();
        incomes.add(new Income(0L, 0L, 0L, 100, "description0", Date.valueOf("1999-12-31")));
        incomes.add(new Income(1L, 0L, 0L, 100, "description1", Date.valueOf("2000-01-01")));

        IncomeDao incomeDao = mock(IncomeDao.class);
        when(incomeDao.getByClientId(0L)).thenReturn(incomes);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get("login")).thenReturn(client);

        CategoryDao categoryDao = mock(CategoryDao.class);
        when(categoryDao.get(0L)).thenReturn(category);

        IncomeService incomeService = new IncomeServiceImpl();
        ((IncomeServiceImpl) incomeService).setIncomeDao(incomeDao);
        ((IncomeServiceImpl) incomeService).setClientDao(clientDao);
        ((IncomeServiceImpl) incomeService).setCategoryDao(categoryDao);

        assertEquals(incomes, incomeService.find("login"));

    }

    @Test
    public void testGetClientMonthAmount() {
        Client client = new Client(0L, "login", "password", Gender.M);

        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get("login")).thenReturn(client);

        IncomeDao incomeDao = mock(IncomeDao.class);
        when(incomeDao.getClientMonthAmount(client.getId(), 12)).thenReturn(250L);

        IncomeService incomeService = new IncomeServiceImpl();
        ((IncomeServiceImpl) incomeService).setIncomeDao(incomeDao);
        ((IncomeServiceImpl) incomeService).setClientDao(clientDao);

        assertEquals(250L, incomeDao.getClientMonthAmount(client.getId(), 12).longValue());
    }
}
