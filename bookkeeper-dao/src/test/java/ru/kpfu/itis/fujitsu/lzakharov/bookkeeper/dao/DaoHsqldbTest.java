package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.dao;

import org.apache.commons.io.FileUtils;
import org.hsqldb.cmdline.SqlFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class DaoHsqldbTest {
    public final long NOT_EXISTING_ID = 10001L;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Initializes database.
     *
     * <ol>
     *     <li>Drop schema if exist.</li>
     *     <li>Create schema.</li>
     *     <li>Load test data.</li>
     * </ol>
     * @throws Exception
     */

    private void executeSqlResources(String[] sqlResources) throws Exception {
        try (Connection conn = JdbcConnectionPool.getInstance().getConnection()) {
            for (String sqlResource : sqlResources) {
                InputStream in = getClass().getResourceAsStream("/" + sqlResource);
                File file = folder.newFile();
                FileUtils.copyInputStreamToFile(in, file);
                SqlFile sqlFile = new SqlFile(file, StandardCharsets.UTF_8.name());
                sqlFile.setConnection(conn);
                sqlFile.execute();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        executeSqlResources(new String[]{"drop-schema.sql", "create-schema.sql", "test-data.sql"});
    }
}
