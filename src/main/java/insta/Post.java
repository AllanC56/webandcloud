package insta;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.repackaged.com.google.datastore.v1.Datastore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

public class Post {
    private String id;
    private Entity entity;

    protected Post(User usr, String image, String desc){
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        this.id =  usr.getId() + new Timestamp(new Date().getTime());

        this.entity = new Entity("Post",id);
        this.entity.setProperty("User",usr.getId());
        this.entity.setProperty("description", desc);
        this.entity.setProperty("image", image);
        this.entity.setProperty("likers",new HashSet<String>());
        this.entity.setProperty("likeCount", 0);
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
        p.like(usr);
    }

    //TODO premier jet, rendre thread safe
    /**
     * method to like the current post
     * @param usr the user liking the current post
     * @throws EntityNotFoundException
     */
    public void like(User usr) throws EntityNotFoundException {
        //retrieve th datastore and the entity corresponding to the post
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity c = datastore.get(this.entity.getKey());

        //retrieving the list of users who liked the post, updating it and updating the entity
        HashSet<String> likers = (HashSet<String>) c.getProperty("likers");
        likers.add(usr.getId());
        c.setProperty("likers",likers);

        //retrieving the number of like, updating it and updating the entity with the new value
        int like = (int) c.getProperty("likeCount");
        c.setProperty("likeCount", like +1);
    }

    /**
     * method to for a given user to unlike a given post
     * @param usr the user who wants to unlike a post
     * @param p the post to unlike
     * @throws EntityNotFoundException
     */
    public static void unlike(User usr, Post p) throws EntityNotFoundException {
        p.unlike(usr);
    }

    //TODO premier jet, rendre thread safe

    /**
     * a mehtod for a given user to unlike the current post
     * @param usr the user who wants to dislike the given post
     * @throws EntityNotFoundException
     */
    public void unlike(User usr) throws EntityNotFoundException {
        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();
        Entity c = datastore.get(this.entity.getKey());
        HashSet<String> likers = (HashSet<String>) c.getProperty("likers");
        likers.remove(usr.getId());
        c.setProperty("likers",likers);

        int like = (int) c.getProperty("likeCount");
        c.setProperty("likeCount", like - 1);
    }

    public String getId(){
        return this.id;
    }

    public static String getPostId(Post p){
        return p.getId();
    }
}
