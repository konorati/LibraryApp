package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dataLayer.User;

import java.sql.SQLException;

public class UserRepo extends BaseDao {
    Dao<User,String> userDao;

    JdbcConnectionSource con = getConnectionSource();

    public ConnectionSource getConnection() {return (ConnectionSource)con;}

    void createDao() throws SQLException{
        if (userDao == null) {
            userDao = DaoManager.createDao(con, User.class);
        }
    }

    public int createUser(User user) throws SQLException{
        createDao();
        return userDao.create(user);
    }

    public User getUser(String id) throws SQLException{
        createDao();
        return userDao.queryForId(id);
    }

    public User getUser(String username, String password) throws SQLException{
        createDao();
        return userDao.queryBuilder().where().eq("username", password).and().eq("password", password).queryForFirst();
    }
}
