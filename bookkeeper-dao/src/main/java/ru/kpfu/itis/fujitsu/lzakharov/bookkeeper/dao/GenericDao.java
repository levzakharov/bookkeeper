package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.AbstractModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T extends AbstractModel> {
    /**
     * Gets connection.
     *
     * @return connection from pool
     * @throws SQLException
     */
     Connection getConnection() throws SQLException;

    /**
     * Returns the specified object.
     *
     * @param id the identifier (primary key) of the object to get
     * @return the object or {@code null} if nothing found
     */
    T get(long id);

    /**
     * Returns all the objects.
     *
     * @return list contains all the objects, may be empty
     */
    List<T> getAll();

    /**
     * Updates the specified by {@code model}'s id object.
     *
     * @param model the object to update
     * @return updated object or {@code null} if model with specified id not exists;
     */
    T update(T model);

    /**
     * Adds the specified object.
     *
     * @param model the object to add
     * @return id of added model
     */
    long add(T model);

    /**
     * Removes the specified object.
     *
     * @param id the identifier (primary key) of the object to remove
     * @return {@code true} if object removed;
     *         {@code false} otherwise
     */
    boolean remove(long id);

    /**
     * Returns count of all objects.
     *
     * @return the number of all objects.
     */
    long getCount();

}
