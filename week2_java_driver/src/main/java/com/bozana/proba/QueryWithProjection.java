package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class QueryWithProjection {

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
					.append("y", new Random().nextInt(100))
					.append("i", i));
		}

		Bson filter = Filters.and(Filters.eq("x", 0), Filters.gt("y", 10), Filters.lt("y", 90));
		
		//find count:
		//count = col.count(filter);
		long count = col.count(filter);
		System.out.println("There are " +  count + " docs that match x=0, y> 10");
		
		//1st way to exclude x and _id from the projections
		/*
		Document projection = new Document("x", 0)
				.append("_id", 0);
		
		List<Document> xDocs = col.find(filter)
				.projection(projection)
				.into(new ArrayList<Document>());
		System.out.println("## Document with x=0/y>10/y<90");
		*/
		
		//1st way to include y and i only
		/*
		Document projection = new Document("y", 1)
				.append("i", 1)
				.append("_id", 0);
		*/

		//2nd way to do that:
		Bson projection = Projections.fields(Projections.include("y", "i"), Projections.exclude("_id"));


		List<Document> xDocs = col.find(filter)
				.projection(projection)
				.into(new ArrayList<Document>());
		System.out.println("## Document with x=0/y>10/y<90");
		
		
		for(Document d : xDocs){
			Helpers.printJson(d);
		}

	}

}
