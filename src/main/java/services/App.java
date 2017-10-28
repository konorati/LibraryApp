package services;

import static spark.Spark.*;


import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {

        Logger logger = LoggerFactory.getLogger(UserService.class);

        //Create Services
        UserService userService = new UserService();
        BookService bookService = new BookService();
        ReviewService reviewService = new ReviewService();

        //Setup up routes
        userService.setupHttpCalls();
        bookService.setupHttpCalls();
        reviewService.setupHttpCalls();

        after((req, res) -> {
            res.type("application/json");
        });

        /*//Create Tables
        UserRepo ur = new UserRepo();
        try {
            TableUtils.createTableIfNotExists(ur.getConnection(), User.class);
            TableUtils.createTableIfNotExists(ur.getConnection(), Book.class);
            TableUtils.createTableIfNotExists(ur.getConnection(), Review.class);
        } catch (SQLException ex) {
            logger.log(Log.Level.ERROR, "Could not add tables " + ex.getMessage());
            ex.printStackTrace();
        }*/
    }
}
