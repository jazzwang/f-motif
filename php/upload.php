<?php
  $error = "";
  $msg = "";
  $fileElementName = 'fgdata_file';
  if(!empty($_FILES[$fileElementName]['error']))
  {
    switch($_FILES[$fileElementName]['error'])
    {
      case '1':
	$error = 'The uploaded file exceeds the upload_max_filesize directive in php.ini';
	break;
      case '2':
	$error = 'The uploaded file exceeds the MAX_FILE_SIZE directive that was specified in the HTML form';
	break;
      case '3':
	$error = 'The uploaded file was only partially uploaded';
	break;
      case '4':
	$error = 'No file was uploaded.';
	break;
      case '6':
	$error = 'Missing a temporary folder';
	break;
      case '7':
	$error = 'Failed to write file to disk';
	break;
      case '8':
	$error = 'File upload stopped by extension';
	break;
      case '999':
	default:
	$error = 'No error code avaiable';
    }
  } elseif (empty($_FILES[$fileElementName]['tmp_name']) || $_FILES[$fileElementName]['tmp_name'] == 'none') {
    $error = 'No file was uploaded..';
  } else {
    $file_temp  = $_FILES[$fileElementName]['tmp_name'];
    $date       = chop(`date +"%y%m%d%H%M%S"`);
    $file_name1 = "$date.txt";
    $file_name2 = "$date-mt.txt";
    $path  = str_replace("/php","",getcwd());
    $file_path1 = $path . "/input/";
    $file_path2 = $path . "/output/";
    if(!file_exists($file_path."/".$file_name1)) {
      //complete upload
      $filestatus2 = copy($file_temp,$file_path2."/".$file_name2);
      $filestatus1 = move_uploaded_file($file_temp,$file_path1."/".$file_name1);
      if(!$filestatus1 || !$filestatus2) {
	$error .= "Upload failed. Please try again.";
      } else {
	$msg .= $file_name1;
      }
    } else {
      $error .= "File already exists on server.";
    }
    
    //for security reason, we force to remove all uploaded file
    @unlink($_FILES[$fileElementName]);
  }		

  echo "{";
  echo "error: '" . $error . "',\n";
  echo "msg: '" . $msg . "'\n";
  echo "}";
?>
