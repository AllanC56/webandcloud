package insta.api;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import endpoints.repackaged.com.google.api.Http;
import insta.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@WebServlet(
        name= "timeline",
        description= "retrieve the timeline of the user",
        urlPatterns = "/api/timeline"
)
public class TimelineServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String googleToken = req.getHeader("connectionToken");
        String userEmail = req.getHeader("userEmail");

        DatastoreService datastore  = DatastoreServiceFactory.getDatastoreService();

        boolean userIdentityVerified = User.googleAuthentification(googleToken);

        if(!userIdentityVerified){
            response.setStatus(401);
        } else {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                Key userKey = User.getKey(userEmail);

                Timestamp oldTs = User.getLastTimelineretrival(userKey);
                User.updateLastTimelineRetrieval(userKey);

                Query query = new Query("Post").setFilter(new Query.FilterPredicate("timestamp", Query.FilterOperator.GREATER_THAN_OR_EQUAL, oldTs));
                //TODO retourne tous les post, filtrer avec les user followed
                PreparedQuery preparedQuery = datastore.prepare(query);
                List<Entity> result = preparedQuery.asList(FetchOptions.Builder.withDefaults());
                response.getWriter().print(new Gson().toJson(result));

            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                response.setStatus(500);
            }

            response.setStatus(202);
        }
    }
}
