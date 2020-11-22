package insta;

import java.util.LinkedList;
import java.util.List;

public class Profile {
    private User user;
    private List<Post> posts;

    public Profile(User user, List<Post> posts) {
        this.user = user;
        this.posts = posts;
    }
}
