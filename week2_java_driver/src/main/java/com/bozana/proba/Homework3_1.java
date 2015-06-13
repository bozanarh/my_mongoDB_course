package com.bozana.proba;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Homework3_1 {

	public static void main(String[] args) {
		//standard procedure - create client, db and collection
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("school");
		MongoCollection<Document> col = db.getCollection("students", Document.class);
		
		/*
		 * Adding some collections
		 */
		/*
		col.drop();
		List<Document> scores = new ArrayList();
		scores.add(new Document().append("type", "exam").append("score", 1.463179736705023));
		scores.add(new Document().append("type", "quiz").append("score", 11.78273309957772));
		scores.add(new Document().append("type", "homework").append("score", 6.676176060654615));
		scores.add(new Document().append("type", "homework").append("score", 35.8740349954354));
		col.insertOne(new Document()
			.append("_id", 0)
			.append("name", "aimee Zank")
			.append("scores", scores)
		);

		scores.clear();
		scores.add(new Document().append("type", "exam").append("score", 60.06045071030959 ));
		scores.add(new Document().append("type", "quiz").append("score", 52.79790691903873));
		scores.add(new Document().append("type", "homework").append("score", 71.76133439165544));
		scores.add(new Document().append("type", "homework").append("score", 34.85718117893772));
		col.insertOne(new Document()
			.append("_id", 1)
			.append("name", "Aurelia Menendez")
			.append("scores", scores)
		);
			
		scores.clear();
		scores.add(new Document().append("type", "exam").append("score", 67.03077096065002 ));
		scores.add(new Document().append("type", "quiz").append("score", 6.301851677835235));
		scores.add(new Document().append("type", "homework").append("score", 20.18160621941858));
		scores.add(new Document().append("type", "homework").append("score", 66.28344683278382));
		col.insertOne(new Document()
			.append("_id", 2)
			.append("name", "Corliss Zuk")
			.append("scores", scores)
		);
		*/
		
		Document projection = new Document()
			.append("scores", 1)
			.append("_id", 1);
		List<Document> xDocs = col.find()
				.projection(projection).into(new ArrayList<Document>());
		
		//print all doc so I can see them
		for(Document curr : xDocs){
			Integer id = curr.getDouble("_id").intValue();
			ArrayList<Document> scoresLoc = (ArrayList) curr.get("scores");
			Double score1 = null;
			Double score2 = null;
			for( Document score : scoresLoc){
				if( score.getString("type").equals("homework")){
					if( score1 == null ) score1 = score.getDouble("score");
					else score2 = score.getDouble("score");
				}
			}
			//TODO: delete score that is lower
			if( score1 > score2 ){
				score1 = score2;
			}
			Document match = new Document("_id", id); // to match your document
			Document update = new Document("scores", new Document("score", score1));
			col.updateMany(match, new Document("$pull", update));
		}
		List<Document> xDocsEnd = col.find()
				.projection(projection).into(new ArrayList<Document>());
		for(Document curr : xDocsEnd){
			Helpers.printJson(curr);
		}
	}

}
