<?php
  $input = $_POST["input"];
  $file_name = chop(`date +"%y%m%d%H%M%S.txt"`);
  $path  = str_replace("/php","",getcwd());
  $file_path = $path . "/input/";
  if(!copy("$file_path/$input","$file_path/$file_name"))
  {
    echo "failed to copy $file_path/$input to $file_path/$file_name ...\n";
    return 1;
  }
  echo $file_name;
?>
