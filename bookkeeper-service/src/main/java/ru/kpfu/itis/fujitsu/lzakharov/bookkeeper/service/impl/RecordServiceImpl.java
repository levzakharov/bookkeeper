package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.RecordDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.CategoryDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.RecordDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Coordinate;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordServiceImpl implements RecordService {
    private RecordDao recordDao;
    private CategoryDao categoryDao;
    private ClientDao clientDao;

    public RecordServiceImpl() {
        recordDao = new RecordDaoHsqldb();
        categoryDao = new CategoryDaoHsqldb();
        clientDao = new ClientDaoHsqldb();
    }

    public void setRecordDao(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public long create(Record record) {
        return recordDao.add(record);
    }

    @Override
    public List<Record> get(String login) {
        Client client = clientDao.get(login);

        List<Record> records = recordDao.getAll(client.getId());

        for (Record record : records) {
            record.setClient(client);
            record.setCategory(categoryDao.get(record.getCategoryId()));
        }

        return records;
    }

    @Override
    public Long getBalance(String login) {
        return recordDao.getCurrentBalance(clientDao.get(login).getId());
    }

    @Override
    public Long getMonthlyIncome(String login, int month) {
        return recordDao.getMonthlyIncome(clientDao.get(login).getId(), month);
    }

    @Override
    public List<Record> getIncomeList(String login) {
        Client client = clientDao.get(login);

        List<Record> records = recordDao.getIncomeList(client.getId());

        for (Record record : records) {
            record.setClient(client);
            record.setCategory(categoryDao.get(record.getCategoryId()));
        }

        return records;
    }

    @Override
    public List<Record> getExpenditureList(String login) {
        Client client = clientDao.get(login);

        List<Record> records = recordDao.getExpenditureList(client.getId());

        for (Record record : records) {
            record.setClient(client);
            record.setCategory(categoryDao.get(record.getCategoryId()));
        }

        return records;
    }

    @Override
    public Long getMonthlyExpenditure(String login, int month) {
        return recordDao.getMonthlyExpenditure(clientDao.get(login).getId(), month);
    }

    @Override
    public boolean remove(Long id) {
        return recordDao.remove(id);
    }

    @Override
    public Map<String, Long> getMonthlyIncomeData(String login, int month) {
        Client client = clientDao.get(login);
        HashMap<String, Long> data = new HashMap<>();

        List<Category> categories = categoryDao.getAll();

        for (Category category : categories) {
            data.put(category.getName(), recordDao.getMonthlyIncomeForCategory(client.getId(), category.getId(), month));
        }

        return data;
    }

    @Override
    public Map<String, Long> getMonthlyExpenditureData(String login, int month) {
        Client client = clientDao.get(login);
        HashMap<String, Long> data = new HashMap<>();

        List<Category> categories = categoryDao.getAll();

        for (Category category : categories) {
            data.put(category.getName(), recordDao.getMonthlyExpenditureForCategory(client.getId(), category.getId(), month));
        }

        return data;
    }

    @Override
    public Long getTotalIncome(String login) {
        return recordDao.getTotalIncome(clientDao.get(login).getId());
    }

    @Override
    public Long getTotalExpenditure(String login) {
        return recordDao.getTotalExpenditure(clientDao.get(login).getId());
    }

    @Override
    public Long getTotalAverageIncome(String login) {
        return recordDao.getTotalAverageIncome(clientDao.get(login).getId());
    }

    @Override
    public Long getTotalAverageExpenditure(String login) {
        return recordDao.getTotalAverageExpenditure(clientDao.get(login).getId());
    }

    @Override
    public List<Coordinate> getTotalMonthlyBalanceData(String login) {
        return recordDao.getTotalMonthlyBalanceData(clientDao.get(login).getId());
    }
}
