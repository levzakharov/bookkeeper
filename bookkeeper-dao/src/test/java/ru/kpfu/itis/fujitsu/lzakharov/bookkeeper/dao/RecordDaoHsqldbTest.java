package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.junit.Test;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.RecordDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Type;

import java.sql.Date;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class RecordDaoHsqldbTest extends GenericDaoHsqldbTest<Record> {
    @Override
    public void initDao() {
        this.dao = new RecordDaoHsqldb();
    }

    @Override
    public void createTable() {
        this.table = new LinkedList<>();
        this.table.add(new Record(0L, 0L, 0L, Type.INCOME, 100, "description0", Date.valueOf("2016-01-01")));
        this.table.add(new Record(1L, 0L, 1L, Type.INCOME, 200, "description1", Date.valueOf("2016-01-02")));
        this.table.add(new Record(2L, 1L, 1L, Type.EXPENDITURE, 100, "description2", Date.valueOf("2016-01-01")));
        this.table.add(new Record(3L, 1L, 0L, Type.EXPENDITURE, 200, "description3", Date.valueOf("2016-01-02")));
    }

    @Override
    public Record objectToUpdate() {
        return new Record(0L, 1L, 1L, Type.EXPENDITURE, 200, "newDescription", Date.valueOf("2016-01-02"));
    }

    @Override
    public Record nonexistentObject() {
        return new Record(NOT_EXISTING_ID, NOT_EXISTING_ID, NOT_EXISTING_ID, Type.INCOME, 0, "", Date.valueOf("9999-12-31"));
    }

    @Override
    public Record newObject() {
        return new Record(1L, 0L, Type.EXPENDITURE, 300, "description4", Date.valueOf("2016-01-03"));
    }

    @Test
    public void testGetAllForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(table.subList(0, 2), recordDao.getAll(0));
    }

    @Test
    public void testGetIncomeListForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(table.subList(0, 2), recordDao.getIncomeList(0));
    }

    @Test
    public void testGetExpenditureListForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(table.subList(2, 4), recordDao.getExpenditureList(1));
    }

    @Test
    public void testGetMonthlyIncomeForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(300L, recordDao.getMonthlyIncome(0, 1).longValue());
    }

    @Test
    public void testGetMonthlyIncomeListForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(table.subList(0, 2), recordDao.getMonthlyIncomeList(0, 1));
    }

    @Test
    public void testGetMonthlyExpenditureListForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(table.subList(2, 4), recordDao.getMonthlyExpenditureList(1, 1));
    }

    @Test
    public void testGetMonthlyExpenditureForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(300L, recordDao.getMonthlyExpenditure(1, 1).longValue());
    }

    @Test
    public void testGetTotalExpenditureForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(300L, recordDao.getTotalExpenditure(1).longValue());
    }

    @Test
    public void testGetTotalIncomeForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(300L, recordDao.getTotalIncome(0).longValue());
    }

    @Test
    public void testGetCurrentBalanceForSpecifiedByIdClient() {
        RecordDao recordDao = (RecordDaoHsqldb) dao;

        assertEquals(300L, recordDao.getCurrentBalance(0).longValue());
    }

}
