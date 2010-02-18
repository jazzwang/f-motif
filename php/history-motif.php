<?
  /*
   * 2009-08-19 modified from motif-table.php
   */

  $path	    = str_replace("/php","",getcwd());
  $input    = fopen($path . "/output/" . $_POST["id"] . ".txt","r");
  $input_t  = $_POST["id"];
  $state    = 0;
  $tmcount  = 0;
  $mtcount  = 0;
  chdir($path);
  if(!file_exists($path . "/output/" . $_POST["id"] . ".json"))
  {
    echo "NOT_YET";
    return 1;
  }
  if($input)
  {
    $total = "\"";
    $table = "\""; 
    while(!feof($input))
    {
      $buffer = fgets($input, 4096);
      if(preg_match("/Strongest Motif/", $buffer) !=0)  { $state = 0; }
      if(preg_match("/RE found/", $buffer) !=0)		{ $state = 0; }
      if($state == 1)
      {
	$tmcount = $tmcount + 1;
	list($pattern, $score, $fgmatch) = split('[:X]', $buffer);
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$total = $total . "<tr><td>$tmcount</td><td>" . str_replace("\n","</td><td><a href='output/$input_t-tm$tmcount.txt' target='_blank'>View</a></td></tr>",$buffer);
	$total = $total . "<tr><td colspan='5'><img src='output_image/$input_t-tm$tmcount.png'></td></tr>";
	$cmd    = $path . "/total-motif.pl " . $input_t . " $pattern $tmcount";
	if(!file_exists($path . "/output/$input_t-tm$tmcount.txt"))
	{
	  //echo "$cmd;\n";
	  system($cmd, $ret);
	}
      }
      if($state == 2)
      {
	$mtcount = $mtcount + 1;
	list($pattern, $toal, $freq, $bgmatch, $score) = split('[:X]', $buffer);
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$buffer = str_replace("Motif Score = ","</td><td>",$buffer);
	$table = $table . "<tr><td>$mtcount</td><td id='MT$mtcount'>" . str_replace("\n","</td><td><a href='output/$input_t-mt$mtcount.txt' target='_blank'>View</a></td></tr>",$buffer);
	$table = $table . "<tr><td colspan='7'><img src='output_image/$input_t-mt$mtcount.png'></td></tr>";
	$cmd    = $path . "/motif-table.pl " . $input_t . " $pattern $mtcount";
	if(!file_exists($path . "/output/$input_t-mt$mtcount.txt"))
	{
	  //echo "$cmd;\n";
	  system($cmd, $ret);
	}
      }
      if(preg_match("/Total Motifs/", $buffer) !=0)   { $state = 1; }
      if(preg_match("/". $input_t ."/", $buffer) !=0) { $state = 2; }
    }
    $total = $total . "\"";
    $table = $table . "\"";
  }
  echo "[{ \"total\": " . $total . ", \"table\": " . $table . ", \"tm_count\": " . $tmcount . ", \"mt_count\": " . $mtcount . "}]";
?>
