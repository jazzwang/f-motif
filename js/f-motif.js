var input;

$(function() {
  // 使用 jQuery UI 的 Tab 元件
  $("#tabs").tabs();
  // 使用 jQuery UI 的 Dialog 元件
  $("#annotation").dialog({
    autoOpen: false, bgiframe: true, modal: true, 
    maxHeight: 400, maxWidth: 1024,
    height: 400, width: 1024,
    buttons: { Ok: function() {	$(this).dialog('close'); } }
  });
  $("#download").dialog({
    autoOpen: false, bgiframe: true, modal: true, 
    buttons: { Ok: function() {	$(this).dialog('close'); } }
  });
  // 只有載入時產生 match 下拉式選單的値(M)
  for ( i=20; i<=60; i++)
  {
    if(i==60) {
      $("#match").append("<option value='"+i+"' selected>"+i+"</option>");
    } else {
      $("#match").append("<option value='"+i+"'>"+i+"</option>");
    }
  }
  // 設定 onChange Event 處理函式
  $('#fgdata_file').change(function () {
    $('#sample').val("");
  });
  $('#sample').change(function () {
    $('#fgdata_file').val("");
  });
  $('#match').change(function () {
    value = $('#match').val();
    $('#cluster').empty();
    $('#threshold').empty();
    for ( j=30; j<=value; j++)
    {
      if(j==30) {
	$("#cluster").append("<option value='"+j+"' selected>"+j+"</option>");
	$("#threshold").append("<option value='"+j+"' selected>"+j+"</option>");
      } else {
	$("#cluster").append("<option value='"+j+"'>"+j+"</option>");
	$("#threshold").append("<option value='"+j+"'>"+j+"</option>");
      }
    }
  });
  // 只有載入時產生 cluster,threshold 下拉式選單的初始値 (G,T)
  $('#match').change();
  // 其他通用初始化動作
  init();
});

function init()
{
  $('#load').empty();
  $('#weblogo').empty();
  $('#total-motif').empty();
  $('#motif-table').empty();
  $('#status').empty();

  $('#motif-table').append("<tr><th width='30'>#</th><th>Motif Pattern</th><th>Match / Total</th><th width='50'>Hit Freq.</th><th>Background Match</th><th>Motif score</th><th>Matched Sequence</th></tr>");
  $('#total-motif').append("<tr><th width='30'>#</th><th>Motif Pattern</th><th>Forground Match</th><th width='50'>Hit Freq.</th><th>Matched Sequence</th></tr>");
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
	    $('#status').append("<li><b>Forground:</b> <u><a href='input/" + data.msg + "' target='_blank'>Download</a></u></li>");
	    $('#status').append("<li><b>Background:</b> <u><a href='PTMDATA/" + $('#background').val() + "' target='_blank'>Download</a></u></li>");
	    process(data.msg);
	  }
	}
      },
      error: function (data, status, e) { alert(e); }
    });
  } else {
    $.ajax({
      url:	"php/copy-sample.php",
      type:     "POST",
      data:	"input=" + $('#sample').val(),
      success:	function(msg)
      {
	$('#status').append("<li><b>Forground:</b> <u><a href='input/" + $('#sample').val() + "' target='_blank'>Download</a></u></li>");
	$('#status').append("<li><b>Background:</b> <u><a href='PTMDATA/" + $('#background').val() + "' target='_blank'>Download</a></u></li>");
	process(msg);
      }
    });
  }

  return false;
}

