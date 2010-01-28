<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head profile="http://gmpg.org/xfn/11">
  <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
  <meta content="noindex,nofollow" name="robots"/>
  <title>F-Motif</title>
  <style media="screen" type="text/css">
    @import url( css/4colourslover/style.css );
  </style>
  <link href="css/4colourslover/green/style.css" rel="stylesheet" type="text/css" title="blu" />
  <link href="css/jquery-ui.css"                 rel="stylesheet" type="text/css" media="screen" />
  <link href="css/motif.css"                     rel="stylesheet" type="text/css" />
  <!--[if lt IE 7]>
  <script type="text/javascript" src="css/themes/4colourslover/unitpngfix.js"></script>
  <link href="css/4colourslover/iestyle.css" rel="stylesheet" type="text/css" />
  <![endif]-->
  <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
  <script type="text/javascript" language="javascript" src="js/jquery-ui.min.js"></script>
  <script type="text/javascript" language="javascript" src="js/jquery.ajaxfileupload.js"></script>
  <script type="text/javascript" language="javascript" src="js/f-motif.js"></script>
</head>
<?
$id=$_GET['id'];
if($id != "")
{
  echo "<body onload='set_id(\"$id\");'>";
} else {
  echo "<body>";
}
?>
<div id="wrap">
  <div id="header">
    <div id="header_center">
      <h2>Motif Finder</h2>
      <h1>F-Motif</h1>
    </div>
    <div id="navigation">
      <ul>
	<li class="page_item page-item-2"><a href="index.php">Home</a></li>
	<li class="page_item page-item-2"><a href="#" onclick="show_annotation();" title="Annotation">Annotation</a></li>
	<li class="page_item page-item-2"><a href="#" onclick="show_download();" title="Download">Download</a></li>
      </ul>
    </div>
  </div>
  <div id="container">
    <div id="content">
      <div class="entry">
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">Input</a></li>
	    <li><a href="#tabs-2">Weblogo</a></li>
	    <li><a href="#tabs-3">Composite Motif List (CML)</a></li>
	    <li><a href="#tabs-4">Final List of Motifs (FLM)</a></li>
	  </ul>
	  <div id="tabs-1">
	    <table cellpadding="12" cellspacing="12">
	      <tr><td colspan=2><h2>Please fill following fields:</h2></td></tr>
	      <tr><td><b> 1. Supply a Foreground File: </b></td><td id="input_file"></td></tr>
	      <tr id="samples"><td>1.1 Select a sample file:</td><td>
		<select id="sample" name="sample">
		  <option></option>
		  <option value="nbt1146-S6.txt">Nature bio-tech data</option>
		  <option value="Seq_positive_ALL_CDK_S_13_phosphoELM_1208.txt">CDK - ALL</option>
		  <option value="Seq_positive_ALL_CK2_S_13_phosphoELM_1208.txt">CK2 - ALL</option>
		  <option value="Seq_positive_ALL_PKA_S_13_phosphoELM_1208.txt">PKA - ALL</option>
		  <option value="Seq_positive_ALL_PKC_S_13_phosphoELM_1208.txt">PKC - ALL</option>
		  <option value="Seq_positive_ALL_4-Kinases_S_13_phosphoELM_1208.txt">4 Kinases - ALL</option>
		  <option value="Seq_positive_Homo-sapiens_CDK_S_13_phosphoELM_1208.txt">CDK - Homo</option>
		  <option value="Seq_positive_Homo-sapiens_CK2_S_13_phosphoELM_1208.txt">CK2 - Homo</option>
		  <option value="Seq_positive_Homo-sapiens_PKA_S_13_phosphoELM_1208.txt">PKA - Homo</option>
		  <option value="Seq_positive_Homo-sapiens_PKC_S_13_phosphoELM_1208.txt">PKC - Homo</option>
		  <option value="Seq_positive_Homo-sapiens_4-Kinases_S_13_phosphoELM_1208.txt">4 Kinases - Homo</option>
		</select></td></tr>
	      <tr id="uploads"><td>1.2 Upload a text file:  </td><td><input type="file" id="fgdata_file" name="fgdata_file" value=""/></td></tr>
	      <tr><td><b> 2. Choose the Background Type:</b></td><td>
		<select id="background" name="background">
		  <option value="ELM_1208_backgroundset_S_13.txt" >All Species - S</option>
		  <option value="ELM_1208_Homo-sapiens_backgroundset_S_13.txt" >Human only - S</option>
		</select></td></tr>
	      <tr><td><b> 3. Choose an Encoding Method:</b></td><td>
		<select id="encode" name="encode">
		  <option value="PCM">PCM</option>
		  <option value="PWM">PWM</option>
		  <option value="BIN">BIN</option>
		</select></td></tr>
