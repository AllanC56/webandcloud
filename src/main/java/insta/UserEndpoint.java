package insta;

/*
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Api(name = "instApi", version = "v1", namespace =
    @ApiNamespace(ownerDomain = "tinyinsta.com", ownerName = "tinyinsta.com")
)
public class UserEndpoint {

    @ApiMethod(name = "submitPost", path = "user/newPost", httpMethod = HttpMethod.POST)
    public Entity addPost(User user, Post post) throws Exception {
        /*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //verify the credentials
        if(user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }
        Key userKey = Utilitary.getUserKey(user);
        post.setPosterKey(userKey);
        post.generateKey();
        Entity postEntity = post.toEntity();
        List<Entity> likeCounters = post.createLikesCounter(postEntity.getKey());
        datastore.put(postEntity);
        datastore.put(likeCounters);
        addReceiversPost(userKey, postEntity.getKey());
        EmbeddedEntity poster = Utilitary.getEmbeddedProfile(userKey);
        postEntity.setProperty("poster", poster);
        changeNumberPosts(userKey, 1);
        return postEntity;
    }
}
*/
