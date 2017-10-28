package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import dataLayer.Book;
import dataLayer.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewRepo extends BaseDao {
    Dao<Review,String> reviewDao;

    JdbcConnectionSource con = getConnectionSource();

    @Override
    void createDao() throws SQLException {
        if (reviewDao == null) {
            reviewDao = DaoManager.createDao(con, Review.class);
        }
    }

    public Review getReview(String id) throws SQLException {
        createDao();
        return reviewDao.queryForId(id);
    }

    public List<Review> getReviews(String bookId) throws SQLException {
        createDao();
        return reviewDao.queryBuilder().where().eq("book_id", bookId).query();
    }

    public int updateReview(Review review) throws SQLException {
        createDao();
        return reviewDao.update(review);
    }

    public int createReview(Review review) throws SQLException {
        createDao();
        return reviewDao.create(review);
    }
}
