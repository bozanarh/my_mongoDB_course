package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class CleanOrphans {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("photos");
		MongoCollection<Document> albums = db.getCollection("albums", Document.class);
		MongoCollection<Document> images = db.getCollection("images", Document.class);
		
		//give me all images
		Document projection = new Document().append("_id", 1);
		
		List<Document> imagesDocs = images.find().projection(projection)
        .into(new ArrayList<Document>());
		
		for( Document d : imagesDocs ){
			Integer imageId = d.getDouble("_id").intValue();
			//System.out.println("id=" + d.getDouble("_id").intValue());
			Document filter = new Document().append("images", imageId);
			
			List<Document> photosDocs = albums.find(filter).into(new ArrayList<Document>());
			
			if( photosDocs.size() == 0){
				System.out.println("Orphan image: " + imageId);
				images.deleteOne(Filters.eq("_id", imageId));
			}
			
		}
		
	}
}
