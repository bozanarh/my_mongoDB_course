package com.bozana.proba;

import org.bson.BsonDocument;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BasicDBOps {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		//MongoCollection<Document> collection = db.getCollection("scores2");
		MongoCollection<BsonDocument> collection = db.getCollection("scores2", BsonDocument.class);
	}

}
