<?
  $output = array();
  $return_var = 0;
  $input = substr($_POST["input"],0,12);
  $cmd	  = $_SERVER['DOCUMENT_ROOT'] . "/motif_finder/motif.sh " . $input;
  system($cmd, $ret);
  if($ret == 0)
  {
    //echo "$cmd\n$output\n$ret";
    echo $_POST["input"];
  }
?>
