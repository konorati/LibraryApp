package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataLayer.Book;
import dataLayer.Review;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import repositories.BookRepo;
import repositories.ReviewRepo;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static spark.Spark.*;

public class ReviewService extends jsonService {

    public void setupHttpCalls() throws SQLException{

        ReviewRepo rr = new ReviewRepo();
        BookRepo br = new BookRepo();

        //Get all reviews for a specific book
        get("api/books/:bookId/reviews",(request, response) -> {
            List<Review> reviews = rr.getReviews(request.params(":bookId"));
            response.type("application/json");
            response.status(HTTP_OK);
            return serializeObject(reviews);
        });

        //Add a review to a book
        post("api/books/:bookId/reviews", (request, response) -> {
            try {
                Review r = mapJsonToObject(request.body());
                r.setBook(br.getBook(request.params(":bookId")));
                if (!isValid(r)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not create Review with provided data"));
                }
                rr.createReview(r);
                return serializeObject(r);
            } catch (JsonParseException ex) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError(ex));
            } catch (Exception ex) {
                response.status(HTTP_INTERNAL_ERROR);
                return serializeObject(new ResponseError(ex));
            }
        });
    }

    @Override
    Review mapJsonToObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Review.class);
    }

    @Override
    Boolean isValid(Object obj) {
        Review r = (Review)obj;
        return (r.getBook() != null) && (r.getReview() != null) && (r.getUsername() != null);
    }
}
