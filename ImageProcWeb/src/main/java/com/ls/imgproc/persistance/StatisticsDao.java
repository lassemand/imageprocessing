package com.ls.imgproc.persistance;

import com.mongodb.client.FindIterable;
import org.bson.Document;

/**
 * Created by Lasse on 11/07/2016.
 */
public class StatisticsDao extends Connector {

    public static StatisticsDao uniqueInstance = new StatisticsDao();

    private StatisticsDao(){}

    public void insertNewStatistiscs(Document element){
        getDbStatistics().insertOne(element);
    }

    public FindIterable<Document> statistics(){
        return getDbStatistics().find();
    }
}
