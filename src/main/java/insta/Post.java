package insta;

import com.google.appengine.api.datastore.*;

import java.util.Date;

public class Post {
    private Key key;

    protected Post(User usr, String image, String desc){
        this(usr.getKey(), image, desc);
    }

    protected Post(Key userKey, String image, String desc){
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        Date ts = new Date();
        String id =  userKey.toString() + ts;
        Entity entity = new Entity("post", id);

        entity = new Entity("Post",id);
        entity.setProperty("User",userKey);
        entity.setProperty("description", desc);
        entity.setProperty("timestamp", ts);
        entity.setProperty("image", image);

        this.key = datastore.put(entity);
    }

    protected Post(User usr, String image){
        this(usr, image, null);
    }

    /**
     * method to like a post
     * @param usr usr who likes the post
     * @param p the post to like
     * @throws EntityNotFoundException
     */
    public static void like(User usr, Post p) throws EntityNotFoundException {
        like(usr.getKey(), p.getKey());
    }

    /**
     * method to register a like for a post from a given user
     * @param userKey the datastore key of the user
     * @param postKey the datastore key of the post
     * @throws EntityNotFoundException
     */
    public static void like(Key userKey, Key postKey) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the post
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

       Entity like = new Entity("like",postKey.toString() + userKey.toString());
       like.setProperty("Post", postKey);
       like.setProperty("User",userKey);

       datastore.put(like);
    }

    /**
     * method to like the current post
     * @param usr the user liking the current post
     * @throws EntityNotFoundException
     */
    public void like(User usr) throws EntityNotFoundException {
       like(usr.getKey(), this.key);
    }

    /**
     * method to register a like from a given user
     * @param user the datastore key of the user
     * @throws EntityNotFoundException
     */
    public void like(Key user) throws EntityNotFoundException {
        like(user, this.key);
    }

    /**
     * method to unlike a post for a user
     * @param userKey the datastore key of the user who wants to unlike
     * @param postKey the datastore key of the post to unlike
     * @throws EntityNotFoundException
     */
    public static void unlike(Key userKey, Key postKey) throws EntityNotFoundException {
        //retrieve the datastore and the entity corresponding to the post
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        Key likeKey = KeyFactory.createKey("like",userKey.toString() + postKey.toString() );

        datastore.delete(likeKey);
    }

    /**
     * method for a given user to unlike a given post
     * @param usr the user who wants to unlike a post
     * @param p the post to unlike
     * @throws EntityNotFoundException
     */
    public static void unlike(User usr, Post p) throws EntityNotFoundException {
        unlike(usr.getKey(),  p.getKey());
    }

    /**
     * a mehtod for a given user to unlike the current post
     * @param usr the user who wants to dislike the given post
     * @throws EntityNotFoundException
     */
    public void unlike(User usr) throws EntityNotFoundException {
        unlike(usr.getKey(), this.key);
    }

    public Key getKey(){
        return this.key;
    }

    public static Key getKey(String postId){
        return KeyFactory.createKey("Post", postId);
    }

    public static Key getPostId(Post p){
        return p.getKey();
    }
}
