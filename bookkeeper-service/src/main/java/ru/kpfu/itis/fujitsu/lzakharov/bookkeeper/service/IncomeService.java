package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;

import java.util.List;

/**
 * Income Service interface.
 * Provides logic to operate on the data sent to and from the DAO and the client.
 */
public interface IncomeService {
    /**
     * Creates specified income.
     *
     * @param income the income to create
     * @return created income's id
     */
    long create(Income income);

    /**
     * Returns all incomes of specified by login client.
     *
     * @param login the client's login of incomes to find
     * @return list contains all incomes of specified by login client, may be empty
     */
    List<Income> find(String login);
}
