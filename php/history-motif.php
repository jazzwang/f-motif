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
	list($pattern, $score, $fgmatch) = split('[:X]', $buffer);
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$total = $total . "<tr><td>" . str_replace("\n","</td><td><a href='output/$input_t-tm$tmcount.txt' target='_blank'>View</a></td></tr>",$buffer);
	$total = $total . "<tr><td colspan='4'><img src='output_image/$input_t-tm$tmcount.png'></td></tr>";
	$tmcount = $tmcount + 1;
      }
      if($state == 2)
      {
	list($pattern, $toal, $freq, $bgmatch, $score) = split('[:X]', $buffer);
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$buffer = str_replace("Motif Score = ","</td><td>",$buffer);
	$table = $table . "<tr><td id='MT$mtcount'>" . str_replace("\n","</td><td><a href='output/$input_t-mt$mtcount.txt' target='_blank'>View</a></td></tr>",$buffer);
	$table = $table . "<tr><td colspan='6'><img src='output_image/$input_t-mt$mtcount.png'></td></tr>";
	$mtcount = $mtcount + 1;
      }
      if(preg_match("/Total Motifs/", $buffer) !=0)   { $state = 1; }
      if(preg_match("/". $input_t ."/", $buffer) !=0) { $state = 2; }
    }
    $total = $total . "\"";
    $table = $table . "\"";
  }
  echo "[{ \"total\": " . $total . ", \"table\": " . $table . ", \"tm_count\": " . $tmcount . ", \"mt_count\": " . $mtcount . "}]";
?>
