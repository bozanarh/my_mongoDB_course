package com.bozana.proba;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDocument {

	public static void main(String[] args) {
		
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("people", Document.class);
		Document hadzic = new Document("name", "Hadzic")
					.append("profession", "programmer");
		//drop previously created collection
		collection.drop();
		
		collection.insertOne(hadzic);
		Helpers.printJson(hadzic);
		
		//removal of this _id has not impact, next insert will succ regardless
		hadzic.remove("_id");
		Helpers.printJson(hadzic);
		
		collection.insertOne(hadzic);
		
	}

}
