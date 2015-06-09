package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class Homework2_2 {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("students");
		MongoCollection<Document> col = db.getCollection("grades", Document.class);
		
/*		col.drop();
		col.insertOne(new Document()
			.append("_id", 1)
			.append("student_id", 1)
			.append("type", "homework")
			.append("score", 10)
		);
		
		col.insertOne(new Document()
		.append("_id", 2)
		.append("student_id", 1)
		.append("type", "homework")
		.append("score", 13)
	);

		col.insertOne(new Document()
		.append("_id", 3)
		.append("student_id", 2)
		.append("type", "homework")
		.append("score", 15)
	);

		col.insertOne(new Document()
		.append("_id", 4)
		.append("student_id", 2)
		.append("type", "homework")
		.append("score", 30)
	);

		col.insertOne(new Document()
		.append("_id", 5)
		.append("student_id", 3)
		.append("type", "homework")
		.append("score", 10)
	);

		col.insertOne(new Document()
		.append("_id", 6)
		.append("student_id", 3)
		.append("type", "homework")
		.append("score", 33)
	);*/

		Document filter = new Document()
				.append("type", "homework");
		Document projection = new Document()
				.append("type",1)
				.append("student_id", 1)
				.append("score", 1)
				.append("_id", 1);
		
		Document sort = new Document().append("student_id", 1)
				.append("score", 1);
		List<Document> xDocs = col.find(filter)
				.projection(projection)
				.sort(sort)
				.into(new ArrayList<Document>());
		
		//print all doc so I can see them
		for(Document curr : xDocs){
			Helpers.printJson(curr);
		}
		
		Document prev = null, lowestScore = null;
		for(Document curr : xDocs){
			if( prev == null ){ //first one
				prev = curr;
				lowestScore = curr;
				continue;
			}
			//mid element for the same student where score is not the last one
			//if( prev.getDouble("student_id").intValue() == curr.getDouble("student_id").intValue()){
			if( prev.getDouble("student_id").intValue() == curr.getDouble("student_id").intValue()){
				prev = curr;
				continue;
			}else{//mid element not the same student
				//System.out.println("Delete: " + lowestScore.getDouble("student_id") + ", score: " + lowestScore.getDouble("score"));
				//col.deleteOne(Filters.eq("student_id", lowestScore.getDouble("student_id")));
				col.deleteOne(Filters.eq("_id", lowestScore.getObjectId("_id")));
				lowestScore = curr;
				prev=curr;
			}
		}
		//delete the lowest score for the last student
		//System.out.println("Delete: " + lowestScore.getDouble("student_id") + ", score: " + lowestScore.getDouble("score"));
		//col.deleteOne(Filters.eq("student_id", lowestScore.getDouble("student_id")));
		col.deleteOne(Filters.eq("_id", lowestScore.getObjectId("_id")));
		
		System.out.println("There are total : " + col.count(filter) + " documents");
	}

}
