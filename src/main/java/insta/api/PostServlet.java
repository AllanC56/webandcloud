package insta.api;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import insta.Post;
import insta.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name= "PostAPI",
        description= "PostAPI: Post a post",
        urlPatterns = "/api/post"
)
public class PostServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String image = req.getParameter("image");
        String description = req.getParameter("description");

        //TODO a modifier, pour l'instant juste le mail de l'utilisateur
        String userId = req.getParameter("userId");
        Key k = KeyFactory.createKey("User", userId);


        try {
            User.post(k, image, description);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        resp.setStatus(201);
    }
}
