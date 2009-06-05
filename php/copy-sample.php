<?php
  $date		= chop(`date +"%y%m%d%H%M%S"`);
  $input	= $_POST["input"];
  $file_name	= "$date.txt";
  $output_name	= "$date-mt.txt";
  $path		= str_replace("/php","",getcwd());
  $file_path	= $path . "/input/";
  $output_path	= $path . "/output/";
  if(!copy("$file_path/$input","$file_path/$file_name"))
  {
    echo "failed to copy $file_path/$input to $file_path/$file_name ...\n";
    return 1;
  }
  if(!copy("$file_path/$input","$output_path/$output_name"))
  {
    echo "failed to copy $file_path/$input to $output_path/$output_name ...\n";
    return 1;
  }
  echo $file_name;
?>
