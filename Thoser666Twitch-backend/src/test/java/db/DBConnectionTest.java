package db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by stbrumm on 22.05.16.
 */
public class DBConnectionTest
{
    private DBConnection db;

    @Before
    public void setUp()
    {
        try
        {
            db = new DBConnection();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

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

    @Test
    public void testForCustomExecute()
    {
        String erg = db.customExecuteWithResult("SELECT * FROM  t666t_user");
        assertNotNull(erg);
    }
	
	@Test
    public void testForCustomQuery()
    {
        String erg = db.customQueryWithResult("SELECT * FROM  t666t_user ");
        assertNotNull(erg);
    }

    @Test
    public void testForCustomQueryWithWantedValue()
    {
        String erg = db.customQueryWithResult("SELECT password FROM  t666t_user u WHERE u.loginname='admin'");
        assertNotNull(erg);
    }

    @Test
    public void testConnectionClose()
    {
        db.connClose();
    }

    @After
    public void tearDown()
    {
        db.connClose();
    }

}
