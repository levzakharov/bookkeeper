package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.CategoryDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.DataAccessException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoryDaoHsqldb extends GenericDaoHsqldb<Category> implements CategoryDao {
    @Override
    protected String getModelName() {
        return "CATEGORY";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT ID, NAME " +
                "FROM CATEGORY WHERE ID = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT ID, NAME " +
                "FROM CATEGORY";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE CATEGORY " +
                "SET NAME = ?" +
                "WHERE ID = ?";
    }

    @Override
    protected String getAddQuery() {
        return "INSERT INTO CATEGORY (NAME) " +
                "VALUES (?)";
    }

    @Override
    protected List<Category> parseResultSet(ResultSet rs) {
        List<Category> list = new LinkedList<>();

        try {
            while (rs.next()) {
                list.add(new Category(rs.getLong("ID"), rs.getString("NAME")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error parsing ResultSet", e);
        }

        return list;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement pstmt, Category model) {
        try {
            pstmt.setString(1, model.getName());
            pstmt.setLong(2, model.getId());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for update", e);
        }
    }

    @Override
    protected void prepareStatementForAdd(PreparedStatement pstmt, Category model) {
        try {
            pstmt.setString(1, model.getName());
        } catch (SQLException e) {
            throw new DataAccessException("Error preparing statement for add", e);
        }
    }
}
