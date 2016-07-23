package persistance;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by Lasse on 11/07/2016.
 */
public class Connector {

    private MongoCollection dbStatistics;

    public Connector(){
        MongoClient mongoClient;
        mongoClient = new MongoClient("127.0.0.1", 27017);
        MongoDatabase db = mongoClient.getDatabase("img");
        dbStatistics = (MongoCollection) db.getCollection("performanceStatistics");
}

    protected MongoCollection getDbStatistics() {
        return dbStatistics;
    }

    public Document createInserter(){
        return new Document();
    }
}
