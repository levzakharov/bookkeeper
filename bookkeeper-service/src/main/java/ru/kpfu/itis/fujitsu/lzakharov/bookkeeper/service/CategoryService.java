package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;

import java.util.List;

/**
 * Category Service interface.
 * Provides logic to operate on the data sent to and from the DAO and the client.
 */
public interface CategoryService {

    /**
     * Returns all categories.
     *
     * @return list contains all categories, may be empty
     */
    List<Category> getAll();
}
