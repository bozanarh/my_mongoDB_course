package com.bozana.proba;

import java.awt.PrintJob;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class FindDocument {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> col = db.getCollection("people", Document.class);
		
		//drop collection in case there is something, so we do not work on stale data
		col.drop();
		
		//create 100 documents
		for( int i=0; i<100; i++){
			col.insertOne(new Document("i", i));
		}

		//find count
		long count = col.count();
		System.out.println("There are " + count + " documents.");
		
		//find first document
		Document first = col.find().first();
		System.out.println("First document:");
		Helpers.printJson(first);
		
		//find all documents
		List<Document> all = col.find().into(new ArrayList<Document>());
		System.out.println("All documents:");
		for( Document d : all ){
			Helpers.printJson(d);
		}
		
		//find using iteration
		MongoCursor<Document> cursor = col.find().iterator();
		System.out.println("All documents using iterator:");
		while( cursor.hasNext()){
			Document d = cursor.next();
			Helpers.printJson(d);
		}

	}

}