function process(input)
{
  $('#load').append("<center><img src='image/loading.gif'><br/><br/>"
		  + "<b><font color='#ff0000'>Your Request is still processing<br/>in background ....<br/></font></b></center>");

  var background    = $('#background').val();
  var encode	    = $('#encode').val();
  var freq	    = $('#freq').val();
  var match	    = $('#match').val();
  var cluster	    = $('#cluster').val();
  var flat	    = $('#flat').val();
  var threshold	    = $('#threshold').val();
  var repeat	    = $('#repeat').val();
  var significance  = $('#significance').val();

  $.ajax({
    url:          "php/weblogic.php",
    type:         "POST",
    data:         "input=" + input,
    success:      function(msg)
    {
      $('#weblogo').append("<p>[[ <a href='output_image/" + msg + "' target='_blank'>Download weblogo result</a> ]] - [[ <a href='input/" + input + "' target='_blank'>Source Input</a> ]]</p>");
      $('#weblogo').append("<img src='output_image/" + msg + "'>");
      $('#status').append("<li><b>WebLogo generated!</b></li>");
      $("#tabs").tabs("select",1);
    }
  });

  if($('#encode').val() == "BIN")
  {
    $('#status').append("<li><b><font color='red'>Warning: it will take more than 1 hours if you choose 'BIN' encoding method! Please check the result using following URL, thanks.</font></b></li>");
  }
  $('#status').append("<li><b>Use <u><a href='?id="+ input.replace('.txt','') +"'>this URL</a></u> to get history results.</b></li>");

  $.ajax({
    url:          "php/motif.php",
    type:         "POST",
    data:         "input=" + input + "&background=" + background + "&encode=" + encode + "&freq=" + freq + "&match=" + match + "&cluster=" + cluster + "&flat=" + flat + "&repeat=" + repeat + "&threshold=" + threshold + "&significance=" + significance,
    success:      function(msg)
    {
      //$('#status').append("<li><b><a href='output/" + msg + "' target='_blank'>1st stage analysis</a> finished!</b></li>");
      analysis(msg);
    }
  });

  if($('#encode').val() == "BIN")
  {
    $('#status').append("<li><b><font color='#0000FF'>Now, it's safe to close your browser!</font></b></li>");
    $('#load').empty();
  }
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
      $('#status').append("<li><b>CML generated!</b></li>");
      $('#status').append("<li><b>FLM generated!</b></li>");
      $("#tabs").tabs("select",2);
      $('#load').empty();
    }
  });
}

function show_annotation()
{
  $("#annotation").dialog("open");
}

function show_download()
{
  $("#download").dialog("open");
}

function IsNumeric(sText)
{ 
  //判斷是否為數值
  var ValidChars = "0123456789.";
  var IsNumber=true;
  var Char;
  
  for (i = 0; i < sText.length && IsNumber == true; i++) 
  { 
    Char = sText.charAt(i); 
    if (ValidChars.indexOf(Char) == -1) 
    {
      IsNumber = false;
    }
  }
  return IsNumber;   
}

function check_freq() 
{
  value = $('#freq').val();
  if(IsNumeric(value))
  {
    if( value < 50 || value > 100 )
    {
      $('#freq').val("50");
      alert("Please enter an number between 50 to 100, thanks!");
    }
  } else {
    $('#freq').val("50");
    alert("No text. Please enter an number between 50 to 100, thanks!");
  }
}

function set_id(id)
{
  $('#load').append("<center><img src='image/loading.gif'><br/><br/>"
                  + "<b><font color='#ff0000'>Your Request will be processed<br/>within one minute ....<br/></font></b></center>");

  $.ajax({
    url:          "php/history-weblogo.php",
    type:         "POST",
    data:         "id=" + id,
    success:      function(msg)
    {
      $('#weblogo').append("<p>[[ <a href='output_image/" + id + ".png' target='_blank'>Download weblogo result</a> ]] - [[ <a href='input/" + id + ".txt' target='_blank'>Source Input</a> ]]</p>");
      $('#weblogo').append("<img src='output_image/" + id + ".png'>");
      $('#status').append("<li><b>WebLogo generated!</b></li>");
    }
  });

  $.ajax({
    url:          "php/history-motif.php",
    type:	  "POST",
    data:	  "id=" + id,
    success:	  function(msg)
    {
      if(msg == "NOT_YET")
      {
	$('#status').append("<li><b><font color='#0000FF'>Result is not yet ready. Please come back again later. Thanks!!</font></b></li>");
      } else {
	var json = eval(msg);
	$.each(json, function(i, item){
	  $('#total-motif').append(item.total);
	  $('#total-motif tr:even').addClass('even');
	  $('#total-motif tr:odd').addClass('odd');
	  $('#motif-table').append(item.table);
	  $('#motif-table tr:even').addClass('even');
	  $('#motif-table tr:odd').addClass('odd');
	});
	$('#status').append("<li><b>CML generated!</b></li>");
	$('#status').append("<li><b>FLM generated!</b></li>");
      }
      $('#load').empty();
    }
  });
}
