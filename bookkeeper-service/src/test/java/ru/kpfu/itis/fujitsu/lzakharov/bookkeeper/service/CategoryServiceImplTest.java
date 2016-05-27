package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.CategoryServiceImpl;

import java.util.LinkedList;
import java.util.List;

public class CategoryServiceImplTest {

    @Test
    public void testGetAll() {
        List<Category> categories = new LinkedList<>();
        categories.add(new Category(0L, "category0"));
        categories.add(new Category(1L, "category1"));

        CategoryDao categoryDao = mock(CategoryDao.class);
        when(categoryDao.getAll()).thenReturn(categories);

        CategoryService categoryService = new CategoryServiceImpl();
        ((CategoryServiceImpl) categoryService).setCategoryDao(categoryDao);

        assertEquals(categories, categoryService.getAll());
    }
}
