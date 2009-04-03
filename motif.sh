#!/bin/bash

cd /var/www/f-motif
java -cp . MotifFinderV5 $1 > ./output/$1.txt
