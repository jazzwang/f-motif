#!/bin/bash
grep -e "$2" ./input/$1.txt > ./output/$1-tm$3.txt
./weblogo/seqlogo -S -s -6 -k 0 -w 15 -abcMnY -F PNG -f ./output/$1-tm$3.txt  -o ./output_image/$1-tm$3
