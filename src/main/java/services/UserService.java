package services;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static spark.Spark.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dataLayer.Book;
import dataLayer.Review;
import dataLayer.User;
import repositories.UserRepo;
import spark.Route;


import java.io.IOException;
import java.sql.SQLException;

public class UserService extends jsonService{

    UserRepo ur = new UserRepo();

    public void setupHttpCalls() {
        // get user with matching username & password (using HTTP get method)
        get("/users", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            User u = ur.getUser(username, password);

            //response.type("application/json");
            if(u == null) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError("No user with given username and password found"));
            }
            response.status(HTTP_OK);
            return serializeObject(u);
        });

        // insert a user (using HTTP post method)
        post("/users", (request, response) -> {
            try {
                User u = mapJsonToObject(request.body());
                if (!isValid(u)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not create User with provided data"));
                }
                ur.createUser(u);
                response.status(HTTP_CREATED);
                //response.type("application/json");
                return serializeObject(u);
            } catch (JsonParseException ex) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError(ex));
            }
        });


    }

    @Override
    User mapJsonToObject(String json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, User.class);
    }

    @Override
    Boolean isValid(Object obj) {
        User u = (User)obj;
        return (u.getName() != null) && (u.getPassword() != null) && (u.getUsername() != null);
    }

}
