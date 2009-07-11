package fmotif;
import java.util.Vector;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;

/**
 * �`�N��XFRAME���S������
 *
 */
 public  class pfmencoding extends Encoding{
     public querydata Data;
     public double Pssm[][];

     public int  change[] = new int[128];


     public pfmencoding(querydata data) {
        PositionFrequenceMatrix pwm = new PositionFrequenceMatrix(data);
        PositionFrequenceMatrix bwm = new PositionFrequenceMatrix(data);
        pwm.start('P');
        bwm.start('p');

        Pssm = pwm.wm ;
        for (int i = 0 ; i < 20;i++){

            for (int j = 0 ; j <  data.windows_size;j++){

                if (Pssm[i][j] != 0){

                    Pssm[i][j] =  Math.log(Pssm[i][j]/bwm.wm[i][j]);
                }
                else{

                    Pssm[i][j] = 0;
                }
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
    public pfmencoding(querydata data,Vector seqin,Vector seqin2) {
        Data = data;
        
        /*
       PositionFrequenceMatrix pwm = new PositionFrequenceMatrix(data,seqin);
       PositionFrequenceMatrix bwm = new PositionFrequenceMatrix(data,seqin2);
       pwm.start2();
       bwm.start2();
       */
         
       PositionProbabilityMatrix pwm = new PositionProbabilityMatrix(data,seqin);
       PositionProbabilityMatrix bwm = new PositionProbabilityMatrix(data,seqin2);
       pwm.SetRelativeFlag();
       pwm.start2();
       bwm.SetRelativeFlag();
       bwm.start2();
        
       Pssm = pwm.wm ;
       for (int i = 0 ; i < 20;i++){

           for (int j = 0 ; j <  data.windows_size;j++){

               if (Pssm[i][j] != 0){

                   Pssm[i][j] =  Math.log(Pssm[i][j]/bwm.wm[i][j]);
               }
               else{

                   Pssm[i][j] = 0;
               }
           }

       }

       /*
       try {
               PrintWriter profilefout = new PrintWriter(new BufferedWriter(new
                       FileWriter("profile_" + data.kinease + "_" +
                                  data.code + "_" + data.windows_size + ".csv")));

                      profilefout.print("Position=");
                      for (int j = 0 ; j < data.windows_size;j++){
                          profilefout.print("," + (j - data.windows_size / 2));
                      }
                      profilefout.println();

               for (int i = 0 ; i < 20;i++){
                   for (int j = 0 ; j <  data.windows_size;j++){
                       profilefout.print(","+(Pssm[i][j]));
                   }
                   profilefout.println();
               }
               profilefout.close();

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

    public String SVMencoder(String insequence) {
        String encoding = "";
        int half = (insequence.length() - 1)/2;

        for (int i = 0; i < insequence.length(); i++) {
            encoding += svmprint(i, insequence.charAt(i),0*-1);
        }

        return encoding;
    }
    public double[] GetEncodeValue(String insequence) {
    	int Dim = insequence.length();
    	double[] encoding = new double[Dim];
    	for (int i = 0; i < Dim; i++) {
            encoding[i] = Pssm[change[insequence.charAt(i)]][i];
        }

        return encoding;
    }
    public void SetCenterAsZero() {
    	int Dim = Pssm.length;
    	int half = (Pssm[0].length - 1)/2;
    	
    	for (int i = 0; i < Dim; i++) {
           Pssm[i][half] =0;
        }

        
    }
    public String PALSVMencoder(String insequence) {
        String encoding = "";
        

        for (int i = 0; i < insequence.length(); i++) {
            encoding += PALsvmprint(i, insequence.charAt(i));
        }

        return encoding;
    }
    
    public String SlideSVMencoder(String insequence,int shift ) {
        String encoding = "";
        int length = Data.slide_size;

        for (int i = 0+shift; i < length +shift; i++) {
            encoding += svmprint(i , insequence.charAt(i),shift*-1);
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
    public String PALsvmprint(int i, char temp) {

        String outputtemp ="";


             outputtemp += Pssm[change[temp]][i]+" ";



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
           outputtemp += " "+(i * size + j + 1 + shift )+":"+Pssm[change[temp]][j];
       }

       return outputtemp;

   }




}
