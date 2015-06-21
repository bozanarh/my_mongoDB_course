In mongo shell you can write plain js script to populate data, e.g.

>var a=0;
>var b=0;
>var c=0;

>for( a=0; a<5; a++){ 
	for( b=0; b<5; b++ ){ 
		for( c=0; c<5; c++){ 
			db.foo.insert({"a":a, "b":b, "c":c}); 
		} 
	} 
}

# Now you can create indexes on a, b and c
#
> db.foo.createIndex( { a : 1, b : 1, c : 1 } )

# explain me execution stat
> var exp=db.foo.explain("executionStats")
> exp.find({a:3})

# pay attention: If you create index on a_b_c, it will apply only when
# searching one of the following:
# a 
# a and b 
# a and b and c  
# It will *not* use inexes when you try to search one of the following:
# b
# b and c
# c

