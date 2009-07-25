#!/usr/bin/perl

open(INPUT,"< ./output/$ARGV[0]-mt.txt");
open(OUTPUT1,"> ./output/$ARGV[0]-mt$ARGV[2].txt");
open(OUTPUT2,"> ./output/$ARGV[0]-mt.tmp");
$ARGV[1]=~s/\./\\\S/g;
while(<INPUT>)
{
  if(/$ARGV[1]/)
  {
    print OUTPUT1 $_;
  } else {
    print OUTPUT2 $_;
  }
}
close(INPUT);
close(OUTPUT1);
close(OUTPUT2);
`mv ./output/$ARGV[0]-mt.tmp ./output/$ARGV[0]-mt.txt`;
`./weblogo/seqlogo -S -s -6 -k 0 -w 15 -abcMnY -F PNG -f ./output/$ARGV[0]-mt$ARGV[2].txt  -o ./output_image/$ARGV[0]-mt$ARGV[2]`;
