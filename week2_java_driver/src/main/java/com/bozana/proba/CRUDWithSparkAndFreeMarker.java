package com.bozana.proba;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class CRUDWithSparkAndFreeMarker {

	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CRUDWithSparkAndFreeMarker.class, "/");

		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("test");
		final MongoCollection<Document> col = db.getCollection("people", Document.class);

		//drop collection in case there is something, so we do not work on stale data
		col.drop();

		col.insertOne(new Document().append("name", "mongoDB"));
		
		Spark.get(new Route("/") {
			@Override
			public Object handle(Request arg0, Response arg1) {
				StringWriter stringWriter = null;
				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					stringWriter = new StringWriter();
					/*
					Map<String, Object> helloMap = new HashMap<String, Object>();
					helloMap.put("name", "Freemarker");
					helloTemplate.process(helloMap, stringWriter);
					*/
					Document d = col.find().first();
					helloTemplate.process(d, stringWriter);
					System.out.println(stringWriter);
				} catch (Exception e) {
					halt(500);
					e.printStackTrace();
				}
				return stringWriter;
			}
		});
	}

}
