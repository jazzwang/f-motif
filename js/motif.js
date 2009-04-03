function upload()
{
  $('#load').empty();
  $('#weblogic').empty();
  $('#motif').empty();

  $.ajaxFileUpload({
    url:'php/upload.php',
    secureuri:false,
    fileElementId:'fgdata_file',
    dataType: 'json',
    success: function (data, status)
    {
      if(typeof(data.error) != 'undefined')
      {
	if(data.error != '') { 
	  alert(data.error); 
	} else {
	  process(data.msg);
	}
      }
    },
    error: function (data, status, e) { alert(e); }
  });

  return false;
}

function process(input)
{
  $('#load').append("<center><img src='image/loading.gif'><br/><br/>"
		  + "Your Request is still in background processing ....<br/></center>");
  $.ajax({
    url:          "php/weblogic.php",
    type:         "POST",
    data:         "input=" + input,
    success:      function(msg)
    {
      $('#weblogic').append("<img src='output_image/" + msg + "'>");
    }
  });

  $.ajax({
    url:          "php/motif.php",
    type:         "POST",
    data:         "input=" + input,
    success:      function(msg)
    {
      $('#motif').append("<iframe src='output/" + msg + "' width='100%' height='400'/>");
      $('#motif').css('background-color','white');
      $('#load').remove();
    }
  });
}
