// DB name is test
use enron

//for debugging purpose, I want to see up to 10000 doc per screen
//DBQuery.shellBatchSize = 100000

db.messages.aggregate([
//scores are kept in array, unwind so we get rid of array
{ $unwind : "$headers.To" },
{ $project : { "_id" : 1,
		"headers.To" : { $toLower : "$headers.To"},
		"headers.From" : { $toLower : "$headers.From"}
	}
},
{ $group : {
	"_id" : { 
		"_id" : "$_id", 
		"headers" : "$headers"
		}
	}
},
{ $group : {
	"_id" : {
		"From" : "$_id.headers.From",
		"To" : "$_id.headers.To"
		},
	"count" : { "$sum" : 1 },
	}
},
{ $project : { "_id" : 1 , 
		"count" : 1,
		"equal" : {
            		$eq : ["$_id.To", "$_id.From"] 
		}
	}
},
{ $match : { "equal" : false }
},
{ $sort : { "count" : -1 } }
], { allowDiskUse : true }).pretty()
