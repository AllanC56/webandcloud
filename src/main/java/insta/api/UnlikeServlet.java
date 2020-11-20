package insta.api;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import insta.Post;
import insta.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (
        name = "LikeAPI",
        description = "LikeAPI: like a post",
        urlPatterns = "/api/like"
)
public class UnlikeServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userEmail = req.getHeader("email");
        String postId = req.getHeader("postId");

        String googleToken = req.getHeader("connectionToken");

        boolean userIdentityVerified = User.googleAuthentification(googleToken);

        if(!userIdentityVerified){
            resp.setStatus(401);
        } else {

            Key userKey = User.getKey(userEmail);
            Key postKey = Post.getKey(postId);

            try {
                Post.unlike(userKey, postKey);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();

                resp.setStatus(500);
            }

            resp.setStatus(201);
        }
    }
}
