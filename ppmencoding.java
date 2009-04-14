import java.util.Vector;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.math.BigDecimal;


/**
 * 注意輸出FRAME有沒有平移
 *
 */
 public  class ppmencoding extends Encoding{
     querydata Data;
    public double Pssm[][];
    public BigDecimal binomial[][];

    public int  change[] = new int[128];


    public ppmencoding(querydata data) {
        PositionProbabilityMatrix pwm = new PositionProbabilityMatrix(data);
       PositionProbabilityMatrix bwm = new PositionProbabilityMatrix(data);
       pwm.SetAbsoluteFlag();
       pwm.start('P');
       bwm.SetRelativeFlag();
       bwm.start('p');
       
       int N = 0;

       for (int i = 0 ; i < 20;i++){
           N += pwm.wm[i][0];
        }

             SetBigBinomial(N);
             Pssm = new double[pwm.wm.length][pwm.wm[0].length];

             for (int i = 0 ; i < 20;i++){

                 for (int j = 0 ; j <  data.windows_size;j++){
                     if (pwm.wm[i][j] > 0) {
                         for (int k =(int) (pwm.wm[i][j]-1); k >= 0 ; k--){
                              //binomial[N][K]*Math.pow(P,K)*Math.pow(1-P,N-K);

                            //Pssm[i][j] += binomial[N][k]*Math.pow(bwm.wm[i][j],k)*Math.pow(1-bwm.wm[i][j],N-k);

                        }

                     }
                     Pssm[i][j] = Math.log(1- Pssm[i][j])*-1;

                 }
             }


        change['A'] = 0;
        change['R'] = 1;
        change['N'] = 2;
        change['D'] = 3;
        change['C'] = 4;
        change['Q'] = 5;
        change['E'] = 6;
        change['G'] = 7;
        change['H'] = 8;
        change['I'] = 9;
        change['L'] = 10;
        change['K'] = 11;
        change['M'] = 12;
        change['F'] = 13;
        change['P'] = 14;
        change['S'] = 15;
        change['T'] = 16;
        change['W'] = 17;
        change['Y'] = 18;
        change['V'] = 19;

    }
    public ppmencoding(querydata data,Vector seqin,Vector seqin2) {
        Data = data;
       PositionProbabilityMatrix pwm = new PositionProbabilityMatrix(data,seqin);
       PositionProbabilityMatrix bwm = new PositionProbabilityMatrix(data,seqin2);
       pwm.SetAbsoluteFlag();
       pwm.start2();
       bwm.SetRelativeFlag();
       bwm.start2();
       int N = seqin.size();
       //SetBigBinomial(N);
       double[] BI =  LogFactorial (N);
       char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
               'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y',
               'V', 'X'};
       double pssmtemp = 0.0;
       Pssm = new double[pwm.wm.length][pwm.wm[0].length];
       Arith math = new Arith();
       int startPOS = 0;
       double pricestion =  Math.pow(10.0,-16);
       for (int i = 0 ; i < 20;i++){

           for (int j = 0 ; j <  data.windows_size;j++){
                //BigDecimal temp1 = new BigDecimal(0);
               //if (pwm.wm[i][j] >= 0)
               {
            	   startPOS = (int)(pwm.wm[i][j] );
                   for (int k = N ; k >=  startPOS; k--){
                       //BigDecimal P = new BigDecimal(bwm.wm[i][j]);
                       //BigDecimal NotP = new BigDecimal(1-bwm.wm[i][j]);
                        //BigDecimal temp2 = new BigDecimal(Math.pow(bwm.wm[i][j],k)*Math.pow(1-bwm.wm[i][j],N-k));
                         //temp1 = temp1.add(binomial[N][k].multiply(P.pow(k).multiply(NotP.pow(N-k))));
                      //Pssm[i][j] += (binomial[N][k].multiply(temp2)).doubleValue();
                      
                	   //Pssm[i][j] += math.round(BI[N][k] * Math.pow(bwm.wm[i][j],k) * Math.pow((1.0 - bwm.wm[i][j]),N-k),18);
                	   pssmtemp = Math.exp((BI[N]-BI[k]-BI[(N-k)]+k*Math.log(bwm.wm[i][j])+(N-k)*Math.log(1.0 - bwm.wm[i][j])));
                	   if (Double.isNaN(pssmtemp)){
                		   pssmtemp = 0;
                	   }
                	   Pssm[i][j] +=  pssmtemp;
                	   
                  }

               }
               //Pssm[i][j] = temp1.doubleValue();
               //if (Pssm[i][j] > 1.0){Pssm[i][j] = 1.0-Float.MIN_VALUE;System.out.println(i +" "+j);}
               if (Pssm[i][j] < pricestion ){Pssm[i][j] = pricestion;}
               if (Pssm[i][j] > 1.0 ){Pssm[i][j] = 1.0;}
               Pssm[i][j] =-1.0*Math.log10(Pssm[i][j]);

           }
       }
/*
       
       try {
                PrintWriter profilefout = new PrintWriter(new BufferedWriter(new
                        FileWriter("PPM_" + data.kinease + "_" +
                                   data.code + "_" + data.windows_size +"_"+data.part+".csv")));
                PrintWriter Bfout = new PrintWriter(new BufferedWriter(new
                        FileWriter("Relfeq_" + data.kinease + "_" +
                                   data.code + "_" + data.windows_size +"_"+data.part+".csv")));
                PrintWriter Pfout = new PrintWriter(new BufferedWriter(new
                        FileWriter("Absfeq_" + data.kinease + "_" +
                                   data.code + "_" + data.windows_size +"_"+data.part+".csv")));
                PrintWriter Binfout = new PrintWriter(new BufferedWriter(new
                        FileWriter("Binomial_" + data.kinease + "_" +
                                   data.code + "_" + data.windows_size +"_"+data.part+".csv")));
                 for (int i = 0 ; i <= N;i++){

                     Binfout.println(binomial[N][i]);
                 }
                 Binfout.close();



                       profilefout.print("Position=");
                       for (int j = 0 ; j < data.windows_size;j++){
                           profilefout.print("," + (j - data.windows_size / 2));
                           Pfout.print("," + (j - data.windows_size / 2));
                           Bfout.print("," + (j - data.windows_size / 2));
                       }
                       profilefout.println();
                       Bfout.println();
                       Pfout.println();


                for (int i = 0 ; i < 20;i++){
                	Bfout.print(aaMap[i]);
                    Pfout.print(aaMap[i]);
                    profilefout.print(aaMap[i]);
                    for (int j = 0 ; j <  data.windows_size;j++){
                        Bfout.print(","+bwm.wm[i][j]);
                        Pfout.print(","+pwm.wm[i][j]);
                        profilefout.print(","+(Pssm[i][j]));
                    }
                    Pfout.println();
                    Bfout.println();
                    profilefout.println();
                }
                profilefout.close();
                Pfout.close();
                Bfout.close();

            } catch (IOException ex) {

            }
  */      
       change['A'] = 0;
       change['R'] = 1;
       change['N'] = 2;
       change['D'] = 3;
       change['C'] = 4;
       change['Q'] = 5;
       change['E'] = 6;
       change['G'] = 7;
       change['H'] = 8;
       change['I'] = 9;
       change['L'] = 10;
       change['K'] = 11;
       change['M'] = 12;
       change['F'] = 13;
       change['P'] = 14;
       change['S'] = 15;
       change['T'] = 16;
       change['W'] = 17;
       change['Y'] = 18;
       change['V'] = 19;

   }
    public double[] GetEncodeValue(String insequence) {
    	int Dim = insequence.length();
    	double[] encoding = new double[Dim];
    	for (int i = 0; i < Dim; i++) {
            encoding[i] = Pssm[change[insequence.charAt(i)]][i];
        }

        return encoding;
    }
    public String SVMencoder(String insequence) {
        String encoding = "";
        int half = (insequence.length() - 1)/2;
        half = 0;
        for (int i =  half; i < insequence.length()-0; i++) {
            encoding += svmprint(i-half, insequence.charAt(i));
        }

        return encoding;
    }
    public String SVMencoder(String insequence,int shift) {
        String encoding = "";
        int half = (insequence.length() - 1)/2;
        half = 0;
        for (int i =  half; i < insequence.length()-0; i++) {
            encoding += svmprint(i-half, insequence.charAt(i),shift);
        }

        return encoding;
    }

    public String SVMencoder2(String insequence) {
       String encoding = "";
       int half = (insequence.length() - 1)/2;
       half = 0;
       for (int i =  half; i < insequence.length()-0; i++) {
           encoding += svmprint2(i-half, insequence.charAt(i));
       }

       return encoding;
   }
   public String SVMencoder2(String insequence,int shift) {
       String encoding = "";
       int half = (insequence.length() - 1)/2;
       half = 0;
       for (int i =  half; i < insequence.length()-0; i++) {
           encoding += svmprint2(i-half, insequence.charAt(i),shift);
       }

       return encoding;
   }

    public String SUMencoder(String insequence) {
        String encoding = "";
        int half = (insequence.length() - 1)/2;
        half = 0;
        double sum= 0;
        for (int i =  half; i < insequence.length()-0; i++) {
            sum += Pssm[change[insequence.charAt(i)]][i];
        }
        encoding = ""+sum;
        return encoding;
    }

    public String NNencoder(String insequence) {
        String encoding = "";
        for (int i = 0; i < insequence.length(); i++) {
            encoding += nnprint( insequence.charAt(i));
        }

        return encoding;
    }


    public String nnprint( char temp) {
        String outputtemp ="";
        for(int i = 0; i < 20;i++){
            outputtemp += Pssm[change[temp]][i]+" ";
        }

        return outputtemp;


    }


    public String svmprint(int i, char temp) {

        String outputtemp ="";


             outputtemp += " "+(1 + i)+":"+Pssm[change[temp]][i];



        return outputtemp;

    }
    public String svmprint(int i, char temp,int shift) {

       String outputtemp ="";


            outputtemp += " "+(1 + i + shift)+":"+Pssm[change[temp]][i];



       return outputtemp;

   }

    public String svmprint2(int i, char temp) {
        int size =  Data.windows_size;
       String outputtemp ="";

       for(int j = 0 ;j < size; j++ ){
           outputtemp += " "+( i * size + j + 1)+":"+Pssm[change[temp]][j];
       }

       return outputtemp;

   }
   public String svmprint2(int i, char temp,int shift) {
       int size =  Data.windows_size;
      String outputtemp ="";

      for(int j = 0 ;j < size; j++ ){
          outputtemp += " "+( i * size + j + 1 + shift)+":"+Pssm[change[temp]][j];
      }

      return outputtemp;

  }

   public void SetBigBinomial (int N ){
      binomial = new BigDecimal[N+1][N+1] ;

      for (int k = 1; k <= N; k++) binomial[0][k] = new BigDecimal(0);
      for (int n = 0; n <= N; n++) binomial[n][0] = new BigDecimal(1);

       for (int n = 1; n <= N; n++){
           for (int k = 1; k <= N; k++){

               binomial[n][k] =binomial[n-1][k-1].add(binomial[n-1][k]);

           }
       }



   }
   public double [][] Binomial (int N ){
      double[][] binomial1 = new double[N + 1][N + 1];

       for (int k = 1; k <= N; k++)
           binomial1[0][k] = 0.0;
       for (int n = 0; n <= N; n++)
           binomial1[n][0] = 1.0;

       for (int n = 1; n <= N; n++) {
           for (int k = 1; k <= N; k++) {

               binomial1[n][k] = binomial1[n - 1][k - 1]+binomial1[n - 1][k];
               if (Double.isInfinite(binomial1[n][k])){
            	   binomial1[n][k] = Double.MAX_VALUE;
            	   System.out.println("Too many sequence");
               }
           }
           
       }
       return binomial1;
   }
   
   public double [] LogFactorial (int N ){
	      double[] factorial = new double[N + 1];
	      factorial[0] = 0.0;
	       for (int n = 0; n < N; n++){
	    	   factorial[n+1] = factorial[n] + Math.log(n+1);
	           
	       }
	       

	       return factorial;
	   }




}
