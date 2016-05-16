package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service;

public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
