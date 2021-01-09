package me.fluffix.manager;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoManager {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoManager() {
        this.mongoClient = new MongoClient("127.0.0.1", 27017);
        this.mongoDatabase = mongoClient.getDatabase("reportsystem");
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
