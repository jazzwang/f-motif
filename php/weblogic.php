<?
  $input  = $_SERVER['DOCUMENT_ROOT'] . "/f-motif/input/" . $_POST["input"];
  $output = $_SERVER['DOCUMENT_ROOT'] . "/f-motif/output_image/" . str_replace(".txt","",$_POST["input"]);
  $cmd	  = $_SERVER['DOCUMENT_ROOT'] . "/f-motif/weblogo/seqlogo -w 10 -abcMnY -F PNG -f $input -o $output";
  system("$cmd",$ret);
  if($ret == 0)
  {
    echo str_replace(".txt",".png",$_POST["input"]);
  }
?>
