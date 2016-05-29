package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import org.apache.log4j.Logger;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClientDaoHsqldb extends GenericDaoHsqldb<Client> implements ClientDao {
    final static Logger log = Logger.getLogger(ClientDaoHsqldb.class.getName());

    @Override
    protected String getModelName() {
        return "CLIENT";
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
            String msg = "Error parsing ResultSet";
            log.error(msg);
            throw new DataAccessException(msg, e);
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
            String msg = "Error preparing statement for update";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    protected void prepareStatementForAdd(PreparedStatement pstmt, Client model) {
        try {
            pstmt.setString(1, model.getLogin());
            pstmt.setString(2, model.getPassword());
            pstmt.setString(3, model.getGender().toString());
        } catch (SQLException e) {
            String msg = "Error preparing statement for add";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }

    }

    @Override
    public Client get(String login) {
        String sql = "SELECT ID, LOGIN, PASSWORD, GENDER " +
                     "FROM CLIENT WHERE LOGIN = ?";
        try(Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();
            List<Client> list = parseResultSet(rs);
            rs.close();

            switch (list.size()) {
                case 0:
                    return null;
                case 1:
                    return list.get(0);
                default:
                    String msg = "Expected only 1 model, got + " + list.size();
                    log.error(msg);
                    throw new DataAccessException(msg);
            }

        } catch (SQLException e) {
            String msg = "Error retrieving " + getModelName() + " with login '" + login + "'";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }
}
