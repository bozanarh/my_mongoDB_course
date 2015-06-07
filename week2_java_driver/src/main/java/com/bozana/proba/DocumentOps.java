package com.bozana.proba;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.bozana.proba.Helpers;

public class DocumentOps {

	public static void main(String[] args) {
		Document document = new Document()
							.append("str", "Hello mongoDB")
							.append("int", 1)
							.append("long", 1L)
							.append("double", 2.2)
							.append("date", new Date())
							.append("docId", new ObjectId())
							.append("bool", true)
							.append("null", null)
							.append("array", Arrays.asList(1,2,3,4))
							.append("embedded_doc", new Document("x", 0));
		Helpers.printJson(document);

	BsonDocument bsonDoc = new BsonDocument("str", new BsonString("Hello mongoDB"));
	
	}

}
