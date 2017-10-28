package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import dataLayer.Book;
import dataLayer.User;

import java.sql.SQLException;
import java.util.List;

public class BookRepo extends BaseDao {
    Dao<Book,String> bookDao;

    JdbcConnectionSource con = getConnectionSource();

    @Override
    void createDao() throws SQLException {
        if (bookDao == null) {
            bookDao = DaoManager.createDao(con, Book.class);
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        createDao();
        return bookDao.queryForAll();
    }

    public Book getBook(String id) throws SQLException {
        createDao();
        return bookDao.queryForId(id);
    }

    public int updateBook(Book book) throws SQLException {
        createDao();
        return bookDao.update(book);
    }

    public int createBook(Book book) throws SQLException {
        createDao();
        return bookDao.create(book);
    }

}
