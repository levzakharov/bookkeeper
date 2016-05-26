package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.IncomeDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;

import java.sql.Date;
import java.util.LinkedList;

public class IncomeDaoHsqldbTest extends GenericDaoHsqldbTest<Income> {
    @Override
    public void initDao() {
        this.dao = new IncomeDaoHsqldb();
    }

    @Override
    public void createTable() {
        this.table = new LinkedList<>();
        this.table.add(new Income(0L, 0L, 0L, 100, "description0", Date.valueOf("1999-12-31")));
        this.table.add(new Income(1L, 1L, 1L, 100, "description1", Date.valueOf("2000-01-01")));
    }

    @Override
    public Income objectToUpdate() {
        return new Income(0L, 0L, 1L, 200, "newDescription0", Date.valueOf("2016-01-01"));
    }

    @Override
    public Income nonexistentObject() {
        return new Income(NOT_EXISTING_ID, NOT_EXISTING_ID, NOT_EXISTING_ID, 0, "", Date.valueOf("9999-12-31"));
    }

    @Override
    public Income newObject() {
        return new Income(1L, 0L, 300, "description2", Date.valueOf("2015-12-31"));
    }
}
