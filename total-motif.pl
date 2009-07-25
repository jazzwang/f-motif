#!/usr/bin/perl
open(INPUT,"< ./input/$ARGV[0].txt");
open(OUTPUT1,"> ./output/$ARGV[0]-tm$ARGV[2].txt");
$ARGV[1]=~s/\./\\\S/g;
while(<INPUT>)
{
  if(/$ARGV[1]/)
  {
    print OUTPUT1 $_;
  }
}
close(INPUT);
close(OUTPUT1);
`./weblogo/seqlogo -S -s -6 -k 0 -w 15 -abcMnY -F PNG -f ./output/$ARGV[0]-tm$ARGV[2].txt  -o ./output_image/$ARGV[0]-tm$ARGV[2]`;
