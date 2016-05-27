package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.IncomeDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.CategoryDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.IncomeDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.IncomeService;

import java.util.List;

public class IncomeServiceImpl implements IncomeService {
    private IncomeDao incomeDao;
    private ClientDao clientDao;
    private CategoryDao categoryDao;

    public IncomeServiceImpl() {
        this.incomeDao = new IncomeDaoHsqldb();
        this.clientDao = new ClientDaoHsqldb();
        this.categoryDao = new CategoryDaoHsqldb();
    }

    public void setIncomeDao(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public long create(Income income) {
        return incomeDao.add(income);
    }

    @Override
    public List<Income> find(String login) {
        Client client = clientDao.get(login);

        List<Income> incomes = incomeDao.getByClientId(client.getId());

        for (Income income: incomes) {
            income.setClient(clientDao.get(income.getClientId()));
            income.setCategory(categoryDao.get(income.getCategoryId()));
        }

        return incomes;
    }
}
