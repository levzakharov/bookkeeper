package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

/**
 * Signals that added client already exists.
 */
public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
