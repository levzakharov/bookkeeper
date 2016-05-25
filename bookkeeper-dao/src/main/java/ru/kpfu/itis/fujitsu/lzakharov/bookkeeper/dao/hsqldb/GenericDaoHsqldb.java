package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.GenericDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.JdbcConnectionPool;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.AbstractModel;

import java.sql.*;
import java.util.List;

public abstract class GenericDaoHsqldb<T extends AbstractModel> implements GenericDao<T> {

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
                    throw new DataAccessException("Expected only 1 client, got + " + list.size());
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving " + getModelName() + " with id " + id, e);
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
            throw new DataAccessException("Error retrieving " + getModelName() + "s", e);
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
                    throw new DataAccessException("Expected only 1 row updated, got " + rowsAffected);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating " + model, e);
        }
    }

    @Override
    public long add(T model) {
        String sql = getAddQuery();

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForAdd(pstmt, model);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected != 1) {
                throw new DataAccessException("Expected only 1 row updated, got " + rowsAffected);
            } else {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new DataAccessException("Adding model failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating " + model, e);
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
                    throw new DataAccessException("Expected only 1 row updated, got " + rowsAffected);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error removing " + getModelName() + " with id " + id, e);
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
            throw new DataAccessException("Error getting number of " + getModelName() + "s");
        }
        return 0;
    }
}
