<?
  /*
   * 2009-08-19 modified from motif-table.php
   */

  $path	    = str_replace("/php","",getcwd());
  $input    = fopen($path . "/output/" . $_POST["id"] . ".png","r") || die("ERROR");
  if($input)
  {
    echo "OK";
  }
?>
