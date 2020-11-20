package insta.api;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.api.datastore.*;
import insta.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name= "LoginAPI",
        description= "LoginAPI: login a user",
        urlPatterns = "/api/login"
)
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String googleToken = req.getParameter("googleToken");
        String email = req.getParameter("email");

        Key k = KeyFactory.createKey("User", email);


    }
}
