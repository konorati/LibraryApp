package services;


import dataLayer.Book;
import repositories.BookRepo;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.net.HttpURLConnection.*;
import static spark.Spark.*;

public class BookService extends jsonService{
    BookRepo br = new BookRepo();

    public void setupHttpCalls() {
        get("/books", (request, response) -> {
            List<Book> books = br.getAllBooks();
            response.type("application/json");
            response.status(HTTP_OK);
            return serializeObject(books);
        });

        get("/books/:id", (request, response) -> {
            Book b = br.getBook(request.params(":id"));
            if(b == null) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError("No book with given id found"));
            }
            response.status(HTTP_OK);
            return serializeObject(b);
        });

        post("/books", (request, response) -> {
            try {
                Book b = mapJsonToObject(request.body());
                if (!isValid(b)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not create Book with provided data"));
                }
                br.createBook(b);
                response.status(HTTP_CREATED);
                //response.type("application/json");
                return serializeObject(b);
            } catch (JsonParseException ex) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError(ex));
            } catch (Exception ex) {
                response.status(HTTP_INTERNAL_ERROR);
                return serializeObject(new ResponseError(ex));
            }
        });

        //TODO: Should make this create or update (not just update)
        put("/books/:id", (request, response) -> {
            try {
                Book b = mapJsonToObject(request.body());
                if (!isValid(b)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not update Book with provided data"));
                }
                int id = Integer.parseInt(request.params(":id"));
                b.setId(id);
                br.updateBook(b);
                response.status(HTTP_OK);
                return serializeObject(b);
            }catch (JsonParseException ex) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError(ex));
            } catch (Exception ex) {
                response.status(HTTP_INTERNAL_ERROR);
                return serializeObject(new ResponseError(ex));
        }
        });
    }



    @Override
    Book mapJsonToObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Book.class);
    }

    @Override
    Boolean isValid(Object obj) {
        Book b = (Book)obj;
        return b.getTitle() != null;
    }
}
