package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dataLayer.User;

import java.sql.SQLException;

public class UserRepo extends BaseDao {
    private Dao<User,String> userDao;
    private JdbcConnectionSource con = getConnectionSource();

    public UserRepo() throws SQLException{
        userDao = DaoManager.createDao(con, User.class);
    }

    public int createUser(User user) throws SQLException{
        return userDao.create(user);
    }

    public User getUser(String id) throws SQLException{
        return userDao.queryForId(id);
    }

    public User getUser(String username, String password) throws SQLException{
        return userDao.queryBuilder().where().eq("username", password).and().eq("password", password).queryForFirst();
    }
}
