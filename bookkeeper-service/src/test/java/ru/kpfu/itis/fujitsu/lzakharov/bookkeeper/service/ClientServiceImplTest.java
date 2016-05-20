package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

public class ClientServiceImplTest {
    public static final String PASSWORD_HASH = BCrypt.hashpw("password", BCrypt.gensalt());

    @Test
    public void testCreateNotExisting() throws ClientAlreadyExistsException {
        Client client = new Client("login", PASSWORD_HASH, Gender.M);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(client.getLogin())).thenReturn(null);
        when(clientDao.add(client)).thenReturn(0L);

        ClientService clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setClientDao(clientDao);

        assertEquals(0L, clientService.create(client));
    }

    @Test(expected = ClientAlreadyExistsException.class)
    public void testCreateExistingClient() throws ClientAlreadyExistsException {
        Client client = new Client("login", PASSWORD_HASH, Gender.M);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(client.getLogin())).thenReturn(client);

        ClientService clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setClientDao(clientDao);
        clientService.create(client);
    }

    @Test
    public void testFindClientByWrongLogin() {
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get(anyString())).thenReturn(null);

        ClientService clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setClientDao(clientDao);

        assertNull(clientService.find("login", "password"));
    }

    @Test
    public void testFindClientByRightLoginAndPassword() {
        Client client = new Client("login", PASSWORD_HASH, Gender.M);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get("login")).thenReturn(client);

        ClientService clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setClientDao(clientDao);

        assertEquals(client, clientService.find("login", "password"));
    }

    @Test
    public void testFindClientByRightLoginAndWrongPassword() {
        Client client = new Client("login", PASSWORD_HASH, Gender.M);
        ClientDao clientDao = mock(ClientDao.class);
        when(clientDao.get("login")).thenReturn(client);

        ClientService clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setClientDao(clientDao);

        assertNull(clientService.find("login", "wrong_password"));
    }

}
