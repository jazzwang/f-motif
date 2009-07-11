<?
  $input      = str_replace(".txt","",$_POST["input"]);
  $background = $_POST["background"];
  $encode     = $_POST["encode"];
  $freq	      = $_POST["freq"];
  $match      = $_POST["match"];
  $cluster    = $_POST["cluster"];
  $flat	      = $_POST["flat"];
  $threshold  = $_POST["threshold"];
  $path  = str_replace("/php","",getcwd());
  chdir($path);
  $cmd	  = $path . "/motif.sh " . $input . " " . $background . " " . $encode . " " . $freq . " " . $match . " " . $cluster . " " . $flat . " " . $threshold;
  system($cmd, $ret);
  if($ret == 0)
  {
    //echo "$cmd\n$output\n$ret";
    echo $_POST["input"];
  } else {
    echo "$cmd\n$output\n$ret";
  }
?>
