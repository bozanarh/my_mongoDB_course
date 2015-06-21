# First, I enabled debug log level, so I can see which queries mongo runs when I visit specified pages. So, I did the following in the mongo shell:

>db.setLogLevel(1, "query")

# Now I could notice that it was executing queries on tags, permalink and was sorting on date (desc). So, I set up indexes on those:

> use blog
switched to db blog
> db.posts.createIndex({tags:1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 4,
	"numIndexesAfter" : 4,
	"note" : "all indexes already exist",
	"ok" : 1
}
> db.posts.createIndex({date:-1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 4,
	"numIndexesAfter" : 4,
	"note" : "all indexes already exist",
	"ok" : 1
}
> db.posts.createIndex({permalink:1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 4,
	"numIndexesAfter" : 4,
	"note" : "all indexes already exist",
	"ok" : 1
}
> 

