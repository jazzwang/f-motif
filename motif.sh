#!/bin/bash
START=$(date +%s)
java -cp . fmotif.MotifFinderV6_5 $1.txt $2 $3 $4 $5 $6 $7 $8 $9 ${10} > ./output/$1.txt 2>&1
END=$(date +%s)
DIFF=$(( $END - $START ))
echo "[{ 'input_file' : \"$1.txt\", 'background' : \"$2\", 'encode' : \"$3\", 'match' : \"$5\", 'freq' : \"$4\", 'cluster' : \"$6\", 'flat' : \"$7\", 'repeat' : \"$8\", 'threshold' : \"$9\", 'significance' : \"${10}\", 'time' : \"$DIFF\" }]" > ./output/$1.json
