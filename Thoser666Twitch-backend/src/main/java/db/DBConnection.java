package db;

import java.sql.*;

/**
 * Created by stbrumm on 22.05.16.
 * DB-Stuff
 */
public class DBConnection
{
    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        // add application code here
        conn.close();

    }
}
