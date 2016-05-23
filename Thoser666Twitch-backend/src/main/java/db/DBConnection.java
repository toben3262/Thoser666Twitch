package db;

import java.sql.*;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Created by stbrumm on 22.05.16.
 * DB-Stuff
 */
public class DBConnection
{
    public DBConnection() throws SQLException, ClassNotFoundException {
        JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:./t666t");
		ds.setUser("sa");
		ds.setPassword("sa");
		Connection conn = ds.getConnection();
        // add application code here
        conn.close();

    }
}
