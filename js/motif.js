function upload()
{
  $('#load').empty();
  $('#weblogo').empty();
  $('#motif-raw').empty();
  $('#status').empty();

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
	  $('#status').append("<li><b>File uploaded!</b></li>");
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
		  + "<b>Your Request is still <br/>in background processing ....<br/></b></center>");
  $.ajax({
    url:          "php/weblogic.php",
    type:         "POST",
    data:         "input=" + input,
    success:      function(msg)
    {
      $('#weblogo').append("<img src='output_image/" + msg + "'>");
      $('#status').append("<li><b>WebLogo generated!</b></li>");
    }
  });

  var background = $('#background').val();
  var freq = $('#freq').val();
  var match = $('#match').val();

  $.ajax({
    url:          "php/motif.php",
    type:         "POST",
    data:         "input=" + input + "&background=" + background + "&freq=" + freq + "&match=" + match ,
    success:      function(msg)
    {
      $('#motif-raw').append("<iframe src='output/" + msg + "' width='100%' height='400'/>");
      $('#status').append("<li><b>Motif Raw Results generated!</b></li>");
      $('#load').empty();
    }
  });
}
