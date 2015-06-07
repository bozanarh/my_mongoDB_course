package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class FindWithFilters {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> col = db.getCollection("people", Document.class);

		//drop collection in case there is something, so we do not work on stale data
		col.drop();

		//create 100 documents
		for( int i=0; i<10; i++){
				col.insertOne(new Document()
					.append("x", new Random().nextInt(2))
					.append("y", new Random().nextInt(100)));
		}

		//find count
		long count = col.count();
		System.out.println("There are total of " + count + " documents.");

		System.out.println("All docs:");
		List<Document> all = col.find().into(new ArrayList<Document>());
		System.out.println("All documents:");
		for( Document d : all ){
			Helpers.printJson(d);
		}
		
		//1. form: Create a filter that fitlers docs where x=0 && y>10 && y < 90
		Bson filter = new Document("x", 0)
			.append("y", new Document("$gt",10))
			.append("y", new Document("$lt", 90));
		
		//2. this is second way to create filter, using builder:
		Bson filter2 = Filters.and(Filters.eq("x", 0), Filters.gt("y", 10), Filters.lt("y", 90));
		
		//find count:
		//count = col.count(filter);
		count = col.count(filter2);
		System.out.println("There are " +  count + " docs that match x=0, y> 10");
		
		//List<Document> xDocs = col.find(filter).into(new ArrayList<Document>());
		List<Document> xDocs = col.find(filter2).into(new ArrayList<Document>());
		System.out.println("## Document with x=0/y>10:");
		for(Document d : xDocs){
			Helpers.printJson(d);
		}

	}

}
