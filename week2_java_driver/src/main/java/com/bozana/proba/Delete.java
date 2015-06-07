package com.bozana.proba;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Delete {

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
					.append("_id", i));			
		}
		
		//delete all documents whose _id > 4
		col.deleteMany(Filters.gt("_id", 4));

		//delete one doc only, that has _id=3
		col.deleteOne(Filters.eq("_id", 3));
		for( Document d : col.find().into(new ArrayList<Document>()) ){
			Helpers.printJson(d);
		}
	}

}
