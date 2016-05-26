package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.CategoryDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;

import java.util.LinkedList;

public class CategoryDaoHsqldbTest extends GenericDaoHsqldbTest<Category> {
    @Override
    public void initDao() {
        this.dao = new CategoryDaoHsqldb();
    }

    @Override
    public void createTable() {
        this.table = new LinkedList<>();
        this.table.add(new Category(0L, "category0"));
        this.table.add(new Category(1L, "category1"));
    }

    @Override
    public Category objectToUpdate() {
        return new Category(0L, "newCategory");
    }

    @Override
    public Category nonexistentObject() {
        return new Category(NOT_EXISTING_ID, "category");
    }

    @Override
    public Category newObject() {
        return new Category("newCategory");
    }
}
