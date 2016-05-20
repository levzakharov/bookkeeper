package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;

public interface ClientDao extends GenericDao<Client> {
    /**
     * Returns the specified by {@code login} client.
     *
     * @param login the login of client to get
     * @return client or {@code null} if client doesn't exists
     */
    Client get(String login);
}
