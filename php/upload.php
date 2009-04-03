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
    $file_temp = $_FILES[$fileElementName]['tmp_name'];
    $file_name = chop(`date +"%y%m%d%H%M%S.txt"`);
    $file_path = $_SERVER['DOCUMENT_ROOT']."/f-motif/input/";
    if(!file_exists($file_path."/".$file_name)) {
      //complete upload
      $filestatus = move_uploaded_file($file_temp,$file_path."/".$file_name);
      if(!$filestatus) {
	$error .= "Upload failed. Please try again.";
      } else {
	$msg .= $file_name;
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
