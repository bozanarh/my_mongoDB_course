package com.bozana.proba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

public class UpdateArray {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> col = db.getCollection("people", Document.class);

		//drop collection in case there is something, so we do not work on stale data
		col.drop();

		//create 100 documents
		for( int i=0; i<8; i++){
				List<Document> comments = new ArrayList<Document>();
				comments.add( new Document().append("author", "Bozana1 " + i)
						.append("num_likes", 1));
				comments.add( new Document().append("author", "Bozana2 " + i));
				comments.add( new Document().append("author", "Bozana3 " + i)
						.append("num_likes", 1));
						
				col.insertOne(new Document()
					.append("_id", i)
					.append("permalink", i)
					.append("comments", comments));
		}

		Document filter = new Document()
        		.append("permalink", 6);
		Document projection = new Document().append("comments", 1).append("permalink", 1);
				
		List<Document> xDocs = col.find(filter)
                .projection(projection)
                .into(new ArrayList<Document>());

		for( Document d : xDocs ){
			Helpers.printJson(d);
		}
		
		if( xDocs.size() == 1 ){
			Document d = xDocs.get(0);
			List<Document> c = (List<Document>) d.get("comments");
			for( Document dc : c ){
				System.out.println(dc.get("num_likes"));
			}
			col.updateOne(filter, new Document("$inc", new Document("comments.0.num_likes", 1)));
		}
		xDocs = col.find(filter)
                .projection(projection)
                .into(new ArrayList<Document>());
		
		for( Document d : xDocs ){
			Helpers.printJson(d);
		}
		
		//use upsert
/*		col.updateOne(Filters.eq("_id", 9), new Document("$set", new Document("x", 22)),
				new UpdateOptions().upsert(true));
	*/	
		//if you specify upsert(false) it will not create new doc if it does not exists
/*		
		//update many documents (all those that have _id > 3, increment their x for 1:
		col.updateMany(Filters.gte("_id", 3), new Document("$inc", new Document("x", 1)));
*/		
/*
		for( Document d : col.find().into(new ArrayList<Document>()) ){
			Helpers.printJson(d);
		}
*/	}

}
