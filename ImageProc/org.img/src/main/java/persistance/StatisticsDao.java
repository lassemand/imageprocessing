package persistance;

/**
 * Created by Lasse on 11/07/2016.
 */
public class StatisticsDao extends Connector {

    public static StatisticsDao uniqueInstance = new StatisticsDao();

    private StatisticsDao(){}

    public void insertNewStatistiscsTest(String name){
        getDbStatistics().insertOne(createInserter().append("testname", name));
    }
}
