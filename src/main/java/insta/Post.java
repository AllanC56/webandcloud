package insta;

import com.google.appengine.api.datastore.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

public class Post {
    private Key key;
    private Entity entity;

    protected Post(User usr, String image, String desc){
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Timestamp ts = new Timestamp(new Date().getTime());
        String id =  usr.getKey().toString() + ts;

        this.entity = new Entity("Post",id);
        this.entity.setProperty("User",usr.getKey());
        this.entity.setProperty("description", desc);
        this.entity.setProperty("timestamp", ts);
        this.entity.setProperty("image", image);
        this.entity.setProperty("likers",new HashSet<Key>());
        this.entity.setProperty("likeCount", 0);

        this.key = datastore.put(this.entity);
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
     * @param user the datastore key of the user
     * @param post the datastore key of the post
     * @throws EntityNotFoundException
     */
    public static void like(Key user, Key post) throws EntityNotFoundException {
        //TODO premier jet, rendre thread safe
        //retrieve the datastore and the entity corresponding to the post
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity postEntity = datastore.get(post);

        //retrieving the list of users who liked the post, updating it and updating the entity
        HashSet<Key> likers = (HashSet<Key>) postEntity.getProperty("likers");
        likers.add(user);
        postEntity.setProperty("likers",likers);

        //retrieving the number of like, updating it and updating the entity with the new value
        int like = (int) postEntity.getProperty("likeCount");
        postEntity.setProperty("likeCount", like +1);
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
     * @param user the datastore key of the user who wants to unlike
     * @param post the datastore key of the post to unlike
     * @throws EntityNotFoundException
     */
    public static void unlike(Key user, Key post) throws EntityNotFoundException {
        //TODO premier jet, rendre thread safe
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity postEntity = datastore.get(post);

        HashSet<Key> likers = (HashSet<Key>) postEntity.getProperty("likers");
        likers.remove(user);
        postEntity.setProperty("likers",likers);

        int like = (int) postEntity.getProperty("likeCount");
        postEntity.setProperty("likeCount", like - 1);
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

    public static Key getPostId(Post p){
        return p.getKey();
    }
}
