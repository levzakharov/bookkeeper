package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import org.apache.log4j.Logger;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.IncomeDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class IncomeDaoHsqldb extends GenericDaoHsqldb<Income> implements IncomeDao {
    final static Logger log = Logger.getLogger(IncomeDaoHsqldb.class.getName());

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
            String msg = "Error parsing ResultSet";
            log.error(msg);
            throw new DataAccessException(msg, e);
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
            String msg = "Error preparing statement for update";
            log.error(msg);
            throw new DataAccessException(msg, e);
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
            String msg = "Error preparing statement for add";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Income> getByClientId(long clientId) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, PRICE, DESCRIPTION, CREATION_DATE " +
                "FROM INCOME WHERE CLIENT_ID = ?";
        try(Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);

            ResultSet rs = pstmt.executeQuery();
            List<Income> list = parseResultSet(rs);
            rs.close();

            return list;
        } catch (SQLException e) {
            String msg = "Error retrieving " + getModelName() + "s with client's id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getClientMonthAmount(long clientId, int month) {
        String sql = "SELECT SUM(PRICE) FROM INCOME " +
                "WHERE CLIENT_ID = ? AND ? = MONTH(CREATION_DATE)";

        try(Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setInt(2, month);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Long amount = rs.getLong(1);
            rs.close();

            return amount;
        } catch (SQLException e) {
            String msg = "Error retrieving amount of income for '" + month + "' month " +
                    "for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }
}
