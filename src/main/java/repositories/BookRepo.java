package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import dataLayer.Book;
import dataLayer.User;

import java.sql.SQLException;
import java.util.List;

public class BookRepo extends BaseDao {
    private Dao<Book,String> bookDao;
    private JdbcConnectionSource con = getConnectionSource();

    public BookRepo() throws SQLException{
        bookDao = DaoManager.createDao(con, Book.class);
    }

    public List<Book> getAllBooks() throws SQLException {
        return bookDao.queryForAll();
    }

    public Book getBook(String id) throws SQLException {
        return bookDao.queryForId(id);
    }

    public int updateBook(Book book) throws SQLException {
        return bookDao.update(book);
    }

    public int createBook(Book book) throws SQLException {
        return bookDao.create(book);
    }

}
