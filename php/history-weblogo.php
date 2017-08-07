<?php
  /*
   * 2009-08-19 modified from motif-table.php
   */

  $path	    = str_replace("/php","",getcwd());
  $file	    = $path . "/output_image/" . $_POST["id"] . ".png";
  if(file_exists($file))
  {
    $input    = fopen($file ,"r") || die("ERROR");
    echo "OK";
  } else {
    echo "ERROR";
  }
?>
