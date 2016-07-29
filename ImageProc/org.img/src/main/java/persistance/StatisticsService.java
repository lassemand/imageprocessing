package persistance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Lasse on 11/07/2016.
 */
public class StatisticsService {

    public static StatisticsService uniqueInstance = new StatisticsService();

    private HttpRequestor requestor = new HttpRequestor();
    private final String baseUrl = "http://178.62.116.14:8080/";

    public StatisticsService(){}

    public void insertNewStatistiscsTest(String version, String type, long duration, String testname){
        try {
            JSONObject object = createJSON(version, type, duration, testname);
            requestor.executePost(baseUrl + "api/statistics/save", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public JSONArray retrieveAllStatistics(){
        String jsonString = requestor.executeGet(baseUrl + "api/statistics/retrieve");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject createJSON(String version, String type, long duration, String testname) throws JSONException, UnknownHostException {
        JSONObject object = new JSONObject();
        object.put("created", (new Date()).getTime());
        object.put("version", version);
        object.put("type", type);
        object.put("duration", duration);
        object.put("testname", testname);
        JSONObject pc = new JSONObject();
        String hostname = "Unknown";
        String ip = "Unknown";
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            ip = addr.getHostAddress();
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }
        pc.put("name", hostname);
        pc.put("ip", ip);
        object.put("pc", pc);
        return object;
    }
}
