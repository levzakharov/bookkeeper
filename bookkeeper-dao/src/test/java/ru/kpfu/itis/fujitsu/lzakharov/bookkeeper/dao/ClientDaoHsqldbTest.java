package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.junit.Test;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ClientDaoHsqldbTest extends GenericDaoHsqldbTest<Client> {
    public final String NOT_EXISTING_LOGIN = "########";

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void initDao() {
        this.dao = new ClientDaoHsqldb();
    }

    @Override
    public void createTable() {
        this.table = new LinkedList<>();
        table.add(new Client(0L, "login", "password", Gender.M));
        table.add(new Client(1L, "client", "qwerty", Gender.F));
    }

    @Override
    public Client objectToUpdate() {
        return new Client(0L, "newLogin", "newPassword", Gender.M);
    }

    @Override
    public Client nonexistentObject() {
        return new Client(NOT_EXISTING_ID, "login", "password", Gender.M);
    }

    @Override
    public Client newObject() {
        return new Client("A", "A", Gender.F);
    }

    @Test
    public void testGetExistingByLogin() {
        Client client = ((ClientDao) dao).get("login");

        assertEquals(table.get(0), client);
    }

    @Test
    public void testGetNotExistingByLogin() {
        assertNull(((ClientDao) dao).get(NOT_EXISTING_LOGIN));
    }

    @Test(expected = DataAccessException.class)
    public void testGetByLoginWithDataAccessException() throws Exception {
        ((ClientDao) daoWithDataAccessException).get("client");
    }

}
