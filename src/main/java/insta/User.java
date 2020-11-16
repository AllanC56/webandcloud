package insta;

import com.google.appengine.api.datastore.*;

import java.util.HashSet;

public class User {
    private String name;
    private String email;
    private String bio;
    private String avatarUrl;
    private Entity entity;

    public User(String name, String email) {
        this(name, email, null, null, null);
    }

    public User(String name, String email, String bio, String avatarUrl, String token) {
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        this.entity = new Entity("User", email);
        this.entity.setProperty("name", name);
        this.entity.setProperty("avatarUrl", avatarUrl);
        this.entity.setProperty("email", email);
        this.entity.setProperty("bio", bio);
        this.entity.setProperty("posts", new HashSet<String>());
        this.entity.setProperty("following", new HashSet<String>());
        this.entity.setProperty("followers", new HashSet<String>());

        //TODO vérifier token glogin, passer par un hash
        this.entity.setProperty("token", token);

        this.name = name;
        this.email = email;
        this.bio = bio;
        this.avatarUrl = avatarUrl;

        datastore.put(this.entity);
    }

    /**
     * create a following association between user a and b, user a follows user  b on tiny Insta
     * @param a user who wants to follow b
     * @param b user being followed by a
     */
    public static void follow(User a, User b) throws EntityNotFoundException {
        //retrieve the datastore and the entities corresponding to the users
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entityA = datastore.get(a.getEntity().getKey());
        Entity entityB = datastore.get(b.getEntity().getKey());

        //retrieve the list of users followed by user a
        HashSet<String> following = (HashSet<String>) entityA.getProperty("following");
        //adding user b to the list
        following.add(b.getId());
        //updating the entity with the new list
        entityA.setProperty("following", following);

        //retrieve the list of users following user b
        HashSet<String> followers = (HashSet<String>) entityB.getProperty("followers");
        //adding a to the list of followers
        followers.add(a.getId());
        //updating the entity with the new list
        entityB.setProperty("followers", followers);

    }

    /**
     * follow the user given in parameter
     * @param a user to follow
     */
    public void follow(User a) throws EntityNotFoundException {
        follow(this, a);
    }

    /**
     * create a post
     * @param image the url of the post's image
     * @param description description of the image
     */
    public void post(String image, String description) throws EntityNotFoundException {
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        this.entity = datastore.get(this.entity.getKey());

        //create the post
        Post p = new Post(this, image, description);

        //retrieve the user's posts list
        HashSet<String> posts = (HashSet<String>) this.entity.getProperty("posts");
        //update the list
        posts.add(p.getId());
        //update the entity woth the new list
        this.entity.setProperty("posts", posts);
    }

    /**
     * create a post
     * @param image the image to post
     * @throws EntityNotFoundException
     */
    public void post(String image) throws EntityNotFoundException {
        this.post(image, null);
    }

    /**
     * remove a following association between user a and b, user a follows user  b on tiny Insta
     * @param a user who wants to unfollow b
     * @param b user being followed by a
     */
    public static void unFollow(User a, User b) throws EntityNotFoundException {
        //retrieve the datastore and the entities corresponding to the users
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entityA = datastore.get(a.getEntity().getKey());
        Entity entityB = datastore.get(b.getEntity().getKey());

        //retrieve the list of users followed by user a
        HashSet<String> following = (HashSet<String>) entityA.getProperty("following");
        //removing user b from the list
        following.remove(b.getId());
        //updating the entity with the new list
        entityA.setProperty("following", following);

        //retrieve the list of users following user b
        HashSet<String> followers = (HashSet<String>) entityB.getProperty("followers");
        //removing a from the list of followers
        followers.remove(a.getId());
        //updating the entity with the new list
        entityB.setProperty("followers", followers);

    }

    /**
     * unfollow the user given in parameter
     * @param a user to follow
     */
    public void unFollow(User a) throws EntityNotFoundException {
        unFollow(this, a);
    }

    /**
     * remove a post
     * @param postId the id of the post to remove
     */
    public void removePost(String postId) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        this.entity = datastore.get(this.entity.getKey());

        //retrieve the list of posts by the user
        HashSet<String> posts = (HashSet<String>) this.entity.getProperty("posts");
        //removing the post from the list of posts
        posts.remove(postId);
        //updating the entity with the new list
        this.entity.setProperty("posts", posts);
    }

    public Entity getEntity(){
        return this.entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId(){
        return this.email;
    }

    public static String getUserId(User usr){
        return usr.getEmail();
    }
}

