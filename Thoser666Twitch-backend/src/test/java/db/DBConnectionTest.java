package db;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by stbrumm on 22.05.16.
 */
public class DBConnectionTest
{
    private DBConnection db;

    @Test
    public void testIfDBConnectionWorks()
    {
        try {
            db = new DBConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(db);
    }
}
