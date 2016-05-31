package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;

import java.util.List;
import java.util.Map;

/**
 * Record Service interface.
 * Provides logic to operate on the data sent to and from the DAO and the client.
 */
public interface RecordService {
    /**
     * Creates specified record.
     *
     * @param record the record to create
     * @return created record's id
     */
    long create(Record record);

    /**
     * Returns all records for specified by login client.
     *
     * @param login the client's login of records to find
     * @return list contains all records of specified by login client, may be empty
     */
    List<Record> get(String login);

    /**
     * Returns current balance for specified by login client.
     *
     * @param login the client's login
     * @return current balance for specified by login client
     */
    Long getBalance(String login);

    /**
     * Returns monthly income for specified month of specified by login client.
     *
     * @param login the client's login
     * @param month number of month
     * @return amount of incomes for a specified month of specified by login client
     */
    Long getMonthlyIncome(String login, int month);

    /**
     * Returns all income records of specified by login client.
     *
     * @param login the client's login
     * @return list of all income records of specified by login client
     */
    List<Record> getIncomeList(String login);

    /**
     * Returns all expenditures records of specified by login client.
     *
     * @param login the client's login
     * @return list of all expenditures records of specified by login client
     */
    List<Record> getExpenditureList(String login);

    /**
     * Returns monthly expenditure for specified month of specified by login client.
     *
     * @param login the client's login
     * @param month number of month
     * @return amount of expenditure for a specified month of specified by login client
     */
    Long getMonthlyExpenditure(String login, int month);

    /**
     * Removes the specified by id record.
     *
     * @param id the identifier (primary key) of the record to remove
     * @return {@code true} if object removed;
     *         {@code false} otherwise
     */
    boolean remove(Long id);

    /**
     * Returns map for specified by login client containing monthly income of this person for each category.
     *
     * @param login the client's login
     * @param month number of month
     * @return map, where keys - names of categories and values - monthly income for appropriate category
     */
    Map<String, Long> getMonthlyIncomeData(String login, int month);

    /**
     * Returns map for specified by login client containing monthly expenditure of this person for each category.
     *
     * @param login the client's login
     * @param month number of month
     * @return map, where keys - names of categories and values - monthly expenditure for appropriate category
     */
    Map<String, Long> getMonthlyExpenditureData(String login, int month);

    /**
     * Returns total income of specified by login client.
     *
     * @param login the client's login
     * @return total income of specified by login client
     */
    Long getTotalIncome(String login);

    /**
     * Returns total expenditure of specified by login client.
     *
     * @param login the client's login
     * @return total expenditure of specified by login client
     */
    Long getTotalExpenditure(String login);

    /**
     * Returns total average income of specified by login client.
     *
     * @param login the client's login
     * @return total average income of specified by login client
     */
    Long getTotalAverageIncome(String login);

    /**
     * Returns total average expenditure of specified by login client.
     *
     * @param login the client's login
     * @return total average expenditure of specified by login client
     */
    Long getTotalAverageExpenditure(String login);
}
