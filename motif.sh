#!/bin/bash

cd /var/www/f-motif
java -cp . MotifFinderV5 $1.txt $2 $3 $4 > ./output/$1.txt
