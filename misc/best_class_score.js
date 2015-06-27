/*
 * This is for homework 5.3 
 * Import grades.js DB and find the class that has the
 * highest score, excluding quiz scores.
 *
 * 1. Import DB:
 *    mongoimport -d test -c grades --drop grades.json
 *
 * 2. Run this scipt from shell:
 *    cat best_class_score.js | mongo
 *
 */

// DB name is test
use test

//for debugging purpose, I want to see up to 10000 doc per screen
//DBQuery.shellBatchSize = 100000

db.grades.aggregate([
//scores are kept in array, unwind so we get rid of array
{ $unwind : "$scores" },
{ 
  // filter so we have only homeworks and exams
  $match : 
	{
	"scores.type" : { $in : [ "homework", "exam" ]}
	}
},
{
  $group :
	{
	//group by student id and class id and calculate average for each student
	"_id" : {
		"student_id" : "$student_id",
		"class_id" : "$class_id"
		},
		"scores" : { "$avg" : "$scores.score"}
	}
},
{
  //group again by class and calculate the average
  $group :
	{
	"_id" : "$_id.class_id",
	"score" : { "$avg" : "$scores" }
	}	
},
{
  //sort by score
  $sort : { "score" : -1	}
}
])
