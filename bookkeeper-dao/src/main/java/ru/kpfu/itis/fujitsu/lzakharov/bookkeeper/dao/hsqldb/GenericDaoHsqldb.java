package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.GenericDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.JdbcConnectionPool;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.AbstractModel;

import java.sql.*;
import java.util.List;

public abstract class GenericDaoHsqldb<T extends AbstractModel> implements GenericDao<T> {
    final static Logger log = Logger.getLogger(GenericDao.class.getName());

    public Connection getConnection() throws SQLException {
        return JdbcConnectionPool.getInstance().getConnection();
    }

    protected abstract String getModelName();
    protected abstract String getSelectByIdQuery();
    protected abstract String getSelectAllQuery();
    protected String getDeleteByIdQuery() {
        return String.format("DELETE FROM %s WHERE ID = ?", getModelName());
    }
    protected String getCountQuery() {
        return String.format("SELECT COUNT(*) FROM %s", getModelName());
    }
    protected abstract String getUpdateQuery();
    protected abstract String getAddQuery();

    /**
     * Parses specified ResultSet and returns list of models from this ResultSet.
     *
     * @param rs the ResultSet of objects to parse
     * @return list of models
     */
    protected abstract List<T> parseResultSet(ResultSet rs);

    /**
     * Prepares specified statement for update model.
     *
     * @param pstmt the statement to prepare
     * @param model the model to update
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement pstmt, T model);

    /**
     * Prepares specified statement for add model.
     *
     * @param pstmt the statement to prepare
     * @param model the model to add
     * @throws SQLException if can't prepare statement
     */
    protected abstract void prepareStatementForAdd(PreparedStatement pstmt, T model);

    @Override
    public T get(long id) {
        String sql = getSelectByIdQuery();

        try(Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            List<T> list = parseResultSet(rs);
            rs.close();

            switch (list.size()) {
                case 0:
                    return null;
                case 1:
                    return list.get(0);
                default:
                    String msg = "Expected only 1 client, got + " + list.size();
                    log.error(msg);
                    throw new DataAccessException(msg);
            }

        } catch (SQLException e) {
            String msg = "Error retrieving " + getModelName() + " with id " + id;
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public List<T> getAll() {
        String sql = getSelectAllQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<T> list = parseResultSet(rs);
            rs.close();

            return list;
        } catch (SQLException e) {
            String msg = "Error retrieving " + getModelName() + "s";
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public T update(T model) {
        String sql = getUpdateQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(pstmt, model);

            int rowsAffected = pstmt.executeUpdate();

            switch (rowsAffected){
                case 0:
                    return null;
                case 1:
                    return model;
                default:
                    String msg = "Expected only 1 row updated, got " + rowsAffected;
                    log.error(msg);
                    throw new DataAccessException(msg);
            }
        } catch (SQLException e) {
            String msg = "Error updating " + model;
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public long add(T model) {
        String sql = getAddQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForAdd(pstmt, model);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected != 1) {
                String msg = "Expected only 1 row updated, got " + rowsAffected;
                log.error(msg);
                throw new DataAccessException(msg);
            } else {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    String msg = "Adding model failed, no ID obtained";
                    log.error(msg);
                    throw new DataAccessException(msg);
                }
            }
        } catch (SQLException e) {
            String msg = "Error updating " + model;
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public boolean remove(long id) {
        String sql = getDeleteByIdQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            switch (rowsAffected){
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    String msg = "Expected only 1 row updated, got " + rowsAffected;
                    log.error(msg);
                    throw new DataAccessException(msg);
            }
        } catch (SQLException e) {
            String msg = "Error removing " + getModelName() + " with id " + id;
            log.error(msg);
            throw new DataAccessException(msg, e);
        }
    }

    @Override
    public long getCount() {
        String sql = getCountQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }

            rs.close();
        } catch (SQLException e) {
            String msg = "Error getting number of " + getModelName() + "s";
            log.error(msg);
            throw new DataAccessException(msg);
        }
        return 0;
    }
}
