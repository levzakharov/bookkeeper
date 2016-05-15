package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.ClientDao;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao.hsqldb.ClientDaoHsqldb;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;

public class ClientServiceImpl implements ClientService {
    private static ClientDao clientDao;

    static {
        clientDao = new ClientDaoHsqldb();
    }

    @Override
    public long create(Client client) {
        return clientDao.add(client);
    }
}
