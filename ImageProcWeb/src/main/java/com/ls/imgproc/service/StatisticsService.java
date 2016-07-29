package com.ls.imgproc.service;

import com.ls.imgproc.persistance.StatisticsDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Lasse on 25/07/2016.
 */
public class StatisticsService {

    public static StatisticsService uniqueInstance = new StatisticsService();
    private StatisticsDao persistance = StatisticsDao.uniqueInstance;

    private StatisticsService(){}



    private String convertIterableToString(FindIterable<Document> iterable){
        final JSONArray array = new JSONArray();
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                array.put(new JSONObject(document.toJson()));
            }
        });
        return array.toString();
    }

    public void insertStatistics(Document element){
        persistance.insertNewStatistiscs(element);
    }

    public String getStatistics(){
        FindIterable<Document> iterable = persistance.statistics();
        return convertIterableToString(iterable);
    }

    public Document createInserter(){
        return new Document();
    }
    public Document convertStringToBSON(String json){
        return Document.parse(json);
    }

}
