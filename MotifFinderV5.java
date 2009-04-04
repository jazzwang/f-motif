// Fig. 25.25: DisplayAuthors.java
// Displaying the contents of the authors table.
//import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.util.regex.*;


public class MotifFinderV5 {
    querydata Data = new querydata();
    boolean flag = false;
    Vector SequenceSet;
    Vector SequenceRule;
    Vector MatchRuleNum;
    Vector BackgroundMatchRuleNum;
    Vector backgroundSet;
    Vector UsedSequenceSet;
    Vector UsedbackgroundSet;
       
    static private int MinMatchNum;
    static private int MinMatchNum2;
    static private int Frequecny;
    static private String Background;
    
    char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
            'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y','V','X'};
    int  change[] = new int[128];
    static String PostiveFinName = "";
    public MotifFinderV5() {
    	
    	for (int i = 0; i < aaMap.length; i++) {
            change[aaMap[i]] = i;
        }

    }

    public MotifFinderV5(querydata data) {
        Data = data;
        for (int i = 0; i < aaMap.length; i++) {
            change[aaMap[i]] = i;
        }

    }

    public static void main(String args[]) {
	if(args.length > 0) {
	  PostiveFinName = args[0];
	  Background = args[1];
	  Frequecny = Integer.parseInt(args[2] ,10); 
	  MinMatchNum  = Integer.parseInt(args[3] ,10);
	  MinMatchNum2 = Integer.parseInt(args[3] ,10);
	} else {
	  System.out.print("java MotifFinderV5 INPUT_FILENAME [All|Homo] FREQUENCY MATCH_NUMBER");
	}

    	querydata data = new querydata();
  /*
    	int input = -1 ;
        System.out.println("Please choice one kind of kinases family");
        for (int i = 0 ;i < data.kinasesname.length;i++ ){
            System.out.println((i+1)+"." + data.kinasesname[i]);
        }
        BufferedReader input1 = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";
         StringTokenizer tokens;
         try {
             while (input > 5 || input < 1){
                 System.out.print(">>");
                 temp = input1.readLine();
                  tokens = new StringTokenizer(temp);
                   int i = Integer.parseInt(tokens.nextToken());
                   input = i ;
             }
             //System.out.println(input-49);
         } catch (IOException ex) {

             System.out.println(ex);
             System.out.println("輸入失敗");
         }
          //System.out.println();

          System.out.println("you choice "+data.kinasesname[input-1]);
          System.out.println("choic start window size  ");
          System.out.print(">>");
         try {
             temp = input1.readLine();
         } catch (IOException ex1) {
         }
          tokens = new StringTokenizer(temp);
          int m = Integer.parseInt(tokens.nextToken());

          System.out.println("choic end window size  ");
          System.out.print(">>");
         try {
             temp = input1.readLine();
         } catch (IOException ex1) {
         }
          tokens = new StringTokenizer(temp);
          int n = Integer.parseInt(tokens.nextToken());

          System.out.println("choic repeat times  ");
         System.out.print(">>");
        try {
            temp = input1.readLine();
        } catch (IOException ex1) {
        }
         tokens = new StringTokenizer(temp);
         int repeat  = Integer.parseInt(tokens.nextToken());


*/
    	for (int j = 2; j <= 2 ; j++){
    		for (int i = 13; i <= 13; i += 2) {
				
				data.windows_size = i;
				data.shift = "";
				data.kinease = data.kinasesname[j];
				
				MotifFinderV5 SeqDatapool = new MotifFinderV5(data);
				SeqDatapool.start(Frequecny);
				
				//SeqDatapool.PrintMotif();
		
			}
    	}
/*
    	try {
			
		
  		// data.kinease =data.kinasesname[3];
        for (int j = 3; j <= 3; j++)
  		//for (int j = input-1; j <= input-1; j++)
  		{
        	PrintWriter Out1 = new PrintWriter(new BufferedWriter(
					new FileWriter("./Motif/"+data.kinasesname[j]+"_MotifRepeatTimesTest1.csv")));
        	PrintWriter Out2 = new PrintWriter(new BufferedWriter(
					new FileWriter("./Motif/"+data.kinasesname[j]+"_MotifRepeatTimesTest2.csv")));
  			//for (int i = m; i <= n; i += 2)
        	int tempout[] = null;
  			for (int i = 13; i <= 13; i += 2) 
  			{
  				
  				for (int m = 1; m <= 51; m +=5){
  					for (int n = 1; n <= 30; n++){
  					
  						data.windows_size = i;
  						data.shift = "";
  						data.kinease = data.kinasesname[j];
  						
  						MotifFinderV5 SeqDatapool = new MotifFinderV5(data);
  						tempout = SeqDatapool.start(m);
  						Out1.print(tempout[0]+",");
  						Out1.flush();
  						Out2.print(tempout[1]+",");
  						Out2.flush();
  						//SeqDatapool.PrintMotif();
					
  					}
  					Out1.println();
  					Out2.println();
					
  				}
  				
			} 
  			Out1.close();
  			Out2.close();
  		}
       
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
        
		

    }

    

    public int[] start(int RepeatTimes) {
    	int nonrepeatmotifnumber[] = new int[2];
    	try {

            String kinases = Data.kinease;
            String code = Data.code;
            String form = Data.form;
            int windows_size = Data.windows_size;
            
           {
        	   String species =Data.species;
               if (Data.species.equals("ALL")){
               	species = "ALL";
               }
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
	       if ( Background == "All" )
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
                 PrintWriter MotifListOut = new PrintWriter(new BufferedWriter(
                 		new FileWriter("./output/" +PostiveFinName+"_MotifList.csv")));
                 
                int kfold = Data.kfold;

                SequenceSet = new Vector();
                
                backgroundSet = new Vector();
              
                String readtemp;
                int count = 0;
                while (PostiveFin.ready()) {
                	SequenceSet.addElement(PostiveFin.readLine());
                }
                PostiveFin.close();
                while (backgroundFin.ready()) {
                    backgroundSet.addElement(backgroundFin.readLine());
                }
                backgroundFin.close();

                pfmencoding pssmencoder = new pfmencoding(Data, SequenceSet, backgroundSet);
                //ppmencoding pssmencoder = new ppmencoding(Data, SequenceSet, backgroundSet);
                ppmencoding ppmencoder = new ppmencoding(Data, SequenceSet, backgroundSet);
                //backgroundSet.clear();
                Map<String, Integer> MotifTimes =   new HashMap<String, Integer>();
                Map<String, Integer> MotifMatchTimes =   new HashMap<String, Integer>();
                Set<String> TotalMotifs = new HashSet<String>();
                String MaxScoreMotif = null;
                double MaxMotifScore = 0.0;
                int MaxMotifTimes = 0;
                //Vector MotifSet = new Vector();
                
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (;RepeatTimes > 0;RepeatTimes--){
                	
                	UsedSequenceSet = (Vector)SequenceSet.clone();
                	UsedbackgroundSet = (Vector)backgroundSet.clone();
                	GroupNumOut.println(RepeatTimes);
                	SequenceRule = new Vector();
                    MatchRuleNum = new Vector();
                    BackgroundMatchRuleNum = new Vector();
                    	                
	               
	             ////////////////////////////////////////////////////////////////////////////////////////
	                flag = true;
	                do{
		                
		                double ClusterData[][] = new double [UsedSequenceSet.size()][];
		                                
		                
		                for (int i = 0 ; i < UsedSequenceSet.size(); i++) {
		                	
		                	ClusterData[i] = pssmencoder.GetEncodeValue(UsedSequenceSet.elementAt(i).toString());
		                	
		                }
		           
		                                
		                int clusterNum = UsedSequenceSet.size()/30 + 2;
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
		                
		                for (int i = 0 ; i < clusterNum; i++) {
		                	REtemplate[i] = FindMotif (UsedSequenceSet,KCluster.kcluster[i],0.4-MatchRuleNum.size()*0.01,MotifNumbertemp[i]);
		                	MotifNumber[i] = MotifNumbertemp[i][0];
		                	if (MotifNumber[i]>=2){
		                		Iterator list = SequenceSet.iterator();	                		
		                		while (list.hasNext()){
		                			if (Pattern.matches("(?i)"+REtemplate[i], (String)list.next())){
		                				MotifMatchNumber[i]++;
		                			}
		                		}
		                		if(MotifMatchNumber[i] >= MinMatchNum){
		                			GoodMotifSize++;
		                		}
		                		
		                	}
		                	System.out.println(REtemplate[i]+":"+MotifMatchNumber[i]);
		                	
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
			                	
			                	
			                	
			                	for (int i = 0 ; i < GoodMotifSize; i++) {
			                		if (GoodMotifNumber[OrderGoodMotifMatchNumber[i]] == GoodMotifNumber[OrderGoodMotifNumber[GoodMotifSize - 1]]){
			                			choice = OrderGoodMotifMatchNumber[i];
			                		}
			                		
			                	}
			                	GroupNumOut.println("Add"+GoodTemplate[choice]);
			                	SequenceRule.addElement(GoodTemplate[choice]);
		 	                	MatchRuleNum.addElement(GoodMotifMatchNumber[choice]);
		 	                	
		 	                	                	
		 	                	RemoveMotif(UsedSequenceSet,GoodTemplate[choice]);
		 	                	
		 	                	
		 	                	
		 	                	if (UsedSequenceSet.size() <= MinMatchNum){
		 	                		flag = false;
		 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
		 	                	}
		 	                	
		 	                	
			                	
			                	
			                	
			                }else{
			                	
			                	
			                	choice = OrderGoodMotifNumber[OrderGoodMotifNumber.length-1];
			                	{
			                		GroupNumOut.println("Add"+GoodTemplate[choice]);
			 	                	SequenceRule.addElement(GoodTemplate[choice]);
			 	                	MatchRuleNum.addElement(GoodMotifMatchNumber[choice]);
			 	                	
			 	                	 	                	
			 	                	RemoveMotif(UsedSequenceSet,GoodTemplate[choice]);
			 	                	
			 	                	if (UsedSequenceSet.size() <= MinMatchNum){
			 	                		flag = false;
			 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
			 	                	}
			 	                	
			 	                }
			                	
			                }
			                Vector slave = new Vector();
			                for (int k = 0 ; k < GoodMotifSize && flag; k++) {
			                	//check motifs  have the same pos but not the same AA
			                	
			                	if (k != choice){
			                		if (ifMotifinSamePosition (GoodTemplate[choice],GoodTemplate[k])){
			                			if (!Pattern.matches("(?i)"+GoodTemplate[k], GoodTemplate[choice])){
			                				boolean matchflag = false; 
			                				for (int l = 0 ; l < slave.size(); l++){
			                					if(Pattern.matches("(?i)"+GoodTemplate[k], (String)slave.elementAt(l))){
			                						matchflag = true ;
			                					}
			                				}
				                			if (!matchflag){
				                				GroupNumOut.println("Add"+GoodTemplate[k]+"As"+GoodTemplate[choice]+"'s slave");
				                				slave.addElement(GoodTemplate[k]);
				                				SequenceRule.addElement(GoodTemplate[k]);
						 	                	MatchRuleNum.addElement(GoodMotifMatchNumber[k]);
						 	                	 
						 	                	
						 	                	RemoveMotif(UsedSequenceSet,GoodTemplate[k]);
						 	                	
						 	                	
						 	                	if (UsedSequenceSet.size() <= MinMatchNum){
						 	                		flag = false;
						 	                		System.out.println("Remain less than "+MinMatchNum+" sequences !");
						 	                	}
					 	                	}
					 	                	
			                			}
			                		}
			                			
			                	}
		                	}
			                
		                }else{
		                	//System.out.println("Strongest Motif Match less than "+MinMatchNum+" sequences !");
		                	flag = false;
		                }
		                
		                
		                
		                
		                
		               
	                }while(flag);
	                /////////////////////////////////////////////////////////////////////////////////////////////
	               
	                
	                //GroupNumOut.close();
	               
	                
	                for (int k = SequenceRule.size()-1;k >=0;k--){
	                	String RuleTemp = (String)SequenceRule.elementAt(k);
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
						MotifMatchTimes.put(RuleTemp,(Integer)MatchRuleNum.elementAt(k));
						
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
	                	System.out.println(Temp+":"+printScore+"X"+MotifMatchTimesTemp);
	                	MotifListOut.println(Temp+":"+printScore+"X"+MotifMatchTimesTemp);
					 
				 }
				 MotifListOut.close();
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
            	FinalMotifList.println(MaxScoreMotif);
                flag = true;
               while(flag){
            	   String FinalMotifs = null;
            	   
            	   
            	   double MaxScore = 0.0;
            	   int MaxMatchTimes = 0;
            	   ppmencoding encoder = new ppmencoding(Data, SequenceSet , backgroundSet);
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
  				
  				
  				
  				
  				
  				//int MotifsOrderArray[] =  Selection_sort(MotifsTimesArray);MotifsScoreArray
  				//int MotifsOrderArray[] =  Selection_sort(MotifMatchTimesArray);
  				//int MotifsOrderArray[] =  Selection_sort(MotifsScoreArray);
               
               
               
                GroupNumOut.close();
                FinalMotifList.close();
         		
                System.out.println("Done");
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
    	char MotifCharTemp ;
    	int half = (Motif.length() - 1)/2;
    	for (int p =Motif.length()-1; p >= 0 ;p--) {
    		MotifCharTemp = Motif.charAt(p);
    		if ( MotifCharTemp != '.'&& p != half){
    			Score += encoder.Pssm[encoder.change[MotifCharTemp]][p];
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
    	double Pmatrix[][] = new double[Dim][21]; 
    	char template[] = new char[Dim];
    	int Number  = 0 ;
    	Iterator list = in.iterator();
		String temp = "";
		int i = 0;
		int Max = 0;
		double Threshold = cutoff*size;
		while (list.hasNext()){
			temp = Set.elementAt((Integer)list.next()).toString();//
			
			for (i = Dim - 1; i >= 0; i--) {

            	Pmatrix[i][change[temp.charAt(i)]]++;

            }
           
		}
		
		for (int j = 0; j < Dim; j++) {
			Max = Selection_sort(Pmatrix[j])[21-1];
			if(Pmatrix[j][Max] >= (Threshold)){
				template[j] = aaMap[Max];
				Number++;
			}else{
				template[j] = '.';
			}
		}
		/*
		for (int j = 0; j < Dim; j++) {
            for (int k = 0; k <  aaMap.length; k++) {
            	Pmatrix[j][k] /= size;
            }            
        }
        */
		Numbers[0] = Number;
		String restemplate = "";
		for (int j = 0; j < Dim; j++) {
			restemplate += template[j];
		}
		
		
		return restemplate;
            

    	
       
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

    





