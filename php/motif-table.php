<?
  $path  = str_replace("/php","",getcwd());
  $input = fopen($path . "/output/" . $_POST["input"],"r");
  $state = 0;
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
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$total = $total . "<tr><td id='TM$count'>" . str_replace("\n","</td><td><a onclick='total_motif($count);' href='#'>Generate</a></td></tr>",$buffer);
	$count = $count + 1;
      }
      if($state == 2)
      {
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$buffer = str_replace("Motif Score = ","</td><td>",$buffer);
	$table = $table . "<tr><td id='MT$count'>" . str_replace("\n","</td><td><a onclick='motif_table($count);' href='#'>Generate</a></td></tr>",$buffer);
	$count = $count + 1;
      }
      if(preg_match("/Total Motifs/", $buffer) !=0)	    { $state = 1; $count=0; }
      if(preg_match("/".$_POST['input']."/", $buffer) !=0)  { $state = 2; $count=0; }
    }
    $total = $total . "\"";
    $table = $table . "\"";
  }
  echo "[{ \"total\": " . $total . ", \"table\": " . $table . "}]";
?>
