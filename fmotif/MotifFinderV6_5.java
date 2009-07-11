package fmotif;
// Fig. 25.25: DisplayAuthors.java
// Displaying the contents of the authors table.

// Fig. 25.25: DisplayAuthors.java
// Displaying the contents of the authors table.
//import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.util.regex.*;


public class MotifFinderV6_5 {
    querydata Data = new querydata();
    boolean flag = false;
    boolean Absoluteflag = false;
    Vector SequenceSet;
    Vector<String> SequenceRule;
    Vector<Integer> MatchRuleNum;
    Vector BackgroundMatchRuleNum;
    Vector backgroundSet;
    Vector UsedSequenceSet;
    Vector UsedbackgroundSet;
    
    static private int MinMatchNum;
    static private int MinMatchNum2;
    static private int MinClusterNum;
    static private int Frequecny;
    static private double posT;
    static private double Mvalue;
    static private String Background;
    static private String EncodingMethod;
    static String PostiveFinName = "";
    
    String centerReg;
    char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
            'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y','V','X'};
    int  change[] = new int[128];
    int Encotype = 0;
    public MotifFinderV6_5() {

    	for (int i = 0; i < aaMap.length; i++) {
            change[aaMap[i]] = i;
        }

    }

    public MotifFinderV6_5(querydata data) {
        Data = data;
        for (int i = 0; i < aaMap.length; i++) {
            change[aaMap[i]] = i;
        }

    }

    public static void main(String args[]) {
       
       querydata data = new querydata();
       data.shift = "";
       MotifFinderV6_5 SeqDatapool = new MotifFinderV6_5(data);
        if(args.length > 0)
        {

            SeqDatapool.PostiveFinName = args[0];
            //SeqDatapool.PostiveFinName = "Seq_positive_Homo-sapiens_PKA_S_13_phosphoELM_1208.txt";
	    SeqDatapool.Background = args[1];
            //SeqDatapool.Background = "Homo";
	    SeqDatapool.EncodingMethod = args[2];
            //SeqDatapool.EncodingMethod = "PFM";

            if (SeqDatapool.EncodingMethod.equals("PFM")){
		SeqDatapool.Encotype = 0;
	    } else if (SeqDatapool.EncodingMethod.equals("PWM")){
		SeqDatapool.Encotype = 1;
	    } else if (SeqDatapool.EncodingMethod.equals("BIN")){
		SeqDatapool.Encotype = 2;
	    } else {
		SeqDatapool.Encotype = 0;
	    }

            SeqDatapool.Frequecny = Integer.parseInt(args[3] ,10);
            //SeqDatapool.Frequecny = 50;
            SeqDatapool.MinMatchNum  = Integer.parseInt(args[4] ,10);
            //SeqDatapool.MinMatchNum =20;
            SeqDatapool.MinMatchNum2 = Integer.parseInt(args[4] ,10);
            //SeqDatapool.MinMatchNum2 =20;
            SeqDatapool.MinClusterNum = Integer.parseInt(args[5] ,10);
            //SeqDatapool.MinClusterNum = 15;
            String Flaftemp = "";
            Flaftemp = args[6];
            //Flaftemp = "1";
	    if (Flaftemp.equals("1")){
		SeqDatapool.Absoluteflag = Boolean.TRUE;
	    } else {
		SeqDatapool.Absoluteflag = Boolean.FALSE;
	    }

            SeqDatapool.posT = Double.parseDouble(args[7]);
	    //SeqDatapool.posT = 15;
            
            SeqDatapool.Mvalue = 1.5*SeqDatapool.MinMatchNum;
         } else {
              System.out.print("java MotifFinderV5 INPUT_FILENAME [All|Homo] [[PFM|PWM|BIN]] FREQUENCY MATCH_NUMBER MINGROUPSIZE [Absoluteflag[1|0]] Threshold_T\n");
              return;
         }   
		       
		SeqDatapool.start(SeqDatapool.Frequecny);
         
    }



    public int[] start(int RepeatTimes) {
    	long start = System.currentTimeMillis();
    	int nonrepeatmotifnumber[] = new int[2];
    	try {

            String kinases = Data.kinease;
            String code = Data.code;code = "S";
            String form = Data.form;
            int windows_size = Data.windows_size;
            int posstart = (Data.windows_size+1)/2 -Data.windows_size;
            String StringCML = "";
            String StringPML = "";
            String StringLog = "";
           {
        	   String species =Data.species;
               if (Data.species.equals("ALL")){
               	species = "ALL";
               }
                //String PostiveFinName =  "Seq_positive_" + species + "_" + kinases + "_" + code + "_" +windows_size +"_"+Data.DATABASE_TABLE;
                //String PostiveFinName =  "nbt1146-S6";

                 String FullPostiveFinName = "./input/"+PostiveFinName;
                //IPI_Human_backgroundset_S_13.txt//"./Dataset/nbt1146-S6.txt"
               //BufferedReader PostiveFin = new BufferedReader(new FileReader("./Dataset/nbt1146-S6.txt"));
        	   //BufferedReader PostiveFin = new BufferedReader(new FileReader("./PTMDATA/Seq_positive_PKA_S_13.txt"));

        	   //BufferedReader PostiveFin = new BufferedReader(new FileReader("./Dataset/Seq_positive_4kinases_S_13.txt"));
              //  BufferedReader backgroundFin = new BufferedReader(new FileReader("./Dataset/IPI_Human_backgroundset_S_13.txt"));
               //"./Dataset/Seq_positive_" + species + "_" + Data.kinease + "_" +QueryCodeName + "_" + windows_size +"_"+Data.DATABASE_TABLE+".txt"

                BufferedReader PostiveFin = new BufferedReader(new FileReader(FullPostiveFinName));
               
	       BufferedReader backgroundFin;
	       if ( Background.equals("All") )
	       {
		  		backgroundFin = new BufferedReader(new
                       FileReader("./PTMDATA/ELM_1208_backgroundset_S_13.txt"));
           } else {
		  		backgroundFin = new BufferedReader(new
                        FileReader("./PTMDATA/ELM_1208_Homo sapiens_backgroundset_S_13.txt"));
	      	}
                 PrintWriter GroupNumOut = new PrintWriter(new BufferedWriter(
                		new FileWriter("./output/" +PostiveFinName+"_MotifLog.csv")));
                 PrintWriter FinalMotifList = new PrintWriter(new BufferedWriter(
                		new FileWriter("./output/" +PostiveFinName+"_Motif.csv")));
                 
              

                int kfold = Data.kfold;

                SequenceSet = new Vector();

                backgroundSet = new Vector();

                String readtemp;
                int count = 0;
                int centerAA[] = new int[aaMap.length];
                int half = (windows_size - 1)/2;
                while (PostiveFin.ready()) {
                	readtemp = PostiveFin.readLine();
                	if (readtemp.length() ==  windows_size){
                		centerAA[change[readtemp.charAt(half)]]++;
                		SequenceSet.addElement(readtemp);

                	}

                }
                PostiveFin.close();
                centerReg = "";
                for(int i = 0; i <aaMap.length;i++){
                	if (centerAA[i]!= 0){
                		centerReg += aaMap[i];
                	}
                }
                if (centerReg.length()> 1){
                	centerReg = "["+centerReg+"]";
                }
                while (backgroundFin.ready()) {
                    backgroundSet.addElement(backgroundFin.readLine());
                }
                backgroundFin.close();
                Encoding pssmencoder;
                switch(Encotype){
                	case 0:
                		pssmencoder = new pfmencoding(Data, SequenceSet, backgroundSet);
                		break;
                	case 1:
                		pssmencoder = new pssmencoding(Data, SequenceSet);
                		break;
                	case 2:
                		pssmencoder = new binaryencoding();
                		break;
                	default:
                		pssmencoder = new pfmencoding(Data, SequenceSet, backgroundSet);
                		break;

                }
                //binaryencoding pssmencoder = new binaryencoding();
                //pssmencoding pssmencoder = new pssmencoding(Data, SequenceSet);

                //pssmencoder.SetCenterAsZero();
                //ppmencoding pssmencoder = new ppmencoding(Data, SequenceSet, backgroundSet);
                ppmencoding ppmencoder = new ppmencoding(Data, SequenceSet, backgroundSet);
                //ppmencoder.SetCenterAsZero();
                //backgroundSet.clear();
                Map<String, Integer> AllPotentialMotifFList =   new HashMap<String, Integer>();
                Map<String, Integer> MotifTimes =   new HashMap<String, Integer>();
                Map<String, Integer> MotifMatchTimes =   new HashMap<String, Integer>();
                Set<String> TotalMotifs = new HashSet<String>();
                String MaxScoreMotif = null;
                double MaxMotifScore = 0.0;
                int MaxMotifTimes = 0;
                //Vector MotifSet = new Vector();

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (;RepeatTimes > 0;RepeatTimes--){
                	Map<String, Integer>  MotifLists  = new HashMap<String, Integer>();
                	UsedSequenceSet = (Vector)SequenceSet.clone();
                	UsedbackgroundSet = (Vector)backgroundSet.clone();
                	GroupNumOut.println(RepeatTimes);
                	SequenceRule = new Vector<String>();
                    MatchRuleNum = new Vector<Integer>();
                    BackgroundMatchRuleNum = new Vector();
                    int iterationtime = 0;
                    int SmallclusterNum = 0;
                    boolean UniformClusterFlag = false;
                    int smallgroupsize = 0;
                    int clusterNum = (int)(UsedSequenceSet.size()/Mvalue) ;//(int)Math.sqrt((UsedSequenceSet.size()));//

	             ////////////////////////////////////////////////////////////////////////////////////////
	                flag = true;
	                do{

		                double ClusterData[][] = new double [UsedSequenceSet.size()][];


		                for (int i = 0 ; i < UsedSequenceSet.size(); i++) {

		                	ClusterData[i] = pssmencoder.GetEncodeValue(UsedSequenceSet.elementAt(i).toString());

		                }

		                if(UniformClusterFlag){
		                	clusterNum--;
		                	UniformClusterFlag =false;
		                }else{
		                	clusterNum =   clusterNum - (SmallclusterNum - (int)Math.floor(smallgroupsize/ MinClusterNum)) ;
		                }
		                if(clusterNum<=0){
		                	System.out.println("cluster zero");
                            flag = false;
		                }
		                System.out.println(UsedSequenceSet.size());
		                KMeans KCluster = new KMeans(ClusterData,clusterNum);

		                if (KCluster.RunKMeans() == -1){
		                	flag = false;
		                	System.out.println("Group Too Small");
		                }

		                int[][] MotifNumbertemp  = new int [clusterNum][1];
		                int[] MotifMatchNumber  = new int [clusterNum];
		                int[] MotifNumber  = new int [clusterNum];
		                String [] REtemplate = new String[clusterNum];
		                int GoodMotifSize = 0;
		                int BadMotifSize = 0;
                        int RemovedNum = 0;
		                String clusterResOutNum = null;
		                System.out.println("Rule sizes "+MotifLists.size());
		                System.out.println("Cluster sizes "+clusterNum);
		                SmallclusterNum = 0;

		                smallgroupsize = 0;
		                for (int i = 0 ; i < clusterNum; i++) {
		                	iterationtime++;
		                	//clusterResOutName = "./Motif/" +PostiveFinName+"_it_"+iterationtime+"_gp_"+(i+1)+".csv";
		                	if (KCluster.kcluster[i].size() < MinClusterNum){SmallclusterNum++;smallgroupsize += KCluster.kcluster[i].size();}
		                	else
		                	{
			                	//REtemplate[i] = FindMotif (UsedSequenceSet,KCluster.kcluster[i],posT-MatchRuleNum.size()*0.01*0,MotifNumbertemp[i]);
			                	REtemplate[i] = FindMotif (UsedSequenceSet,KCluster.kcluster[i],posT,MotifNumbertemp[i]);

			                	MotifNumber[i] = MotifNumbertemp[i][0];
			                	if (MotifNumber[i]>=2){
				                	if (AllPotentialMotifFList.containsKey(REtemplate[i])){
				                			MotifMatchNumber[i] = AllPotentialMotifFList.get(REtemplate[i]);
				                			if(MotifMatchNumber[i] >= MinMatchNum){
					                			GoodMotifSize++;
					                		}else {
					                			BadMotifSize++;
					                		}
				                	}
				                	else{
				                			Iterator list = SequenceSet.iterator();
					                		while (list.hasNext()){
					                			if (Pattern.matches("(?i)"+REtemplate[i], (String)list.next())){
					                				MotifMatchNumber[i]++;
					                			}
					                		}
					                		AllPotentialMotifFList.put(REtemplate[i], MotifMatchNumber[i]);
					                		if(MotifMatchNumber[i] >= MinMatchNum){
					                			GoodMotifSize++;
					                		}else {
					                			BadMotifSize++;
					                		}


				                	}

			                	}else{
			                		//MotifNumber = 1 --> .....[STY].....
			                		BadMotifSize++;
			                	}
			                	clusterResOutNum = REtemplate[i]+":"+MotifMatchNumber[i]+" : "+KCluster.kcluster[i].size();
			                	System.out.println(clusterResOutNum);
			                	StringPML += clusterResOutNum+"\n";
			                	//OutPutCluster(UsedSequenceSet,KCluster.kcluster[i],clusterResOutName);
			                	//String cmd = "perl ./weblogo/seqlogo.pl -s "+posstart+" -k 0 -h 18 -w 32 -abcMnY -F PNG -f "+clusterResOutName+" -o "+clusterResOutName+".PNG";
			                	//Thread t = new Execthreads(cmd);
			                	//t.start();

		                	}
		                }
		                int choice = 0;
		                if (GoodMotifSize > 0 ){
		                	int[] GoodMotifMatchNumber  = new int [GoodMotifSize];
			                int[] GoodMotifNumber  = new int [GoodMotifSize];
			                String [] GoodTemplate = new String[GoodMotifSize];
			                int j = 0;
			                for (int i = 0 ; i < clusterNum; i++) {

			                	if (MotifMatchNumber[i] >= MinMatchNum){
			                		GoodMotifMatchNumber[j] = MotifMatchNumber[i];
			                		GoodMotifNumber[j] = MotifNumber[i];
			                		GoodTemplate[j] = REtemplate[i];
			                		j++;
			                	}

			                }
			                if(j != GoodMotifSize ){System.out.println("What!!");}


		                	int[] OrderGoodMotifNumber  = Selection_sort(GoodMotifNumber);



			                if (ifmorethantwice(OrderGoodMotifNumber,GoodMotifNumber)){

			                	int[] OrderGoodMotifMatchNumber  = Selection_sort(GoodMotifMatchNumber);



			                	//for (int i = 0 ; i < GoodMotifSize ; i++)
			                	for (int i = GoodMotifSize-1 ; i >=0 ; i--)
			                	{
			                		if (GoodMotifNumber[OrderGoodMotifMatchNumber[i]] == GoodMotifNumber[OrderGoodMotifNumber[GoodMotifSize - 1]]){
			                			choice = OrderGoodMotifMatchNumber[i];
			                		}

			                	}
			                	
			                	MotifLists.put(GoodTemplate[choice], GoodMotifMatchNumber[choice]);
			                	//SequenceRule.addElement(GoodTemplate[choice]);
		 	                	//MatchRuleNum.addElement(GoodMotifMatchNumber[choice]);

		 	                	
                                 RemovedNum = RemoveMotif(UsedSequenceSet,GoodTemplate[choice]);
                                    System.out.println("Add"+GoodTemplate[choice]+" and kill "+ RemovedNum);
			                		GroupNumOut.println("Add"+GoodTemplate[choice]+" and kill "+ RemovedNum);


		 	                	if (UsedSequenceSet.size() <= MinMatchNum){
		 	                		flag = false;
		 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
		 	                	}





			                }else{


			                	choice = OrderGoodMotifNumber[OrderGoodMotifNumber.length-1];
			                	{
			                		
			                		MotifLists.put(GoodTemplate[choice], GoodMotifMatchNumber[choice]);
			 	                	//SequenceRule.addElement(GoodTemplate[choice]);
			 	                	//MatchRuleNum.addElement(GoodMotifMatchNumber[choice]);


			 	                	 RemovedNum = RemoveMotif(UsedSequenceSet,GoodTemplate[choice]);
                                    System.out.println("Add"+GoodTemplate[choice]+" and killed "+ RemovedNum);
			                		GroupNumOut.println("Add"+GoodTemplate[choice]+" and killed "+ RemovedNum);

			 	                	if (UsedSequenceSet.size() <= MinMatchNum){
			 	                		flag = false;
			 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
			 	                	}

			 	                }

			                }
			                 /*
			                Vector slave = new Vector();
			                for (int k = GoodMotifSize-1 ; k >=0 && flag; k--) {
			                	//check motifs  have the same pos but not the same AA
			                	//!Pattern.matches("(?i)"+GoodTemplate[OrderGoodMotifNumber[k]].replace("[", "\\[").replace("]", "\\]"), GoodTemplate[choice])
			                	if (k != choice){
			                		if (ifMotifinSamePosition (GoodTemplate[choice],GoodTemplate[OrderGoodMotifNumber[k]])){
			                			if (!Pattern.matches("(?i)"+GoodTemplate[OrderGoodMotifNumber[k]].replace("[", "\\[").replace("]", "\\]"),GoodTemplate[choice])){
			                				boolean matchflag = false;
			                				for (int l = 0 ; l < slave.size(); l++){
			                					if(Pattern.matches("(?i)"+GoodTemplate[OrderGoodMotifNumber[k]].replace("[", "\\[").replace("]", "\\]"), (String)slave.elementAt(l))){
			                						matchflag = true ;
			                					}
			                				}
				                			if (!matchflag){
				                				GroupNumOut.println("Add"+GoodTemplate[OrderGoodMotifNumber[k]]+"As"+GoodTemplate[choice]+"'s slave");
				                				slave.addElement(GoodTemplate[OrderGoodMotifNumber[k]]);
				                				SequenceRule.addElement(GoodTemplate[OrderGoodMotifNumber[k]]);
						 	                	MatchRuleNum.addElement(GoodMotifMatchNumber[OrderGoodMotifNumber[k]]);


						 	                	RemoveMotif(UsedSequenceSet,GoodTemplate[OrderGoodMotifNumber[k]]);


						 	                	if (UsedSequenceSet.size() <= MinMatchNum){
						 	                		flag = false;
						 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
						 	                	}
					 	                	}

			                			}
			                		}

			                	}

		                	}*/

		                }else if(GoodMotifSize <= 0 && clusterNum <= 1){
//		                	System.out.println("Last Motif Match less than "+MinMatchNum+" sequences !");
		                	flag = false;
		                }
		                else{
		                	//System.out.println("Strongest Motif Match less than "+MinMatchNum+" sequences !");
		                	if (BadMotifSize == (clusterNum - SmallclusterNum) ||  RemovedNum == 0){
		                		UniformClusterFlag = true;
		                	}

		                }



	                }while(flag);
	                /////////////////////////////////////////////////////////////////////////////////////////////

	                Iterator it = MotifLists.entrySet().iterator();
	                while (it.hasNext()) {
	                    Map.Entry pairs = (Map.Entry)it.next();

	                	String RuleTemp = (String)pairs.getKey();
	                    SequenceRule.addElement(RuleTemp);
	                    MatchRuleNum.addElement((Integer)pairs.getValue());
	                	//GroupNumOut.println(kkkk);
						if ( MotifTimes.containsKey(RuleTemp) ){

							Integer temp = (Integer)MotifTimes.get(RuleTemp);
							temp++;

							MotifTimes.put(RuleTemp,temp);

						}else{
							Integer temp = new Integer(1);
							temp=1;
							MotifTimes.put(RuleTemp,temp);
						}
						TotalMotifs.add(RuleTemp);
						MotifMatchTimes.put(RuleTemp,MatchRuleNum.lastElement());

					}
	                GroupNumOut.flush();

                }

                System.out.println("Repeat over");
                System.out.println("Total Motifs");

                nonrepeatmotifnumber[0] = TotalMotifs.size();
                Iterator  key = TotalMotifs.iterator();
                if (TotalMotifs.size() > 0){


				 while (key.hasNext()){
					 	String Temp = (String)key.next();
	                	double MotifScoreTemp = MotifScore(Temp,ppmencoder);
	                	int MotifMatchTimesTemp = CountMotif(SequenceSet,Temp);
	                	if (MotifScoreTemp > MaxMotifScore){
	                		MaxScoreMotif = Temp;
	                		MaxMotifScore = MotifScoreTemp;
	                		MaxMotifTimes = MotifMatchTimesTemp;
	                	}else if(MotifScoreTemp == MaxMotifScore){
	                		if (MotifMatchTimesTemp > MaxMotifTimes){
	                			MaxScoreMotif = Temp;
		                		MaxMotifScore = MotifScoreTemp;
		                		MaxMotifTimes = MotifMatchTimesTemp;
	                		}
	                	}
	                	String printScore = String.format("%2.2f",MotifScoreTemp );
	                	System.out.println(Temp+":"+printScore+"X"+MotifMatchTimesTemp+"::"+(Integer)MotifTimes.get(Temp));
	                	StringCML += Temp+":"+printScore+"X"+MotifMatchTimesTemp+"::"+(Integer)MotifTimes.get(Temp)+"\n";

				 }
				 GroupNumOut.println(StringCML);
				 System.out.println("RE found");
				 System.out.println(PostiveFinName);
                Map<String, Integer> FinalMotifTimes =   new HashMap<String, Integer>();
                Map<String, Double> FinalMotifScores =   new HashMap<String, Double>();
                Map<String, Integer> FinalMotifFMatchTimes =   new HashMap<String, Integer>();
                Map<String, Integer> FinalMotifbackgroundSizes =   new HashMap<String, Integer>();
                Map<String, Integer> FinalMotifForgroundSizes =   new HashMap<String, Integer>();
                Map<String, Integer> FinalMotifBMatchTimes =   new HashMap<String, Integer>();
                Vector<String> FinalTotalMotifs = new Vector<String>();

                FinalTotalMotifs.add(MaxScoreMotif);
                FinalMotifScores.put(MaxScoreMotif,MotifScore(MaxScoreMotif ,ppmencoder));
                FinalMotifbackgroundSizes.put(MaxScoreMotif,backgroundSet.size());
                FinalMotifForgroundSizes.put(MaxScoreMotif,SequenceSet.size());
                FinalMotifFMatchTimes.put(MaxScoreMotif, RemoveMotif(SequenceSet,MaxScoreMotif));
                FinalMotifBMatchTimes.put(MaxScoreMotif, RemoveMotif(backgroundSet,MaxScoreMotif));
                FinalMotifTimes.put(MaxScoreMotif, (Integer)MotifTimes.get(MaxScoreMotif));
                TotalMotifs.remove(MaxScoreMotif);

                String num = String.format("%2.5f", (FinalMotifBMatchTimes.get(MaxScoreMotif) *100.0)/FinalMotifbackgroundSizes.get(MaxScoreMotif) );
            	String score = String.format("%2.5f",FinalMotifScores.get(MaxScoreMotif) );
            	String MatchTimes = String.format("%3d/%3d", FinalMotifFMatchTimes.get(MaxScoreMotif),FinalMotifForgroundSizes.get(MaxScoreMotif));
            	String Times = String.format("%3d", FinalMotifTimes.get(MaxScoreMotif));
            	System.out.println(MaxScoreMotif+":"+MatchTimes+"X"+Times+" :: "+num+"% Motif Score = "+score);
            	GroupNumOut.println(MaxScoreMotif+":"+MatchTimes+"X"+Times+" :: "+num+"% Motif Score = "+score);
            	FinalMotifList.println(MaxScoreMotif);
                flag = true;
               while(flag){
            	   String FinalMotifs = null;


            	   double MaxScore = 0.0;
            	   int MaxMatchTimes = 0;
            	   ppmencoding encoder = new ppmencoding(Data, SequenceSet , backgroundSet);
            	   //encoder.SetCenterAsZero();
            	   Iterator  value = TotalMotifs.iterator();

     				 while (value.hasNext()) {
     					String MotifsTemp = (String)value.next();
     					int MotifMatchTimesTemp = CountMotif(SequenceSet,MotifsTemp);
     					double MxaMotifsScoreTemp = MotifScore(MotifsTemp,encoder);
     					if (MotifMatchTimesTemp >= MinMatchNum2){
	     					if (MxaMotifsScoreTemp > MaxScore){
	     						MaxScore = MxaMotifsScoreTemp;
	     						FinalMotifs = MotifsTemp;
	     						MaxMatchTimes = MotifMatchTimesTemp;
	     					}else if(MxaMotifsScoreTemp == MaxScore){
	     						if (MaxMatchTimes < MotifMatchTimesTemp){
		     						MaxScore = MxaMotifsScoreTemp;
		     						FinalMotifs = MotifsTemp;
		     						MaxMatchTimes = MotifMatchTimesTemp;
	     						}
	     					}

     					}

     				 }
     				 if (FinalMotifs != null){
     					FinalTotalMotifs.add(FinalMotifs);
     					FinalMotifScores.put(FinalMotifs,MaxScore);
     					FinalMotifForgroundSizes.put(FinalMotifs,SequenceSet.size());
     					FinalMotifbackgroundSizes.put(FinalMotifs,backgroundSet.size());
     	                FinalMotifFMatchTimes.put(FinalMotifs, RemoveMotif(SequenceSet,FinalMotifs));
     	                FinalMotifBMatchTimes.put(FinalMotifs, RemoveMotif(backgroundSet,FinalMotifs));
     	                FinalMotifTimes.put(FinalMotifs, (Integer)MotifTimes.get(FinalMotifs));
     	                TotalMotifs.remove(FinalMotifs);
     	                 num = String.format("%2.5f", (FinalMotifBMatchTimes.get(FinalMotifs) *100.0)/FinalMotifbackgroundSizes.get(FinalMotifs) );
	                	 score = String.format("%2.5f",FinalMotifScores.get(FinalMotifs) );
	                	 MatchTimes = String.format("%3d/%3d", FinalMotifFMatchTimes.get(FinalMotifs),FinalMotifForgroundSizes.get(FinalMotifs));
	                	 Times = String.format("%3d", FinalMotifTimes.get(FinalMotifs));
	                	System.out.println(FinalMotifs+":"+MatchTimes+"X"+Times+" :: "+num+"% Motif Score = "+score);
	                	GroupNumOut.println(FinalMotifs+":"+MatchTimes+"X"+Times+" :: "+num+"% Motif Score = "+score);
	                	FinalMotifList.println(FinalMotifs);

     				 }else{
     					flag = false;
     					System.out.println("Strongest Motif Match less than "+MinMatchNum+" sequences !");
     				 }
     				nonrepeatmotifnumber[1] = FinalTotalMotifs.size();




               }


               }else{
            	   System.out.println("No motif");

               }
                long end = System.currentTimeMillis();


  				long exectimes = end-start;


  				//int MotifsOrderArray[] =  Selection_sort(MotifsTimesArray);MotifsScoreArray
  				//int MotifsOrderArray[] =  Selection_sort(MotifMatchTimesArray);
  				//int MotifsOrderArray[] =  Selection_sort(MotifsScoreArray);


  				GroupNumOut.println("Done exec times = "+exectimes+"ms");
                GroupNumOut.close();
                FinalMotifList.close();

                System.out.println("Done exec times = "+exectimes+"ms");
            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            /*JOptionPane.showMessageDialog(null, "no this file",
                                          "FileNotFoundException",
                                          JOptionPane.ERROR_MESSAGE);*/
        } catch (IOException ex) {
        	System.err.println(ex);
        }
        return nonrepeatmotifnumber;

    }
    public void PrintMotif() {

    	for (int i = 0; i < SequenceRule.size(); i++) {
    		//System.out.println(SequenceRule.elementAt(i)+":"+MatchRuleNum.elementAt(i)+"/"+BackgroundMatchRuleNum.elementAt(i));
    		System.out.println(SequenceRule.elementAt(i)+":"+MatchRuleNum.elementAt(i));

    	}

    }

    public void setFlag(boolean in) {

        flag = in;
    }
    public int RemoveMotif(Vector Set,String rule) {
    	Iterator list = Set.iterator();
     	int MatchTime = 0;
     	while (list.hasNext()){
     		if (Pattern.matches("(?i)"+rule, (String)list.next())){
     			list.remove();
     			MatchTime++;
     		}

     	}
     	return MatchTime;

    }
    public int CountMotif(Vector Set,String rule) {
    	Iterator list = Set.iterator();
     	int MatchTime = 0;
     	while (list.hasNext()){
     		if (Pattern.matches("(?i)"+rule, (String)list.next())){
     			MatchTime++;
     		}

     	}
     	return MatchTime;

    }
    public boolean  ifmorethantwice (int[]order,int[]value) {

    	int size = value.length;


    	if (size < 2){
    		return false;
    	}else{
    		if (value[order[size-1]] == value[order[size-2]]){
        		return true;
        	}else{
        		return false;
        	}

    	}
    }
    public double MotifScore (String Motif , ppmencoding encoder) {
    	double Score = 0.0;
    	String[] pre = Motif.split("\\[[A-Z]+\\]");
    	char MotifCharTemp ;
    	int shift = 0;

    	if( pre.length == 2){

	    	for (int q = 0; q < pre.length ;q++) {
	    		for (int p = pre[q].length()-1; p >= 0 ;p--) {
	    			MotifCharTemp = pre[q].charAt(p);
	    			if ( MotifCharTemp != '.'){
	    				Score += encoder.Pssm[encoder.change[MotifCharTemp]][p+shift];
	    			}

	    		}
	    		shift += pre[q].length()+1;
	    	}
    	}else{
    		int half = (Motif.length() - 1)/2;
	    		for (int p =Motif.length()-1; p >= 0 ;p--) {
	    			MotifCharTemp = Motif.charAt(p);
	    			if ( MotifCharTemp != '.'&& p != half){
	    				Score += encoder.Pssm[encoder.change[MotifCharTemp]][p];
	    			}
	    		}


    	}

    	return Score;
    }
    public boolean  ifMotifinSamePosition (String Master,String Slave) {
        boolean flag = true;

    	for ( int i = Master.length() - 1; i >= 0; i--) {

        	if (Master.charAt(i) == '.' ){
        		if (Slave.charAt(i) != '.'){
        			 flag = false;
        		}

        	}

        }

    	return flag;

    }



    public String FindMotif(Vector Set,Vector in,double cutoff,int[] Numbers) {
    	int size = in.size();
    	int Dim = Set.elementAt(0).toString().length();
    	int Pmatrix[][] = new int[Dim][21];
    	char template[] = new char[Dim];
    	int Number  = 0 ;
    	Iterator list = in.iterator();
		String temp = "";
		int i = 0;
		int Max = 0;
        double Threshold = 1;
        if(Absoluteflag){
            Threshold = cutoff;
        }else{
		  Threshold = cutoff*size;//cutoff;//10;//cutoff*size;
        }
		if (cutoff <= 0){Threshold = 0;};
		while (list.hasNext()){
			temp = Set.elementAt((Integer)list.next()).toString();//

			for (i = Dim - 1; i >= 0; i--) {

            	Pmatrix[i][change[temp.charAt(i)]]++;

            }

		}
		String abs ="";
		for (int j = 0; j < Dim; j++) {
			Max = Selection_sort(Pmatrix[j])[21-1];
			abs += aaMap[Max]+":"+Pmatrix[j][Max]+";";
			if(Pmatrix[j][Max] >= (Threshold)){
				template[j] = aaMap[Max];
				Number++;
			}else{
				template[j] = '.';
			}
		}
		if (Number == 1){
			System.out.println(abs);

		}

		Numbers[0] = Number;
		String restemplate = "";
		int half = (Dim-1)/2;
		for (int j = 0; j < half; j++) {
			restemplate += template[j];
		}
		restemplate += centerReg;
		for (int j = half+1; j < Dim; j++) {
			restemplate += template[j];
		}
		/*
		for (int j = 0; j < Dim; j++) {
			restemplate += template[j];
		}
		*/

		return restemplate;




    }
    public void OutPutCluster(Vector Set,Vector in,String name) {

    	Iterator list = in.iterator();
		String temp = "";

		PrintWriter clusterResOut;
		try {
			clusterResOut = new PrintWriter(new BufferedWriter(
					new FileWriter(name)));
			while (list.hasNext()){
				temp = Set.elementAt((Integer)list.next()).toString();//

				clusterResOut.println(temp);

			}
			clusterResOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}






    }


    int[] Selection_sort(double[] data) {
		int order[] = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			order[i] = i;
		}
		for (int i = 0; i < data.length - 1; i++) {
			// find minimun in i ~ data.length - 1
			int min = i;
			for (int j = i + 1; j < data.length; j++) {
				if (data[order[min]] > data[order[j]]) {
					min = j;
				}
			}
			// swap data[i] with data[min]
			int tmp = order[i];
			order[i] = order[min];
			order[min] = tmp;
		}
		return order;
	}
    int[] Selection_sort(int[] data) {
		int order[] = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			order[i] = i;
		}
		for (int i = 0; i < data.length - 1; i++) {
			// find minimun in i ~ data.length - 1
			int min = i;
			for (int j = i + 1; j < data.length; j++) {
				if (data[order[min]] > data[order[j]]) {
					min = j;
				}
			}
			// swap data[i] with data[min]
			int tmp = order[i];
			order[i] = order[min];
			order[min] = tmp;
		}
		return order;
	}


    int[] randomMapGenerater(int arraysize, boolean flag) {
        int randomMap[] = new int[arraysize];
        if (flag == true) {
            int ran;
            for (int i = arraysize; i > 0; i--) {
                ran = (int) (Math.random() * 100000) % i;
                int k = 0;
                int j = 0;
                for (j = 0; k <= ran; k += ((randomMap[j] == 0) ? 1 : 0), j++) {
                    ;
                }

                randomMap[j - 1] = i - 1;
            }
        } else {
            for (int i = arraysize - 1; i >= 0; i--) {
                randomMap[i] = i;
            }

        }

        return randomMap;
    }

    int[] randomMapGenerater2(int arraysize, boolean flag) {
        int randomMap[] = new int[arraysize];
        int temp;
        if (flag == true) {
            for (int i = arraysize - 1; i >= 0; i--) {
                randomMap[i] = i;
            }
            for (int j = 3; j > 0; j--) {

                int ran;
                for (int i = arraysize - 2; i > 0; i--) {
                    ran = (int) (Math.random() * Integer.MAX_VALUE) % (i + 1);
                    temp = randomMap[i + 1];
                    randomMap[i + 1] = randomMap[ran];
                    randomMap[ran] = temp;

                }

            }
        } else {
            for (int i = arraysize - 1; i >= 0; i--) {
                randomMap[i] = i;
            }

        }

        return randomMap;
    }


} // end class









