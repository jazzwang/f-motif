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
	<li class="page_item page-item-2"><a href="#" onclick="show_example();" title="Example">Output Example</a></li>
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
		  <option value="nbt1146-S6.txt">Nature bio-tech data (four kinase substrates)</option>
		  <option value="nbt1146-S5.txt">Nature bio-tech data (synthetic peptides)</option>
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
		  <option value="pr800599n_11_S_N_hiP.txt">Mouse mass spectrometry data</option>
		</select></td></tr>
	      <tr id="uploads"><td>1.2 Upload a text file:  </td><td><input type="file" id="fgdata_file" name="fgdata_file" value=""/></td></tr>
	      <tr><td><b> 2. Choose the Background Type:</b></td><td id="back_file">
		<select id="background" name="background">
		  <option value="ELM_1208_backgroundset_S_13.txt">All Species - S</option>
		  <option value="ELM_1208_Homo-sapiens_backgroundset_S_13.txt">Human only - S</option>
		  <option value="ELM_1208_Homo-sapiens_backgroundset_S_13_plus_S5.txt">Human only for synthetic data - S</option>
		  <option value="ipi.MOUSE.v3.71.fasta_S_N_s.txt">Mouse</option>
		</select></td></tr>
	      <tr><td><b> 3. Choose an Encoding Method:</b></td><td id="encode_s">
		<select id="encode" name="encode">
		  <option value="PCM">PCM</option>
		  <option value="PWM">PWM</option>
		  <option value="BIN">BIN</option>
		</select></td></tr>
-             <tr><td><b> 4. Number of Iterations (1~100):</b></td>
		  <td id="freq_s"><input id="freq" name="freq" value="50"/></td>
	      </tr>
	      <tr><td><b> 5. Choose <i>M</i> for Identifying Motifs:</b></td><td id="match_s">
		<select id="match"   name="match">
		</select></td></tr>
	      <tr><td><b> 6. Choose <i>G</i> to define small cluster: </b></td><td id="cluster_s">
		<select id="cluster" name="cluster">
		</select></td></tr>
		<input type="hidden" id="flat" name="flat" value="1">
              <tr><td><b> 7. Choose filter of repeat patterns: </b></td>
		<td id="repeat_s"><select id="repeat" name="repeat"/>
		  <option value="0">Disable</option>
		  <option value="1" selected>Enable</option>
		</select></td></tr>
	      <tr><td><b> 8. Choose <i>T</i> to define potential conserved position:</b></td>
		<td id="threshold_s"><select id="threshold" name="threshold"/>
		</select></td></tr>
              <tr><td><b> 9. Significance [0~1): </b></td>
		  <td id="significance_s"><input id="significance" name="significance" value="0.000001"/>
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
<ol><li><b>Nature bio-tech data (four kinase substrates)</b>
  <p>This foreground data set is adopted from the supplementary material of a previous study (Schwartz and Gygi, 2005), which contains 298 serine-phosphorylated peptides considering the following four kinds of kinases: Ataxia Telangiectasia Mutated (ATM, 43 peptides), Casein II (184 peptides), Calcium/Calmodulin-dependent protein Kinase II (CaMK II, 41 peptides), and Mitogen-Activated Protein Kinase (MAPK, 30 peptides).</p></li>
  <li><b>Nature bio-tech data (synthetic peptides)</b>
  <p>This foreground data set is adopted from the supplementary material of a previous study (Schwartz and Gygi, 2005), which contains 9774 synthetic peptides with five specially designed synthetic motifs "XXXDXXSQXNXXX" (128 peptides), "XXXXRXSXXLXXX" (199 peptides), "XXXTVXSXEXXXX" (137 peptides), "XXXXRXSXXPXXX" (192 peptides), and "XXXXXKSXXXIXX" (160 peptides).</p></li>
  <li><b>PKA - ALL</b>
  <p>The foreground data set considering all species with respect to PKA kinase substrates (306 serine-phosphorylated peptides)</p></li>
  <li><b>PKC - ALL</b>
  <p>The foreground data set considering all species with respect to PKC kinase substrates (297 serine-phosphorylated peptides)</p></li>
  <li><b>CDK - ALL</b>
  <p>The foreground data set considering all species with respect to CDK kinase substrates (209 serine-phosphorylated peptides)</p></li>
  <li><b>CK2 - ALL</b>
  <p>The foreground data set considering all species with respect to CK2 kinase substrates (241 serine-phosphorylated peptides)</p></li>
  <li><b>4 kinases - ALL</b>
  <p>The foreground data set considering all species with respect to PKA, PKC, CDK, and CK2 kinase substrates (998 serine-phosphorylated peptides)</p></li>
  <li><b>PKA - Homo</b>
  <p>The foreground data set considering only human species with respect to PKA kinase substrates (187 serine-phosphorylated peptides)</p></li>
  <li><b>PKC - Homo</b>
  <p>The foreground data set considering only human species with respect to PKC kinase substrates (209 serine-phosphorylated peptides)</p></li>
  <li><b>CDK - Homo</b>
  <p>The foreground data set considering only human species with respect to CDK kinase substrates (155 serine-phosphorylated peptides)</p></li>
  <li><b>CK2 - Homo</b>
  <p>The foreground data set considering only human species with respect to CK2 kinase substrates (177 serine-phosphorylated peptides)</p></li>
  <li><b>4 kinases - Homo</b>
  <p>The foreground data set considering only human species with respect to PKA, PKC, CDK, and CK2 kinase substrates (697 serine-phosphorylated peptides)</p></li>
  <li><b>Mouse mass spectrometry data</b>
  <p>This foreground data set is adopted from the supplementary material of a previous study (Zanivan, et al., 2008), which is the mouse mass spectrometry data with 4189 mouse serine-phosphorylated sites considering phosphorylation probabilities &gt;= 0.8 under multiple TiO2 conditions.</p></li>
</ol>
</div>
<div id="download" title="Download" align="left">
  <h3>Download Data Set:</h3>
  <p> You can download the <a href='input/foregroundset.zip'>foreground</a> and <a href='input/backgroundset.zip'>background</a> data sets from this <a href='input/dataset.zip' target='_blank'><font color='blue'>"Data Set Archive"</font></a></p>
</div>
<div id="example" title="Example" align="left">
  <h3>Output Example:</h3>
  <table><tr><td>Forground</td><td>Background</td><td>Results</td></tr>
  <tr><td>Mouse mass spectrometry data</td><td>Mouse</td><td><a href='/?id=sample-mouse'>Mouse Output Example</a></td></tr>
  </table>
</div>
<div id="footer">
  <div id="footer_inner">
    <p><a target='_blank' href='http://wordpress.org/extend/themes/4colourslover'>4ColoursLover</a> Theme by <a href="http://www.potamocheri.eu/blog" target="_blank">Edo Grandinetti</a>. Power by <a href="http://trac.nchc.org.tw/grid" target="_blank">Grid Technology Division, <a href="http://www.nchc.org.tw" target="_blank">NCHC</a>, <a href="http://www.narl.org.tw" target="_blank">NARL</a>, Taiwan.</p>
  </div>    
</div>
</body></html>
