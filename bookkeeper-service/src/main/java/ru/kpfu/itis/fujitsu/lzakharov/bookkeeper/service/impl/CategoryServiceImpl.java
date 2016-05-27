package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.CategoryDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;

    public CategoryServiceImpl() {
        this.categoryDao = new CategoryDaoHsqldb();
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
