package db;

import java.sql.*;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Created by stbrumm on 22.05.16.
 * DB-Stuff
 */
public class DBConnection
{
    private Connection conn = null;
    private JdbcDataSource ds;

    public DBConnection() throws SQLException, ClassNotFoundException {
        ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:~/t666t;");
		ds.setUser("sa");
		ds.setPassword("");
		conn = ds.getConnection();
        // add application code here
 //       conn.close();

    }

    /**
     * to send a query not covered by other ones
     * @param query
     * @return
     */
    public String customQueryWithResult(String query)
    {
        Statement stmt = null;
        ResultSet rs = null;
        String rueckgabe = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            rueckgabe = rs.getString(1);
        } catch (SQLException e)
        {
           e.printStackTrace();
        }


        return rueckgabe;
    }

    /**
     * to send a Execute not covered by other ones
     * @param query
     * @return
     */
    public String customExecuteWithResult(String query)
    {
        Statement stmt = null;
        boolean rs = Boolean.FALSE;
        String rueckgabe = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.execute(query);
            rueckgabe = String.valueOf(rs);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        return rueckgabe;
    }

    /**
     * Connection close
     */
    public void connClose()
    {
        try
        {
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
