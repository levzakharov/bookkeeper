package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ClientDaoHsqldbTest extends DaoHsqldbTest {
    public final String NOT_EXISTING_LOGIN = "########";
    static ClientDao clientDao;

    @BeforeClass
    public static void init() {
        clientDao = new ClientDaoHsqldb();
    }

    @Test
    public void testGetExisting() throws Exception {

        Client client = clientDao.get(0);
        assertEquals(0L, client.getId().longValue());
        assertEquals("client", client.getLogin());
        assertEquals("password", client.getPassword());
        assertEquals(Gender.M, client.getGender());
    }

    @Test
    public void testGetNotExisting() throws Exception {
        assertNull(clientDao.get(NOT_EXISTING_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testGetWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        clientDaoSpy.get(0);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Client> list = clientDao.getAll();

        Iterator<Client> it = list.iterator();

        Client client = it.next();
        assertEquals(0L, client.getId().longValue());
        assertEquals("client", client.getLogin());
        assertEquals("password", client.getPassword());
        assertEquals(Gender.M, client.getGender());

        client = it.next();
        assertEquals(1L, client.getId().longValue());
        assertEquals("test", client.getLogin());
        assertEquals("pass", client.getPassword());
        assertEquals(Gender.F, client.getGender());
    }

    @Test(expected = DataAccessException.class)
    public void testGetAllWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        clientDaoSpy.getAll();
    }

    @Test
    public void testUpdateExisting() throws Exception {
        Client client = new Client(0L, "client", "password", Gender.M);

        assertEquals(clientDao.update(client), client);
    }

    @Test
    public void testUpdateNotExisting() throws Exception {
        Client client = new Client(NOT_EXISTING_ID, "client", "password", Gender.M);

        assertNull(clientDao.update(client));
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        Client client = new Client(0L, "client", "password", Gender.M);
        clientDaoSpy.update(client);
    }

    @Test
    public void testAdd() throws Exception {
        Client client = new Client("client", "password", Gender.M);

        assertEquals(clientDao.add(client), 2);
    }

    @Test(expected = DataAccessException.class)
    public void testAddWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        Client client = new Client("client", "password", Gender.M);
        clientDaoSpy.add(client);
    }

    @Test
    public void testRemoveExisting() throws Exception {
        assertTrue(clientDao.remove(1));
        assertNull("Found previously removed entry", clientDao.get(1));
    }

    @Test
    public void testRemoveNotExisting() throws Exception {
        assertFalse(clientDao.remove(NOT_EXISTING_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testRemoveWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        clientDaoSpy.remove(0);
    }

    @Test
    public void testGetExistingByLogin() {
        Client client = clientDao.get("client");
        assertEquals(0L, client.getId().longValue());
        assertEquals("client", client.getLogin());
        assertEquals("password", client.getPassword());
        assertEquals(Gender.M, client.getGender());
    }

    @Test
    public void testGetNotExistingByLogin() {
        assertNull(clientDao.get(NOT_EXISTING_LOGIN));
    }

    @Test(expected = DataAccessException.class)
    public void testGetByLoginWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        clientDaoSpy.get("client");
    }

    @Test
    public void testGetCount() {
        assertEquals(2, clientDao.getCount());
    }

    @Test(expected = DataAccessException.class)
    public void testGetCountWithDataAccessException() throws Exception {
        ClientDao clientDaoSpy = PowerMockito.spy(new ClientDaoHsqldb());
        PowerMockito.doThrow(new DataAccessException("")).when(clientDaoSpy, "getConnection");
        clientDaoSpy.getCount();
    }
}
