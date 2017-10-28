package repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import dataLayer.Book;
import dataLayer.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewRepo extends BaseDao {
    private Dao<Review,String> reviewDao;
    private JdbcConnectionSource con = getConnectionSource();

    public ReviewRepo() throws SQLException{
        reviewDao = DaoManager.createDao(con, Review.class);
    }

    public Review getReview(String id) throws SQLException {
        return reviewDao.queryForId(id);
    }

    public List<Review> getReviews(String bookId) throws SQLException {
        return reviewDao.queryBuilder().where().eq("book_id", bookId).query();
    }

    public int updateReview(Review review) throws SQLException {
        return reviewDao.update(review);
    }

    public int createReview(Review review) throws SQLException {
        return reviewDao.create(review);
    }
}
