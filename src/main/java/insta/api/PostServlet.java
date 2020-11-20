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
        String image = req.getHeader("image");
        String description = req.getHeader("description");
        String userEmail = req.getHeader("email");

        String googleToken = req.getHeader("userId");

        boolean userIdentityVerified = User.googleAuthentification(googleToken);

        if(!userIdentityVerified){
            resp.setStatus(401);
        } else {

            Key userKey = User.getKey(userEmail);

            try {
                User.post(userKey, image, description);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();

                resp.setStatus(500);
            }

            resp.setStatus(201);
        }
    }
}
