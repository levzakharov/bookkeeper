package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.apache.commons.io.FileUtils;
import org.hsqldb.cmdline.SqlFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.AbstractModel;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public abstract class GenericDaoHsqldbTest<T extends AbstractModel> {
    public final long NOT_EXISTING_ID = 10001L;

    public GenericDao<T> dao;
    public GenericDao<T> daoWithSQLException;
    public List<T> table;

    public abstract void initDao();
    public abstract void createTable();
    public abstract T objectToUpdate();
    public abstract T nonexistentObject();
    public abstract T newObject();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Initializes database.
     *
     * <ol>
     *     <li>Drop schema if exist.</li>
     *     <li>Create schema.</li>
     *     <li>Load test data.</li>
     * </ol>
     * @throws Exception
     */

    private void executeSqlResources(String[] sqlResources) throws Exception {
        try (Connection conn = JdbcConnectionPool.getInstance().getConnection()) {
            for (String sqlResource : sqlResources) {
                InputStream in = getClass().getResourceAsStream("/" + sqlResource);
                File file = folder.newFile();
                FileUtils.copyInputStreamToFile(in, file);
                SqlFile sqlFile = new SqlFile(file, StandardCharsets.UTF_8.name());
                sqlFile.setConnection(conn);
                sqlFile.execute();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        createTable();
        initDao();

        executeSqlResources(new String[]{"drop-schema.sql", "create-schema.sql", "test-data.sql"});

        daoWithSQLException = PowerMockito.spy(this.dao);
        PowerMockito.doThrow(new SQLException("")).when(daoWithSQLException, "getConnection");
    }

    @Test
    public void testGetExisting() throws Exception {
        T object = dao.get(0L);

        assertEquals(table.get(0), object);
    }

    @Test
    public void testGetNotExisting() throws Exception {
        assertNull(dao.get(NOT_EXISTING_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testGetWithDataAccessException() throws Exception {
        daoWithSQLException.get(0L);
    }

    @Test
    public void testGetAll() throws Exception {
        List<T> list = dao.getAll();

        assertEquals(table, list);
    }

    @Test(expected = DataAccessException.class)
    public void testGetAllWithDataAccessException() throws Exception {
        daoWithSQLException.getAll();
    }

    @Test
    public void testUpdateExisting() throws Exception {
        T object = objectToUpdate();

        T updatedObject = dao.update(object);

        assertEquals(updatedObject, dao.get(updatedObject.getId()));
    }

    @Test
    public void testUpdateNotExisting() throws Exception {
        T object = nonexistentObject();

        assertNull(dao.update(object));
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateWithDataAccessException() throws Exception {
        T object = objectToUpdate();

        assertEquals(daoWithSQLException.update(object), object);
    }

    @Test
    public void testAdd() throws Exception {
        T object = newObject();

        assertEquals(dao.add(object), table.size());
    }

    @Test(expected = DataAccessException.class)
    public void testAddWithDataAccessException() throws Exception {
        T object = newObject();

        assertEquals(daoWithSQLException.add(object), table.size());
    }

    @Test
    public void testRemoveExisting() throws Exception {
        assertTrue(dao.remove(1L));
        assertNull("Found previously removed entry", dao.get(1L));
    }

    @Test
    public void testRemoveNotExisting() throws Exception {
        assertFalse(dao.remove(NOT_EXISTING_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testRemoveWithDataAccessException() throws Exception {
        daoWithSQLException.remove(0);
    }

    @Test
    public void testGetCount() {
        assertEquals(table.size(), dao.getCount());
    }

    @Test(expected = DataAccessException.class)
    public void testGetCountWithDataAccessException() throws Exception {
        daoWithSQLException.getCount();
    }
}
