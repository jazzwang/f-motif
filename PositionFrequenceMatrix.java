// Fig. 25.25: DisplayAuthors.java
// Displaying the contents of the authors table.
import java.sql.*;
import java.lang.*;
import java.io.*;
import java.util.*;


public class PositionFrequenceMatrix {
    // JDBC driver name and database URL
    Vector pwmseq;
    static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
    static final String DATABASE_URL = "jdbc:odbc:project2";
    static final String DATABASE_TABLE = "Dataset_0707";//"Dataset_041106"
    querydata Data = new querydata();
    double wm[][];
    //jdbc:mysql://localhost/books
    // launch the application
    public PositionFrequenceMatrix(){

    }
    public PositionFrequenceMatrix(querydata data){
        Data = data;

   }
   public PositionFrequenceMatrix(querydata data,Vector seqin){
        Data = data;
        pwmseq = seqin;

   }
   public PositionFrequenceMatrix(querydata data,List seqin){
       Data = data;
       pwmseq = new Vector(seqin);

  }


   public static void main(String args[]) {

       PositionFrequenceMatrix wmatrix = new PositionFrequenceMatrix();
       wmatrix.start('p');
    try {
        PrintWriter profilefout = new PrintWriter(new BufferedWriter(new
                FileWriter("profile_" + wmatrix.Data.kinease + "_" +
                           wmatrix.Data.code + "_" + wmatrix.Data.windows_size+ ".csv")));

               profilefout.print("Position=");
               for (int j = 0 ; j < wmatrix.Data.windows_size;j++){
                   profilefout.print("," + (j - wmatrix.Data.windows_size / 2));
               }
               profilefout.println();

        for (int i = 0 ; i < 20;i++){
            for (int j = 0 ; j <  wmatrix.Data.windows_size;j++){
                profilefout.print(","+wmatrix.wm[i][j]);
            }
            profilefout.println();
        }
        profilefout.close();

    } catch (IOException ex) {

    }

   }

