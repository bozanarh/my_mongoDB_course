package com.bozana.proba;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

public class UpdateAndReplace {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> col = db.getCollection("people", Document.class);

		//drop collection in case there is something, so we do not work on stale data
		col.drop();

		//create 100 documents
		for( int i=0; i<8; i++){
				col.insertOne(new Document()
					.append("_id", i)
					.append("x", i));
					
			
		}
		/*
		//replace entire document #5 with new one
		col.replaceOne(Filters.eq("x", 5), new Document().append("x", 20)
				.append("_id", 5)
				.append("update", true));
		*/
		
		//update document #5 with new value		
		//col.updateOne(Filters.eq("x", 5), new Document("$set", new Document("x", 22)));
				
		//use upsert
		col.updateOne(Filters.eq("_id", 9), new Document("$set", new Document("x", 22)),
				new UpdateOptions().upsert(true));
		
		//if you specify upsert(false) it will not create new doc if it does not exists
		
		//update many documents (all those that have _id > 3, increment their x for 1:
		col.updateMany(Filters.gte("_id", 3), new Document("$inc", new Document("x", 1)));
		
		for( Document d : col.find().into(new ArrayList<Document>()) ){
			Helpers.printJson(d);
		}
	}

}
