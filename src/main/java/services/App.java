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

        App.enableCORS("*", "*", "*");

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

    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
