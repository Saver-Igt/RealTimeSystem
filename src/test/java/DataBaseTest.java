import org.example.dataBase.PostgresDataBase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataBaseTest {
    private PostgresDataBase postgresDataBase;

    @Before
    public void setUp() throws Exception {
        postgresDataBase = new PostgresDataBase();
    }

    @Test
    public void shouldGetJdbcConnection(){
        try(Connection connection = postgresDataBase.getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
