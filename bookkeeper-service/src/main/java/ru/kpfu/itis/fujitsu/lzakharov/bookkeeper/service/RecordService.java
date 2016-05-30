package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;

import java.util.List;

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
}
