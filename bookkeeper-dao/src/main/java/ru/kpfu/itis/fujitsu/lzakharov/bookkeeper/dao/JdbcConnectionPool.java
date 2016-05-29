package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionPool {
    final static Logger log = Logger.getLogger(JdbcConnectionPool.class.getName());
    final static String JDBC_PROPERTIES_FILE = "/jdbc.properties";

    private static JdbcConnectionPool instance;

    private HikariDataSource ds;

    private JdbcConnectionPool() {
        HikariConfig config = new HikariConfig();

        Properties jdbcProps = new Properties();
        try {
            jdbcProps.load(getClass().getResourceAsStream(JDBC_PROPERTIES_FILE));
        } catch (IOException e) {
            log.warn("Unable to open JDBC properties file '" + JDBC_PROPERTIES_FILE + "'");
        }
        config.setJdbcUrl(jdbcProps.getProperty("jdbc.url"));
        config.setUsername(jdbcProps.getProperty("jdbc.username"));
        config.setPassword(jdbcProps.getProperty("jdbc.password"));
        config.setIdleTimeout(Long.parseLong(jdbcProps.getProperty("jdbc.pool.idleTimeout")));
        config.setMaxLifetime(Long.parseLong(jdbcProps.getProperty("jdbc.pool.maxLifetime")));
        config.setMaximumPoolSize(Integer.parseInt(jdbcProps.getProperty("jdbc.pool.maximumPoolSize")));

        ds = new HikariDataSource(config);
    }

    public static JdbcConnectionPool getInstance() {
        if (instance == null) {
            synchronized (JdbcConnectionPool.class) {
                if (instance == null) {
                    instance = new JdbcConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
