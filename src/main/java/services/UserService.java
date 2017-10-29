package services;

import static java.net.HttpURLConnection.*;
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



    public void setupHttpCalls() throws SQLException{
        UserRepo ur = new UserRepo();

        // get user with matching username & password (using HTTP get method)
        get("api/users", (request, response) -> {
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
        post("api/users", (request, response) -> {
            try {
                User u = mapJsonToObject(request.body());
                if (!isValid(u)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not create User with provided data"));
                }
                ur.createUser(u);
                response.status(HTTP_CREATED);
                return serializeObject(u);
            } catch (JsonParseException ex) {
                response.status(HTTP_BAD_REQUEST);
                return serializeObject(new ResponseError(ex));
            }
        });

        put("api/users/:uname", (request, response) -> {
            try {
                User u = mapJsonToObject(request.body());
                if (!isValid(u)) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Could not update User with provided data"));
                }
                String uname = request.params(":uname");
                System.out.println("Username = " + uname);
                if(ur.getUserByUsername(uname) == null) {
                    response.status(HTTP_NOT_FOUND);
                    return serializeObject(new ResponseError("Error: No user with username: %s found", uname));
                }
                if(!u.getUsername().equals(uname) && ur.getUserByUsername(u.getUsername()) != null) {
                    response.status(HTTP_BAD_REQUEST);
                    return serializeObject(new ResponseError("Username: %s is already in use", u.getUsername()));
                }
                ur.updateUser(u);
                response.status(HTTP_OK);
                return serializeObject(u);
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
