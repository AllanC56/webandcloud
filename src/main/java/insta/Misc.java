package insta;

import com.google.appengine.api.datastore.Entity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/* Classe qui fournit des fonctions utiles partout */
public class Misc {

    public static List<Entity> parseQuery(List<Entity> input){
        return quickSort(input);

    }


    public static List<Entity> quickSort(List<Entity> array) {
        if (array.isEmpty())
            return array;
        else {
            Entity pivot = array.get(0);

            List<Entity> less = new LinkedList<>();
            List<Entity> pivotList = new LinkedList<>();
            List<Entity> more = new LinkedList<>();

            // Partition
            for (Entity i: array) {
                Timestamp ts = (Timestamp) i.getProperty("timestamp");
                Timestamp tsPivot = (Timestamp) pivot.getProperty("timestamp");
                if (ts.before(tsPivot))
                    less.add(i);
                else if (ts.after(tsPivot))
                    more.add(i);
                else
                    pivotList.add(i);
            }

            // Recursively sort sublists
            less = quickSort(less);
            more = quickSort(more);

            // Concatenate results
            less.addAll(pivotList);
            less.addAll(more);
            return less;
        }
    }

}
