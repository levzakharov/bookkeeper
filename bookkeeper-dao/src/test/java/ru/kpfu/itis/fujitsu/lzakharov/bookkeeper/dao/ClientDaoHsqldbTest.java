package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.junit.Test;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ClientDaoHsqldbTest extends DaoHsqldbTest {

    @Test
    public void testGetExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        Client client = clientDao.get(0);
        assertEquals(0L, client.getId().longValue());
        assertEquals("client", client.getLogin());
        assertEquals("password", client.getPassword());
        assertEquals(Gender.M, client.getGender());
    }

    @Test
    public void testGetNotExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        assertNull(clientDao.get(NOT_EXISTING_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

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

    @Test
    public void testUpdateExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        Client client = new Client(0L, "client", "password", Gender.M);

        assertEquals(clientDao.update(client), client);


    }

    @Test
    public void testUpdateNotExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        Client client = new Client(NOT_EXISTING_ID, "client", "password", Gender.M);

        assertNull(clientDao.update(client));
    }

    @Test
    public void testAdd() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        Client client = new Client("client", "password", Gender.M);

        assertEquals(clientDao.add(client), 2);
    }

    @Test
    public void testRemoveExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        assertTrue(clientDao.remove(1));
        assertNull("Found previously removed entry", clientDao.get(1));
    }

    @Test
    public void testRemoveNotExisting() throws Exception {
        ClientDaoHsqldb clientDao = new ClientDaoHsqldb();

        assertFalse(clientDao.remove(NOT_EXISTING_ID));
    }
}
