package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.RecordDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.CategoryDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.RecordDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;

import java.util.List;

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

        for (Record record: records) {
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
        return recordDao.getIncomeList(clientDao.get(login).getId());
    }
}
