package repositories;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 *
 */
public abstract class BaseDao {
    private static Logger logger = LoggerFactory.getLogger("BaseDao");

    private static String databaseUrl = "jdbc:mysql://localhost:3306/library";
    private static String dbusername = "root";
    private static String dbpassword = "root";

    /**
     * Creates a database connection and returns a handle to it.
     * @return Connection source for the connection or null on error.
     */
    JdbcConnectionSource getConnectionSource() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, dbusername, dbpassword);
            return (JdbcConnectionSource)connectionSource;
        } catch (SQLException ex) {
            logger.log(Log.Level.ERROR, "Could not create connection to database: " + ex.getMessage());
            return null;
        }
    }

    abstract void createDao() throws SQLException;
}
