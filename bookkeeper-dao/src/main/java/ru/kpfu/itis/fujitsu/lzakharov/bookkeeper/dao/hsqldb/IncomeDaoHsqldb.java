package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.IncomeDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class IncomeDaoHsqldb extends GenericDaoHsqldb<Income> implements IncomeDao {
    @Override
    protected String getModelName() {
        return "INCOME";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT ID, CLIENT_ID, CATEGORY_ID, PRICE, DESCRIPTION, CREATION_DATE " +
                "FROM INCOME WHERE ID = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT ID, CLIENT_ID, CATEGORY_ID, PRICE, DESCRIPTION, CREATION_DATE " +
                "FROM INCOME";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE INCOME " +
                "SET CLIENT_ID = ?, CATEGORY_ID = ?, PRICE = ?, DESCRIPTION = ?, CREATION_DATE = ? " +
                "WHERE ID = ?";
    }

    @Override
    protected String getAddQuery() {
        return "INSERT INTO INCOME (CLIENT_ID, CATEGORY_ID, PRICE, DESCRIPTION, CREATION_DATE) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected List<Income> parseResultSet(ResultSet rs) {
        List<Income> list = new LinkedList<>();

        try {
            while (rs.next()) {
                list.add(new Income(rs.getLong("ID"), rs.getLong("CLIENT_ID"), rs.getLong("CATEGORY_ID"), rs.getInt("PRICE"),
                        rs.getString("DESCRIPTION"), rs.getDate("CREATION_DATE")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error parsing ResultSet", e);
        }

        return list;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pstmt, Income model) {
        try {
            pstmt.setLong(1, model.getClientId());
            pstmt.setLong(2, model.getCategoryId());
            pstmt.setInt(3, model.getPrice());
            pstmt.setString(4, model.getDescription());
            pstmt.setDate(5, model.getCreationDate());
            pstmt.setLong(6, model.getId());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for update", e);
        }
    }

    @Override
    protected void prepareStatementForAdd(PreparedStatement pstmt, Income model) {
        try {
            pstmt.setLong(1, model.getClientId());
            pstmt.setLong(2, model.getCategoryId());
            pstmt.setInt(3, model.getPrice());
            pstmt.setString(4, model.getDescription());
            pstmt.setDate(5, model.getCreationDate());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for update", e);
        }
    }
}
