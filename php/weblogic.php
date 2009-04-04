<?
  $path  = str_replace("/php","",getcwd());
  $input  = $path . "/input/" . $_POST["input"];
  $output = $path . "/output_image/" . str_replace(".txt","",$_POST["input"]);
  $cmd	  = $path . "/weblogo/seqlogo -w 15 -abcMnY -F PNG -f $input -o $output";
  system("$cmd",$ret);
  if($ret == 0)
  {
    echo str_replace(".txt",".png",$_POST["input"]);
  }
?>
