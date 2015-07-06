#!/bin/bash

cd /home/bozana/projects/tools/mongoDB/mongodb-linux-x86_64-3.0.3/bin

sudo PATH=$PATH:/home/bozana/projects/tools/mongoDB/mongodb-linux-x86_64-3.0.3/bin /home/bozana/projects/my_mongoDB_course/week6/create_replica_set.19830756d7a7.sh

cat /home/bozana/projects/my_mongoDB_course/week6/init_replica.f87e369ec321.js | mongo

# Now go to one of them, e.g.:
# mongo --port 27017
