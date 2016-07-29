import org.json.JSONException;
import statistics.Statistics;

/**
 * Created by Lasse on 10/07/2016.
 */
public class App {

    public static void main(String[] args) {
        try {
            Statistics handler = new Statistics();
            handler.createStatistics();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
