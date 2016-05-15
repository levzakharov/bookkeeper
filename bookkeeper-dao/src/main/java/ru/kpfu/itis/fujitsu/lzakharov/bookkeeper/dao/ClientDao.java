package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;

public interface ClientDao extends GenericDao<Client> {
    Client get(String login);
}
