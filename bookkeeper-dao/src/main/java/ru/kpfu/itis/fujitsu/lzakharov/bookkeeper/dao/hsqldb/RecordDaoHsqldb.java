package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import org.apache.log4j.Logger;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.RecordDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RecordDaoHsqldb extends GenericDaoHsqldb<Record> implements RecordDao {
    final static Logger log = Logger.getLogger(RecordDaoHsqldb.class.getName());

    @Override
    protected String getModelName() {
        return "RECORD";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE ID = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE RECORD " +
                "SET CLIENT_ID = ?, CATEGORY_ID = ?, TYPE = ?, AMOUNT = ?, DESCRIPTION = ?, CREATION_DATE = ? " +
                "WHERE ID = ?";
    }

    @Override
    protected String getAddQuery() {
        return "INSERT INTO RECORD (CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected List<Record> parseResultSet(ResultSet rs) {
        List<Record> records = new LinkedList<>();

        try {
            while (rs.next()) {
                records.add(new Record(rs.getLong("ID"), rs.getLong("CLIENT_ID"), rs.getLong("CATEGORY_ID"),
                        Type.valueOf(rs.getString("TYPE")), rs.getInt("AMOUNT"),
                        rs.getString("DESCRIPTION"), rs.getDate("CREATION_DATE")));
            }
        } catch (SQLException e) {
            String msg = "Error parsing ResultSet";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }

        return records;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pstmt, Record model) {
        try {
            pstmt.setLong(1, model.getClientId());
            pstmt.setLong(2, model.getCategoryId());
            pstmt.setString(3, model.getType().toString());
            pstmt.setInt(4, model.getAmount());
            pstmt.setString(5, model.getDescription());
            pstmt.setDate(6, model.getCreationDate());
            pstmt.setLong(7, model.getId());
        } catch (SQLException e) {
            String msg = "Error preparing statement for update";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    protected void prepareStatementForAdd(PreparedStatement pstmt, Record model) {
        try {
            pstmt.setLong(1, model.getClientId());
            pstmt.setLong(2, model.getCategoryId());
            pstmt.setString(3, model.getType().toString());
            pstmt.setInt(4, model.getAmount());
            pstmt.setString(5, model.getDescription());
            pstmt.setDate(6, model.getCreationDate());
        } catch (SQLException e) {
            String msg = "Error preparing statement for add";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Record> getAll(long clientId) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE CLIENT_ID = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);

            ResultSet rs = pstmt.executeQuery();
            List<Record> records = parseResultSet(rs);
            rs.close();

            return records;
        } catch (SQLException e) {
            String msg = "Error retrieving " + getModelName() + "s with client's id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Record> getIncomeList(long clientId) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE CLIENT_ID = ? AND TYPE = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.INCOME.toString());

            ResultSet rs = pstmt.executeQuery();
            List<Record> records = parseResultSet(rs);
            rs.close();

            return records;
        } catch (SQLException e) {
            String msg = "Error retrieving income list for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Record> getExpenditureList(long clientId) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE CLIENT_ID = ? AND TYPE = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.EXPENDITURE.toString());

            ResultSet rs = pstmt.executeQuery();
            List<Record> records = parseResultSet(rs);
            rs.close();

            return records;
        } catch (SQLException e) {
            String msg = "Error retrieving expenditure list for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getMonthlyIncome(long clientId, int month) {
        String sql = "SELECT SUM(AMOUNT) FROM RECORD " +
                "WHERE CLIENT_ID = ? AND TYPE = ? AND MONTH(CREATION_DATE) = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.INCOME.toString());
            pstmt.setInt(3, month);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Long amount = rs.getLong(1);
            rs.close();

            return amount;
        } catch (SQLException e) {
            String msg = "Error retrieving monthly income for " + month + "s month " +
                    "of client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Record> getMonthlyIncomeList(long clientId, int month) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE CLIENT_ID = ? AND TYPE = ? AND MONTH(CREATION_DATE) = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.INCOME.toString());
            pstmt.setInt(3, month);

            ResultSet rs = pstmt.executeQuery();
            List<Record> records = parseResultSet(rs);
            rs.close();

            return records;
        } catch (SQLException e) {
            String msg = "Error retrieving monthly income list for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<Record> getMonthlyExpenditureList(long clientId, int month) {
        String sql = "SELECT ID, CLIENT_ID, CATEGORY_ID, TYPE, AMOUNT, DESCRIPTION, CREATION_DATE " +
                "FROM RECORD WHERE CLIENT_ID = ? AND TYPE = ? AND MONTH(CREATION_DATE) = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.EXPENDITURE.toString());
            pstmt.setInt(3, month);

            ResultSet rs = pstmt.executeQuery();
            List<Record> records = parseResultSet(rs);
            rs.close();

            return records;
        } catch (SQLException e) {
            String msg = "Error retrieving monthly expenditure list for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getMonthlyExpenditure(long clientId, int month) {
        String sql = "SELECT SUM(AMOUNT) FROM RECORD " +
                "WHERE CLIENT_ID = ? AND TYPE = ? AND MONTH(CREATION_DATE) = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.EXPENDITURE.toString());
            pstmt.setInt(3, month);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Long amount = rs.getLong(1);
            rs.close();

            return amount;
        } catch (SQLException e) {
            String msg = "Error retrieving monthly expenditure for " + month + "s month " +
                    "of client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getTotalIncome(long clientId) {
        String sql = "SELECT SUM(AMOUNT) FROM RECORD " +
                "WHERE CLIENT_ID = ? AND TYPE = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.INCOME.toString());

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Long amount = rs.getLong(1);
            rs.close();

            return amount;
        } catch (SQLException e) {
            String msg = "Error retrieving total income for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getTotalExpenditure(long clientId) {
        String sql = "SELECT SUM(AMOUNT) FROM RECORD " +
                "WHERE CLIENT_ID = ? AND TYPE = ?";

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setString(2, Type.EXPENDITURE.toString());

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Long amount = rs.getLong(1);
            rs.close();

            return amount;
        } catch (SQLException e) {
            String msg = "Error retrieving total expenditure for client with id '" + clientId + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public Long getCurrentBalance(long clientId) {
        return getTotalIncome(clientId) - getTotalExpenditure(clientId);
    }

}