-             <tr><td><b> 4. Number of Iterations (50~100):</b></td>
		  <td><input id="freq" name="freq" value="50" onChange="check_freq();"/></td>
	      </tr>
	      <tr><td><b> 5. Choose <i>M</i> for Identifying Motifs:</b></td><td>
		<select id="match"   name="match">
		</select></td></tr>
	      <tr><td><b> 6. Choose <i>G</i> to define small cluster: </b></td><td>
		<select id="cluster" name="cluster">
		</select></td></tr>
		<input type="hidden" id="flat" name="flat" value="1">
              <tr><td><b> 7. Choose filter of repeat patterns: </b></td>
		<td><select id="repeat" name="repeat"/>
		  <option value="0">Disable</option>
		  <option value="1">Enable</option>
		</select></td></tr>
	      <tr><td><b> 8. Choose <i>T</i> to define potential conserved position:</b></td>
		<td><select id="threshold" name="threshold"/>
		</select></td></tr>
              <tr><td><b> 9. Significance (0~1): </b></td>
		  <td><input id="significance" name="significance" value="0.000001"/>
		  </td></tr>
	      <tr id="submit"><td><h2>Then click the submit button.</h2></td><td><input type="button" onclick="upload()" value="Submit"></td></tr>
	    </table>
	  </div>
	  <div id="tabs-2">
	    <div id="weblogo"></div>
	  </div>
	  <div id="tabs-3">
	    <table id="total-motif" border="1"></table>
	  </div>
	  <div id="tabs-4">
	    <table id="motif-table" border="1"></table>
	  </div>
	</div>
      </div>
    </div>
    <div id="sidebar">
      <h2>Process Status</h2>
      <ul id='status'></ul>
      <div id="load"></div>
    </div>
  </div>
</div>
<div id="annotation" title="Annotation" align="left">
<ol><li><b>FM</b>
  <p>This foreground data set is directly adopted from the supplementary material of a previous study (Schwartz and Gygi, 2005), which contains 298 serine-phosphorylated peptides considering the following four kinds of kinases: Ataxia Telangiectasia Mutated (ATM, 43 peptides), Casein II (184 peptides), Calcium/Calmodulin-dependent protein Kinase II (CaMK II, 41 peptides), and Mitogen-Activated Protein Kinase (MAPK, 30 peptides).</p></li>
  <li><b>FA<sub>PKA</sub></b>
  <p>The foreground data set considering all species with respect to PKA kinase substrates (322 serine-phosphorylated peptides)</p></li>
  <li><b>FA<sub>PKC</sub></b>
  <p>The foreground data set considering all species with respect to PKC kinase substrates (333 serine-phosphorylated peptides)</p></li>
  <li><b>FA<sub>CDK</sub></b>
  <p>The foreground data set considering all species with respect to CDK kinase substrates (213 serine-phosphorylated peptides)</p></li>
  <li><b>FA<sub>CK2</sub></b>
  <p>The foreground data set considering all species with respect to CK2 kinase substrates (247 serine-phosphorylated peptides)</p></li>
  <li><b>FA<sub>COMBINED</sub></b>
  <p>The foreground data set considering all species with respect to PKA, PKC, CDK, and CK2 kinase substrates (1115 serine-phosphorylated peptides)</p><li>
  <li><b>FH<sub>PKA</sub></b>
  <p>The foreground data set considering only human species with respect to PKA kinase substrates (187 serine-phosphorylated peptides)</p></li>
  <li><b>FH<sub>PKC</sub></b>
  <p>The foreground data set considering only human species with respect to PKC kinase substrates (210 serine-phosphorylated peptides)</p></li>
  <li><b>FH<sub>CDK</sub></b>
  <p>The foreground data set considering only human species with respect to CDK kinase substrates (155 serine-phosphorylated peptides)</p></li>
  <li><b>FH<sub>CK2</sub></b>
  <p>The foreground data set considering only human species with respect to CK2 kinase substrates (177 serine-phosphorylated peptides)</p></li>
  <li><b>FH<sub>COMBINED</sub></b>
  <p>The foreground data set considering only human species with respect to PKA, PKC, CDK, and CK2 kinase substrates (729 serine-phosphorylated peptides)</p></li>
  <li><b>BH</b>
  <p>Human background data set (249175 serine-phosphorylated and non-phosphorylated peptides)</p></li></ol>
</div>
<div id="download" title="Download" align="left">
  <h3>Download Data Set:</h3>
  <p> You can download the foreground and background data sets from this <a href='input/dataset.zip' target='_blank'><font color='blue'>"Data Set Archive"</font></a></p>
</div>
<div id="footer">
  <div id="footer_inner">
    <p>4ColoursLover Theme by <a href="http://www.potamocheri.eu/blog" target="_blank">Edo Grandinetti</a>. Power by <a href="http://trac.nchc.org.tw/grid" target="_blank">Grid Technology Division, <a href="http://www.nchc.org.tw" target="_blank">NCHC</a>, <a href="http://www.narl.org.tw" target="_blank">NARL</a>, Taiwan.</p>
  </div>    
</div>
</body></html>
