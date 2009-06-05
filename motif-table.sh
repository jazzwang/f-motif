#!/bin/bash
grep -e "$2" ./output/$1-mt.txt > ./output/$1-mt$3.txt
sed -e "/$2/ d" ./output/$1-mt.txt > ./output/$1-mt.tmp
mv ./output/$1-mt.tmp ./output/$1-mt.txt
./weblogo/seqlogo -S -s -6 -k 0 -w 15 -abcMnY -F PNG -f ./output/$1-mt$3.txt  -o ./output_image/$1-mt$3
