$(function() {
  $("#tabs").tabs();
  init();
});

function init()
{
  $('#load').empty();
  $('#weblogo').empty();
  $('#total-motif').empty();
  $('#motif-table').empty();
  $('#motif-raw').empty();
  $('#status').empty();

  $('#motif-table').append("<tr><th>Motif Pattern</th><th>Match / Total</th><th>hit freq.</th><th>Background Match</th><th>Motif score</th><th>Matched Sequence</th></tr>");
  $('#total-motif').append("<tr><th>Motif Pattern</th><th>Motif score</th><th>Forground Match</th><th>Matched Sequence</th></tr>");
}

function upload()
{
  init();

  if($('#sample').val() == "" && $('#fgdata_file').val() == "") { alert("please select a forground file or choose a sample file!!"); return; }

  if($('#fgdata_file').val() != "") {
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
  } else {
    process($('#sample').val());
  }

  return false;
}

function process(input)
{
  $('#load').append("<center><img src='image/loading.gif'><br/><br/>"
		  + "<b><font color='#ff0000'>Your Request is still processing<br/>in background ....<br/></font></b></center>");
  $.ajax({
    url:          "php/weblogic.php",
    type:         "POST",
    data:         "input=" + input,
    success:      function(msg)
    {
      $('#weblogo').append("<p>[[ <a href='output_image/" + msg + "' target='_blank'>Download weblogo result</a> ]]</p>");
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
      $('#motif-raw').append("<p>[[ <a href='output/" + msg + "' target='_blank'>Download F-Motif raw output</a> ]]</p>");
      $('#motif-raw').append("<iframe src='output/" + msg + "' width='100%' height='300'/>");
      $('#status').append("<li><b>Motif Raw Results generated!</b></li>");
      analysis(msg);
    }
  });
}

function analysis(input)
{
  $.ajax({
    url:          "php/motif-table.php",
    type:	  "POST",
    data:	  "input=" + input,
    success:	  function(msg)
    {
      var json = eval(msg);
      $.each(json, function(i, item){
	$('#total-motif').append(item.total);
	$('#total-motif tr:even').addClass('even');
	$('#total-motif tr:odd').addClass('odd');
	$('#motif-table').append(item.table);
	$('#motif-table tr:even').addClass('even');
	$('#motif-table tr:odd').addClass('odd');
      });
      $('#status').append("<li><b>Total Motif generated!</b></li>");
      $('#status').append("<li><b>Motif Table generated!</b></li>");
      $('#load').empty();
    }
  });
}
