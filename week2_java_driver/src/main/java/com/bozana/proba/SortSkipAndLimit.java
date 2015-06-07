package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class SortSkipAndLimit {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> col = db.getCollection("people", Document.class);

		//drop collection in case there is something, so we do not work on stale data
		col.drop();

		//create 100 documents
		for( int i=0; i<10; i++){
			for( int j=0; j< 10; j++ ){
				col.insertOne(new Document()
					.append("i", i)
					.append("j", j));
			}
		}
		Bson projection = Projections.fields(Projections.include("i", "j"), Projections.excludeId());
		//1st way: Sort i ascending and j descending
		Bson sort = new Document("i", 1)
			.append("j", -1);
		
		//2nd way with Builder
		Bson sort2 = Sorts.orderBy(Sorts.ascending("i"), Sorts.descending("j"));
		
		//if you wish to use builder for descending both i and j:
		Bson sort3 = Sorts.descending("i", "j");
		
		List<Document> all = col.find()
				.sort(sort)
		//		.sort(sort2)
		//		.sort(sort3)
				.projection(projection)
				.skip(2) 		//want to skip first 2
				.limit(5) 		//want to limit to only 5
				.into(new ArrayList<Document>());
		
		for( Document d : all ){
			Helpers.printJson(d);
		}
	}

}
