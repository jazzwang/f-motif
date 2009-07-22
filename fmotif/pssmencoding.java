package fmotif;
import java.util.Vector;

/**
 * �`�N��XFRAME���S������
 *
 */
 public  class pssmencoding extends Encoding{

    public double Pssm[][];
    public int  change[] = new int[128];


    public pssmencoding(querydata data) {
        wmatrix wm = new wmatrix(data);
        wm.start();
        Pssm = wm.wm;

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
    public pssmencoding(querydata data,Vector seqin) {
       wmatrix wm = new wmatrix(data,seqin);
       wm.start2();
       Pssm = wm.wm;

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
    public String SVMencoder(String insequence,int shiit) {

       String encoding = "";
       int half = (insequence.length() - 1)/2;
       half = 0;
       for (int i =  half; i < insequence.length()-0; i++) {
           encoding += svmprint(i-half, insequence.charAt(i),shiit);
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


             outputtemp += " "+(1 + i + shift )+":"+Pssm[change[temp]][i];



        return outputtemp;

    }



}
