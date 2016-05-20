package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

/**
 * Signals that an data access exception of some sort has occurred.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
