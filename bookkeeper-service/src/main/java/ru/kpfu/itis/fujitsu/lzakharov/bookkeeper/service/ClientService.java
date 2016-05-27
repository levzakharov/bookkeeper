package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;

/**
 * Client Service interface.
 * Provides logic to operate on the data sent to and from the DAO and the client.
 */
public interface ClientService {
    /**
     * Creates specified client.
     *
     * @param client the client to create
     * @return created client's id
     * @throws ClientAlreadyExistsException if client with specified login already exists
     */
    long create(Client client) throws ClientAlreadyExistsException;

    /**
     * Returns specified by login and password client.
     *
     * @param login the login of client to find
     * @param password the password of client to find
     * @return the client with specified login exists and password is correct;
     *         {@code null} otherwise
     */
    Client find(String login, String password);
}
