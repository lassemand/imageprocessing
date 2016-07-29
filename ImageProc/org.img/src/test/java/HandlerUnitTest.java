import org.json.JSONException;
import org.junit.Test;
import statistics.Statistics;

/**
 * Created by Lasse on 29/07/2016.
 */
public class HandlerUnitTest {

    Statistics handler;

    public HandlerUnitTest() {
        try {
            handler = new Statistics();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void versionComparerTest(){

    }


}
