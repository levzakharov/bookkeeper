package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;

import java.util.List;

public interface IncomeDao extends GenericDao<Income> {
    /**
     * Returns all incomes of specified by id client.
     *
     * @param clientId the client's id of incomes to get
     * @return list contains all incomes with specified client's id, may be empty
     */
    List<Income> getByClientId(long clientId);

    /**
     * Returns amount of income for a specified month of specified by id client.
     *
     * @param clientId the client's id
     * @param month    number of month
     * @return amount of incomes for a specified month of specified by id client.
     */
    Long getClientMonthAmount(long clientId, int month);
}
