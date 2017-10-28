package services;

import static spark.Spark.*;

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


import java.sql.SQLException;

public class UserService extends jsonService{

    UserRepo ur = new UserRepo();

    public void setupHttpCalls() {
        // get user with matching username & password (using HTTP get method)
        get("/users", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            User u = ur.getUser(username, password);

            response.type("application/json");
            if(u == null) {
                response.status(404);
                return "";
            }
            response.status(200);
            return serializeObject(u);
        });

        // insert a user (using HTTP post method)
        post("/users", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
                if (!creation.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                int id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
                response.status(200);
                response.type("application/json");
                return id;
            } catch (JsonParseException jpe) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        });
    }



}
