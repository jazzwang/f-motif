#!/bin/bash

cd /var/www/motif_finder
java -cp . MotifFinderV5 $1 > ./output/$1.txt
