package statistics;

import logic.GaussianFilter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import persistance.StatisticsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lasse on 29/07/2016.
 */
public class Statistics {

    private StatisticsService service = new StatisticsService();
    //Receives all statistics and maps it into this, in order to know which ones has to be updated.
    private HashMap<String, JSONObject> previousInformation;

    public Statistics() throws JSONException {
        JSONArray array = new JSONArray();
        HashMap<String, JSONObject> tempInformation = new HashMap<>();
        for(int a = 0; a<array.length(); a++){
            JSONObject statistic = new JSONObject();
            if(statistic.has("type") && tempInformation.containsKey(statistic.getString("type")) &&
                    compareTwoStatisticsSameType(tempInformation.get(statistic.getString("type")).getString("version"), statistic.getString("version"))){
                tempInformation.put(statistic.getString("type"), statistic);
            }
            else if(statistic.has("type") && !tempInformation.containsKey(statistic.getString("type"))){
                tempInformation.put(statistic.getString("type"), statistic);
            }
        }
        previousInformation = tempInformation;
    }

    /**
     * Version compare of two statistics
     * @param version1 version of statistics
     * @param version2 version of statistics
     * @return Whether version1 is greater than version2
     */
    private boolean compareTwoStatisticsSameType(String version1, String version2){
        String[] type1Array = version1.split("\\.");
        String[] type2Array = version2.split("\\.");
        for(int a = 0; a<Math.min(type1Array.length, type2Array.length); a++){
            if(Integer.parseInt(type1Array[a]) < Integer.parseInt(type2Array[a])) return true;
            else if(Integer.parseInt(type1Array[a]) > Integer.parseInt(type2Array[a])) return false;
        }
        return type2Array.length > type1Array.length;
    }

    private class StatisticsHolder{
        private String type;
        private StatisticsInterface inter;

        public StatisticsHolder(String type, StatisticsInterface inter){
            this.type = type;
            this.inter = inter;
        }
    }

    public void createStatistics(){
        List<StatisticsHolder> allStatistics = new ArrayList<StatisticsHolder>();
        allStatistics.add(new StatisticsHolder("1", new GaussianFilter(1)));
        for(StatisticsHolder holder: allStatistics){
            if(!previousInformation.containsKey(holder.type)){
                long start = System.currentTimeMillis();
                holder.inter.createStatestic();
                long end = System.currentTimeMillis();
                createStatistics(holder, start - end);
            }

        }
    }

    private void createStatistics(StatisticsHolder holder, long duration){
        String testName = holder.inter.getTestName().name();
        String version = holder.inter.getVersion();
        service.insertNewStatistiscsTest(version, holder.type, duration, testName);
    }



}
