package fmotif;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class binaryencoding extends Encoding {


    public String precoding ="";
    public int  change[] = new int[128];
    public binaryencoding(String insequence) {

        precoding = insequence;

    }
    public binaryencoding( ) {
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
    public double[] GetEncodeValue(String insequence){
    	double  encoding [] = new double [insequence.length()*20];
    	int shift = 0;
    	for (int i = 0; i < insequence.length();i++){
    		shift =	change[insequence.charAt(i)] ;
    		encoding [i*20+shift] = 1.0;	
    	}
    	
    	return encoding;
    }
    public String SVMencoder(String insequence) {
        String encoding = "";
        precoding = insequence;
        int half  = (precoding.length()-1)/2;
        half = 0 ;
        for (int i = half; i < precoding.length(); i++) {
            encoding += svmprint(i-half, precoding.charAt(i));
        }

        return encoding;
    }
    public String SVMencoder(String insequence,int shift) {
       String encoding = "";
       precoding = insequence;
       int half  = (precoding.length()-1)/2;
       half = 0 ;
       for (int i = half; i < precoding.length(); i++) {
           encoding += svmprint(i-half+shift, precoding.charAt(i));
       }

       return encoding;
   }

    public String NNencoder() {
        String encoding = "";
        for (int i = 0; i < precoding.length(); i++) {
            encoding += NNprint( precoding.charAt(i));
        }

        return encoding;
    }


    public String NNprint( char temp) {


        switch (temp) {
        case 'A':
            return "1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'R':
            return "0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'N':
            return "0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'D':
            return "0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'C':
            return "0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'Q':
            return "0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'E':
            return "0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'G':
            return "0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'H':
            return "0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 ";

        case 'I':
            return "0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 ";

        case 'L':
            return "0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 ";

        case 'K':
            return "0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 ";

        case 'M':
            return "0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 ";

        case 'F':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 ";

        case 'P':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 ";

        case 'S':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 ";

        case 'T':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 ";
        case 'W':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 ";
        case 'Y':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 ";

        case 'V':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 ";

        case 'X':
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        default:
            return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

        }

    }


    public String svmprint(int i, char temp) {
        int size = 20;

        switch (temp) {
        case 'A':
            return " " + (1 + size * i) + ":1 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'R':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":1 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'N':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":1 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'D':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":1 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'C':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":1 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'Q':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":1 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'E':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":1 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'G':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":1 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'H':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":1 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'I':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":1 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'L':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":1 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'K':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":1 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'M':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":1 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'F':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":1 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'P':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":1 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'S':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":1 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'T':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":1 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        case 'W':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":1 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";
        case 'Y':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":1 " + (20 + size * i) + ":0";

        case 'V':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":1";

        case 'X':
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0";

        default:
            return " " + (1 + size * i) + ":0 " + (2 + size * i) + ":0 " +
                    (3 + size * i) +
                    ":0 " + (4 + size * i) + ":0 " + (5 + size * i) + ":0 " +
                    (6 + size * i) +
                    ":0 " + (7 + size * i) + ":0 " + (8 + size * i) + ":0 " +
                    (9 + size * i) +
                    ":0 " + (10 + size * i) + ":0 " + (11 + size * i) + ":0 " +
                    (12 + size * i) +
                    ":0 " + (13 + size * i) + ":0 " + (14 + size * i) + ":0 " +
                    (15 + size * i) +
                    ":0 " + (16 + size * i) + ":0 " + (17 + size * i) + ":0 " +
                    (18 + size * i) +
                    ":0 " + (19 + size * i) + ":0 " + (20 + size * i) + ":0" ;

        }

    }

}