    public  void start (char choice) {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        wm = new double[20][Data.windows_size];
        String QueryKinasesName = "%" + Data.kinease + "%";
        //data.code = data.codenames[3];

        String QueryCodeName = Data.code;
        char code = QueryCodeName.charAt(0);
        int windows_size = Data.windows_size;
        int center = (int) ( (windows_size-1)/2);

        //int windows_size = 9;

        int shift = windows_size / 2;

        try {
            Class.forName(JDBC_DRIVER); // load database driver class
            for (windows_size = Data.windows_size;
                                windows_size <= Data.windows_size;
                                windows_size += 2) {
                shift = windows_size / 2;
                // establish connection to database
                connection =
                        DriverManager.getConnection(DATABASE_URL, "", "");

                // create Statement for querying database
                statement = connection.createStatement();

                String LIKE = "LIKE";
                //int POSITION = 0;
                //int index = 0;
                String temp = null;

                double[] totalAAcount = new double[windows_size];

                double weightmatrix[][] = new double[windows_size][128]; //windowssize;aa;

                if (choice == 'P') {
                    String statementquery1 = "SELECT Mid(sequence,(position-" +
                                             shift + ")," +
                                             windows_size + ") AS TARGET, index,code,length,position,sequence FROM "+DATABASE_TABLE+" WHERE ((position-" +
                                             shift + ")>1) AND ((position +" +
                                             shift +
                                             ")<length) AND kinases " + LIKE +
                                             " '" +
                                             QueryKinasesName +
                                             "' AND (code LIKE '" +
                                             QueryCodeName +
                                             "')";

                    System.out.println("#" + statementquery1);
                    ///fout.println("#"+statementquery1);
                    ResultSet resultSet1 = statement.executeQuery(
                            statementquery1);
                    int seqsize = 0;
                    while ((resultSet1.next())) {
                        String posseq = resultSet1.getString("TARGET");
                        seqsize = posseq.length();
                        if (posseq.charAt(0) != 'X' &&
                            posseq.charAt(seqsize - 1) != 'X') { //去掉邊緣

                            for (int i = 0; i < seqsize; i++) {

                                weightmatrix[i][posseq.charAt(i)]++;

                            }
                            //possequence.addElement(posseq);
                        }

                    } // end while
                    resultSet1.close();
                    connection.close();
                }
                else if (choice == 'p'){
                    PrintWriter   profilefout = new PrintWriter(new BufferedWriter(new  FileWriter("XXXXXXXXXX.csv")));
                    String statementquery1 =
                            "SELECT Dataset_0707.acc FROM Dataset_0707 GROUP BY Dataset_0707.acc ";

                    //// PrintWriter    profilefout = new PrintWriter(new BufferedWriter(new   FileWriter("XXXXXXXXXX.csv")));


                    System.out.println("#" + statementquery1);
                    ///fout.println("#"+statementquery1);
                    ResultSet resultSet1 = statement.executeQuery(
                            statementquery1);
                    Vector ACCIn = new Vector();
                    while ((resultSet1.next())) {
                        ACCIn.addElement(resultSet1.getString("acc"));

                    }
                    int count = 1;
                    for(int k = 0 ; k < ACCIn.size(); k++) {
                        statementquery1 =
                                "SELECT  sequence FROM Dataset_0707 WHERE acc ='"+ACCIn.get(k)+"'";




                        //System.out.println("#" + statementquery1);
                        ///fout.println("#"+statementquery1);
                        resultSet1 = statement.executeQuery(
                                statementquery1);
                        int seqsize = 0;
                        int seqsize2 = 0;

                        resultSet1.next();
                        //while ((resultSet1.next()))
                        {
                            temp = resultSet1.getString("sequence");
                            //temp ="ARNDCQEGHILKMFPSTWYXVV";
                            profilefout.println(">"+count++);
                            profilefout.println(temp);
                            seqsize2 = temp.length();
                            for (int j = 0; j <= seqsize2 - windows_size; j++) {
                                String posseq = temp.substring(j,
                                        (j + windows_size));

                                //posseq =  "ARNDCQEGHILKMFPSTWYXV";
                                seqsize = posseq.length();
                                if (seqsize < windows_size) {
                                    System.out.println("window size not eq");
                                }
                                if (posseq.charAt(0) != 'X' &&
                                    posseq.charAt(seqsize - 1) != 'X') { //去掉邊緣
                                     if (posseq.charAt(center) == code ){
                                         for (int i = 0; i < seqsize; i++) {

                                             weightmatrix[i][posseq.charAt(i)]++;

                                         }
                                     }
                                    //possequence.addElement(posseq);
                                }
                            }
                        } // end while





                    }
                     profilefout.close();
                    resultSet1.close();
                    connection.close();

                }

                char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
                               'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y',
                               'V', 'X'};

                //double[] aaMapfreq = new double[windows_size];

                for (int j = 0 ; j < weightmatrix.length;j++){
                    for (int i = 0; i < aaMap.length - 1; i++) {
                        totalAAcount[j] += weightmatrix[j][aaMap[i]];
                    }
                }

                for (int i = 0; i < aaMap.length - 1; i++) {
                    //profilefout.print(aaMap[i]);
                    for (int j = 0 ; j < windows_size;j++){

                          wm[i][j]   = ((weightmatrix[j][aaMap[i]]) / (totalAAcount[j]));

                      //  profilefout.print(","+aaMapfreq[i]);


                    }
                    //profilefout.println();
                }

                //fout.close();
                //profilefout.close();


            }

        } // end try

        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (NullPointerException nullpointerException) {
            nullpointerException.printStackTrace();
            System.exit(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            /** @todo Handle this exception */
       } catch (IOException ex) {
            /** @todo Handle this exception */
         }  catch (NoClassDefFoundError ex) {

        }

        finally { // ensure statement and connection are closed properly
            try {
                statement.close();
                connection.close();

            } catch (Exception exception) { // end try
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
    } // end main

    public  void start2 () {


       wm = new double[20][Data.windows_size];
       String QueryKinasesName = "%" + Data.kinease + "%";
       //data.code = data.codenames[3];
       String QueryCodeName = Data.code;
       int windows_size = Data.windows_size;

       //int windows_size = 9;

       int shift = windows_size / 2;

       try {

          {
               shift = windows_size / 2;
               // establish connection to database

               String LIKE = "LIKE";
               //int POSITION = 0;
               //int index = 0;
               //int numtemp = 0;
               //int LENGTH = (int) 0;
               //int count = 0;
               double[] totalAAcount = new double[windows_size];

               double weightmatrix[][] = new double[windows_size][128]; //windowssize;aa;


               ///fout.println("#"+statementquery1);

               int seqsize = 0;

               for (int i = 0; i < pwmseq.size(); i++){
                   String posseq = pwmseq.elementAt(i).toString();
                   seqsize = posseq.length();
                  {
                       for (int j = 0; j < seqsize; j++) {

                           weightmatrix[j][posseq.charAt(j)]++;

                       }
                       //possequence.addElement(posseq);
                   }

               } // end while
               char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
                              'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y',
                              'V', 'X'};

               //double[] aaMapfreq = new double[windows_size];

               for (int j = 0 ; j < weightmatrix.length;j++){
                   for (int i = 0; i < aaMap.length - 1; i++) {
                       totalAAcount[j] += weightmatrix[j][aaMap[i]];
                   }
               }

               for (int i = 0; i < aaMap.length - 1; i++) {
                   //profilefout.print(aaMap[i]);
                   for (int j = 0 ; j < windows_size;j++){


                         wm[i][j]   = ((weightmatrix[j][aaMap[i]]) / (totalAAcount[j]));

                     //  profilefout.print(","+aaMapfreq[i]);


                   }
                   //profilefout.println();
               }

               //fout.close();
               //profilefout.close();


           }

       } // end try

       catch (NullPointerException nullpointerException) {
           nullpointerException.printStackTrace();
           System.exit(1);

       }  catch (NoClassDefFoundError ex) {

       }

       finally { // ensure statement and connection are closed properly
           try {

           } catch (Exception exception) { // end try
               exception.printStackTrace();
               System.exit(1);
           } // end catch
       } // end finally
   } // end main


} // end class DisplayAuthors
