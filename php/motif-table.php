<?
  $input = fopen($_SERVER['DOCUMENT_ROOT'] . "/f-motif/output/" . $_POST["input"],"r");
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
	$total = $total . "<tr><td>" . str_replace("\n","</td></tr>",$buffer);
      }
      if($state == 2)
      {
	$buffer = str_replace("::","</td><td>",$buffer);
	$buffer = str_replace(":","</td><td>",$buffer);
	$buffer = str_replace("X","</td><td>",$buffer);
	$buffer = str_replace("Motif Score = ","</td><td>",$buffer);
	$table = $table . "<tr><td>" . str_replace("\n","</td></tr>",$buffer);
      }
      if(preg_match("/Total Motifs/", $buffer) !=0)	    { $state = 1; }
      if(preg_match("/".$_POST['input']."/", $buffer) !=0)  { $state = 2; }
    }
    $total = $total . "\"";
    $table = $table . "\"";
  }
  echo "[{ \"total\": " . $total . ", \"table\": " . $table . "}]";
?>
