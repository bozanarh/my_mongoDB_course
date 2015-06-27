/*
 * Calculate average population of cities in CA and NY with population over
 * 25,000 
 *
 * 1. Import small_zips.js file
 *   mongoimport -d test -c zips --drop small_zips.json
 * 2. Run this scipt 
 *   cat avg_population.js | mongo 
 *
 */

// DB is named test
use test

db.zips.aggregate([
//filter on NY and CA states only and only on those cities whose pop is over 25000
{ $match :
	{
		$and : [
			{"state": { $in : ["NY", "CA"] }},
			{"pop" : { $gt: 25000}}
			]
	}
},
//project only pop
{ $project : 
	{ "pop" : 1}
},
//squash them all together and calculate avg
{ $group :
	{ 
		"_id": null,
		"avg" : {"$avg" : "$pop"}
	}
}
])
