package course;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

        // XXX HW 3.2,  Work Here
    	Document filter = new Document()
    		.append("permalink", permalink);
        Document post = postsCollection.find(filter).first();

        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // XXX HW 3.2,  Work Here
        // Return a list of DBObjects, each one a post from the posts collection
    	Document sort = new Document()
    		.append("date", -1);
        List<Document> posts = postsCollection.find().sort(sort).into(new ArrayList<Document>());

        return posts;
    }


    public String addPost(String title, String body, List<String> tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        // XXX HW 3.2, Work Here
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Build the post object and insert it
        ArrayList<String> tagsArray = new ArrayList();
        for( String t : tags ){
        	tagsArray.add(t);
        }
        Document post = new Document()
        	.append("title", title)
        	.append("author", username)
        	.append("body", body)
        	.append("permalink", permalink)
        	.append("tags", tagsArray)
        	.append("comments", new ArrayList<String>())
        	.append("date", new Date());

        postsCollection.insertOne(post);
        return permalink;
    }




    // White space to protect the innocent








    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {
    	
        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments
    	
    	Document newComment = new Document()
			.append("author", name)
			.append("body", body);
    	
    	if( email != null ) newComment.append("email", email);
    	
    	Document d = findByPermalink(permalink);
    	ArrayList<Document> comments = (ArrayList<Document>) d.get("comments");
    	comments.add(newComment);
    	postsCollection.updateOne(Filters.eq("permalink", permalink), new Document("$set", d),
    			new UpdateOptions().upsert(true));
    }
}
