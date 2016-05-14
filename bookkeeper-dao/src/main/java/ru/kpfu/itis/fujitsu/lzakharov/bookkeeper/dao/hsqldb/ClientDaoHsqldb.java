package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClientDaoHsqldb extends GenericDaoHsqldb<Client> {
    @Override
    protected String getModelName() {
        return "Client";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT ID, LOGIN, PASSWORD, GENDER " +
                "FROM CLIENT WHERE ID = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT ID, LOGIN, PASSWORD, GENDER " +
                "FROM CLIENT";
    }

    @Override
    protected String getDeleteByIdQuery() {
        return "DELETE FROM CLIENT " +
                "WHERE ID = ?";
    }

    @Override
    protected String getCountQuery() {
        return "SELECT COUNT(*) " +
                "FROM CLIENT";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE CLIENT " +
                "SET LOGIN = ?, PASSWORD = ?, GENDER = ? " +
                "WHERE ID = ?";
    }

    @Override
    protected String getAddQuery() {
        return "INSERT INTO CLIENT (LOGIN, PASSWORD, GENDER) " +
                "VALUES (?, ?, ?)";
    }

    @Override
    protected List<Client> parseResultSet(ResultSet rs) {
        List<Client> list = new LinkedList<>();

        try {
            while (rs.next()) {
                list.add(new Client(rs.getLong("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD"), Gender.valueOf(rs.getString("GENDER"))));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error parsing ResultSet", e);
        }

        return list;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pstmt, Client model) {
        try {
            pstmt.setString(1, model.getLogin());
            pstmt.setString(2, model.getPassword());
            pstmt.setString(3, model.getGender().toString());
            pstmt.setLong(4, model.getId());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for update", e);
        }
    }

    @Override
    protected void prepareStatementForAdd(PreparedStatement pstmt, Client model) {
        try {
            pstmt.setString(1, model.getLogin());
            pstmt.setString(2, model.getPassword());
            pstmt.setString(3, model.getGender().toString());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for add", e);
        }

    }
}
