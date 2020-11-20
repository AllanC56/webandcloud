package insta.api;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import insta.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (
        name = "UnFollowUserServlet",
        description = "UnFollowUserServlet: follow a user",
        urlPatterns = "api/unfollow"
)
public class UnFollowUserServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String googleToken = req.getHeader("connectionToken");
        String userEmail = req.getHeader("userEmail");

        //retrieve the email of the user to follow
        String userToFollow = req.getHeader("userToFollow");

        boolean userIdentityVerified = User.googleAuthentification(googleToken);

        if(!userIdentityVerified){
            resp.setStatus(401);
        } else {
            Key followingUserKey = User.getKey(userEmail);
            Key followedUserKey = User.getKey(userToFollow);

            try {
                User.unFollow(followingUserKey, followedUserKey);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                resp.setStatus(500);
            }

            resp.setStatus(202);
        }

    }
}
