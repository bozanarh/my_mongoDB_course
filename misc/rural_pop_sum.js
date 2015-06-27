/*
 * This is homework 5.4:
 *  - Import zips.js file and calculate number of people who live in
 *    rural areas (those where city starts with number, i.e. city 
 *    is marked to be the same as zipcode
 *
 *  1. Import js file:
 *  	mongoimport -d test -c zips --drop zips.js
 *  2. Run this script from the shell:
 *  	cat rural_pop_sum.js | mongo
 *
 */

//DB name: test
use test

/* 
 * this is just for debugging purpose. By default shell sends only
 * 20 docs. I want more, so I better see them
 */
DBQuery.shellBatchSize = 100000

db.zips.aggregate([
{$project: 
	{
		//I want to easily spot cities whose first letter is a number. Project the first letter
		//in a separate field named: "first_char"
		first_char: {$substr : ["$city",0,1]},
		pop : 1
	}	 
},
{$match : { 
	//show me only those documents where first_char is a number
	"first_char" : { $regex : "^[0-9]" }
	}

},
{$group : {
	//group by null, meaning squash them all together and calculate the sum on pop field
	_id : null,
	total : { "$sum" : "$pop"}
	}
}
])
