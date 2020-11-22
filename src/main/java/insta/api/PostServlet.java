package insta.api;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import insta.Post;
import insta.User;
import insta.datastore.PostEntity;
import insta.datastore.UserEntity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        String userEmail = req.getHeader("userEmail");

        String googleToken = req.getHeader("googleToken");

        System.out.println("userEmail" + userEmail);
        System.out.println("image : " + image);
        System.out.println("description : " + description);

        Entity userIdentityVerified = UserEntity.googleAuthentification(googleToken);

        if(userIdentityVerified == null){
            resp.setStatus(401);
        } else {
            Key userKey = userIdentityVerified.getKey();
            PostEntity.createPost(userKey, image, description);
            resp.setStatus(201);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String googleToken = req.getHeader("googleToken");
        String posts = req.getHeader("posts");

        String[] postsList = posts.split("/");

        Entity userIdentityVerified = UserEntity.googleAuthentification(googleToken);

        if(userIdentityVerified == null){
            resp.setStatus(401);
        } else {
            for (String post : postsList){

            }
        }
    }
}
