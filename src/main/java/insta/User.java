package insta;

import com.google.appengine.api.datastore.*;

import java.util.HashSet;

public class User {
    
    private Key key;

    public User(String name, String email) {
        this(name, email, null, null, null);
    }

    public User(String name, String email, String bio, String avatarUrl, String token) {
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        Entity entity = new Entity("User", email);
        entity.setProperty("name", name);
        entity.setProperty("avatarUrl", avatarUrl);
        entity.setProperty("email", email);
        entity.setProperty("bio", bio);
        entity.setProperty("googletoken", "");
        entity.setProperty("posts", new HashSet<Key>());
        entity.setProperty("following", new HashSet<Key>());
        entity.setProperty("followers", new HashSet<Key>());

        //TODO vérifier token glogin, passer par un hash
        entity.setProperty("token", token);

        this.key = datastore.put(entity);
    }

    /**
     * create a following association between user a and b, user a follows user  b on tiny Insta
     * @param followingUser user who wants to follow b
     * @param followedUser user being followed by a
     */
    public static void follow(Key followingUser, Key followedUser) throws EntityNotFoundException {
        //retrieve the datastore and the entities corresponding to the users
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        Entity entityA = datastore.get(followingUser);
        Entity entityB = datastore.get(followedUser);

        //retrieve the list of users followed by user a
        HashSet<Key> following = (HashSet<Key>) entityA.getProperty("following");
        //adding user b to the list
        following.add(followedUser);
        //updating the entity with the new list
        entityA.setProperty("following", following);

        //retrieve the list of users following user b
        HashSet<Key> followers = (HashSet<Key>) entityB.getProperty("followers");
        //adding a to the list of followers
        followers.add(followingUser);
        //updating the entity with the new list
        entityB.setProperty("followers", followers);
    }

    /**
     * follow the user given in parameter
     * @param followedUser user to follow
     */
    public void follow(User followedUser) throws EntityNotFoundException {
        User.follow(this.key, followedUser.getKey());
    }

    /**
     * create a post
     * @param image the url of the post's image
     * @param description description of the image
     */
    public void post(String image, String description) throws EntityNotFoundException {
        User.post(this.key, image, description);
    }

    /**
     * create a post with the given image and description attributed to the user associated to the userKey
     * @param userKey the datastore Key of the user
     * @param image the image of the post
     * @param description the description of the post
     * @throws EntityNotFoundException
     */
    public static void post(Key userKey, String image, String description) throws EntityNotFoundException {
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(userKey);

        //create the post
        Post p = new Post(userKey, image, description);

        //retrieve the user's posts list
        HashSet<Key> posts = (HashSet<Key>) entity.getProperty("posts");
        //update the list
        posts.add(p.getKey());
        //update the entity woth the new list
        entity.setProperty("posts", posts);
    }

    /**
     * create a post
     * @param image the image to post
     * @throws EntityNotFoundException
     */
    public void post(String image) throws EntityNotFoundException {
        User.post(this.key,image, null);
    }

    /**
     * remove a following association between user a and b, user a follows user  b on tiny Insta
     * @param a user who wants to unfollow b
     * @param b user being followed by a
     */
    public static void unFollow(User a, User b) throws EntityNotFoundException {
        //retrieve the datastore and the entities corresponding to the users
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entityA = datastore.get(a.getKey());
        Entity entityB = datastore.get(b.getKey());

        //retrieve the list of users followed by user a
        HashSet<Key> following = (HashSet<Key>) entityA.getProperty("following");
        //removing user b from the list
        following.remove(b.getKey());
        //updating the entity with the new list
        entityA.setProperty("following", following);

        //retrieve the list of users following user b
        HashSet<Key> followers = (HashSet<Key>) entityB.getProperty("followers");
        //removing a from the list of followers
        followers.remove(a.getKey());
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
    public void removePost(Key postId) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        //retrieve the list of posts by the user
        HashSet<Key> posts = (HashSet<Key>) entity.getProperty("posts");
        //removing the post from the list of posts
        posts.remove(postId);
        //updating the entity with the new list
        entity.setProperty("posts", posts);
    }

    public String getName() throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        return (String) entity.getProperty("name");
    }

    public void setName(String name) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        entity.setProperty("name", name);
    }

    public String getEmail() throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        return (String) entity.getProperty("email");
    }

    public void setEmail(String email) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        entity.setProperty("email", email);
    }

    public String getBio() throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        return (String) entity.getProperty("bio");
    }

    public void setBio(String bio) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        entity.setProperty("bio", bio);
    }

    public String getAvatarUrl() throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        return (String) entity.getProperty("avatarUrl");
    }

    public void setAvatarUrl(String avatarUrl) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the user
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastore.get(this.key);

        entity.setProperty("avatarUrl", avatarUrl);
    }

    public Key getKey(){
        return this.key;
    }

    public static Key getKey(String email){
        return KeyFactory.createKey("User", email);
    }

    public static boolean googleAuthentification(String googleToken){
        //TODO
        return true;
    }

}

