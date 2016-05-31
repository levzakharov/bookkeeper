package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;

import java.util.List;

public interface RecordDao extends GenericDao<Record> {
    /**
     * Returns all records of specified by id client.
     *
     * @param clientId the client's id of records to get
     * @return list contains all records with specified client's id, may be empty
     */
    List<Record> getAll(long clientId);

    /**
     * Returns all income records of specified by id client.
     *
     * @param clientId the client's id
     * @return list of all income records of specified by id client
     */
    List<Record> getIncomeList(long clientId);

    /**
     * Returns all expenditure records of specified by id client.
     *
     * @param clientId the client's id
     * @return list of all expenditure records of specified by id client
     */
    List<Record> getExpenditureList(long clientId);

    /**
     * Returns monthly income for specified month of specified by id client.
     *
     * @param clientId the client's id
     * @param month    number of month
     * @return amount of incomes for a specified month of specified by id client
     */
    Long getMonthlyIncome(long clientId, int month);

    /**
     * Returns income records for specified month of specified by id client.
     *
     * @param clientId the client's id
     * @return list of income records for specified month of specified by id client
     */
    List<Record> getMonthlyIncomeList(long clientId, int month);

    /**
     * Returns expenditure records for specified month of specified by id client.
     *
     * @param clientId the client's id
     * @return list of expenditure records for specified month of specified by id client
     */
    List<Record> getMonthlyExpenditureList(long clientId, int month);


    /**
     * Returns monthly expenditure for specified month of specified by id client.
     *
     * @param clientId the client's id
     * @param month    number of month
     * @return amount of expenditures for a specified month of specified by id client
     */
    Long getMonthlyExpenditure(long clientId, int month);

    /**
     * Returns total income of specified by id client.
     *
     * @param clientId the client's id
     * @return total income of specified by id client
     */
    Long getTotalIncome(long clientId);

    /*
     * Returns total expenditure of specified by id client.
     *
     * @param clientId the client's id
     * @return total expenditure of specified by id client
     */
    Long getTotalExpenditure(long clientId);

    /**
     * Returns current balance for specified by id client.
     *
     * @param clientId the client's id
     * @return current balance for specified by id client
     */
    Long getCurrentBalance(long clientId);

    /*
     * Returns monthly income of specified by id client for specified category
     *
     * @param clientId the client's id
     * @param categoryId the id of category
     * @return monthly income of specified by id client for specified category
     */
    Long getMonthlyIncomeForCategory(Long clientId, Long categoryId, int month);

    /*
     * Returns monthly expenditure of specified by id client for specified category.
     *
     * @param clientId the client's id
     * @param categoryId the id of category
     * @return monthly expenditure of specified by id client for specified category
     */
    Long getMonthlyExpenditureForCategory(Long clientId, Long categoryId, int month);

    /**
     * Returns total average income of specified by id client.
     *
     * @param clientId the client's id
     * @return total average income of specified by id client
     */
    Long getTotalAverageIncome(Long clientId);

    /**
     * Returns total average expenditure of specified by id client.
     *
     * @param clientId the client's id
     * @return total average expenditure of specified by id client
     */
    Long getTotalAverageExpenditure(Long clientId);
}

