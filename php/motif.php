<?
  $output = array();
  $return_var = 0;
  $input = substr($_POST["input"],0,12);
  $background = $_POST["background"];
  $freq = $_POST["freq"];
  $match = $_POST["match"];
  $cmd	  = $_SERVER['DOCUMENT_ROOT'] . "/f-motif/motif.sh " . $input . " " . $background . " " . $freq . " " . $match;
  system($cmd, $ret);
  if($ret == 0)
  {
    //echo "$cmd\n$output\n$ret";
    echo $_POST["input"];
  }
?>
