package services;

import static spark.Spark.*;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.table.TableUtils;
import dataLayer.Book;
import dataLayer.Review;
import dataLayer.User;
import repositories.UserRepo;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(UserService.class);

        //Create Services
        UserService userService = new UserService();
        userService.setupHttpCalls();

        BookService bookService = new BookService();
        bookService.setupHttpCalls();

        ReviewService reviewService = new ReviewService();
        reviewService.setupHttpCalls();


        UserRepo ur = new UserRepo();
        //Create Tables
        try {
            TableUtils.createTableIfNotExists(ur.getConnection(), User.class);
            TableUtils.createTableIfNotExists(ur.getConnection(), Book.class);
            TableUtils.createTableIfNotExists(ur.getConnection(), Review.class);
        } catch (SQLException ex) {
            logger.log(Log.Level.ERROR, "Could not add tables " + ex.getMessage());
            ex.printStackTrace();
        }

        after((req, res) -> {
            res.type("application/json");
        });

        //get("/hello", (req, res) -> { return "Hello World!"; } );
    }
}
