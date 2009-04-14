// Fig. 25.25: DisplayAuthors.java
// Displaying the contents of the authors table.
import java.sql.*;
import java.lang.*;
import java.io.*;
import java.util.*;


public class wmatrix {
    // JDBC driver name and database URL
    static Vector pwmseq;
    static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
    static final String DATABASE_URL = "jdbc:odbc:project2";
    static querydata Data = new querydata();
    static double wm[][];
    //jdbc:mysql://localhost/books
    // launch the application
    public wmatrix() {

    }

    public wmatrix(querydata data) {
        Data = data;

    }

    public wmatrix(querydata data, Vector seqin) {
        Data = data;
        pwmseq = seqin;

    }

    public static void main(String args[]) {

        wmatrix wmatrix = new wmatrix();
        wmatrix.start();
        try {
            PrintWriter profilefout = new PrintWriter(new BufferedWriter(new
                    FileWriter("profile_" + Data.kinease + "_" +
                               Data.code + "_" + Data.windows_size + ".csv")));

            profilefout.print("Position=");
            for (int j = 0; j < Data.windows_size; j++) {
                profilefout.print("," + (j - Data.windows_size / 2));
            }
            profilefout.println();

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < Data.windows_size; j++) {
                    profilefout.print("," + wm[i][j]);
                }
                profilefout.println();
            }
            profilefout.close();

        } catch (IOException ex) {

        }

    }

    public void start() {

        Connection connection = null; // manages connection
        Connection connection2 = null;
        Statement statement = null; // query statement
        Statement statement2 = null;
        wm = new double[20][Data.windows_size];
        String QueryKinasesName = "%" + Data.kinease + "%";
        //data.code = data.codenames[3];
        String QueryCodeName = Data.code;
        int windows_size = Data.windows_size;

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
                connection2 =
                        DriverManager.getConnection(DATABASE_URL, "", "");
                // create Statement for querying database
                statement = connection.createStatement();
                statement2 = connection2.createStatement();
                String ACC = null;
                String SEQUENCE = null;
                String KINASES = null;
                String LIKE = "LIKE";
                //int POSITION = 0;
                //int index = 0;
                String temp = null;
                //int numtemp = 0;
                //int LENGTH = (int) 0;
                //int count = 0;
                double[] totalAAcount = new double[windows_size];

                double weightmatrix[][] = new double[windows_size][128]; //windowssize;aa;

                String statementquery1 = "SELECT Mid(sequence,(position-" +
                                         shift + ")," +
                                         windows_size + ") AS TARGET, index,code,length,position,sequence FROM Dataset_041106 WHERE ((position-" +
                                         shift + ")>1) AND ((position +" +
                                         shift +
                                         ")<length) AND kinases " + LIKE + " '" +
                                         QueryKinasesName +
                                         "' AND (code LIKE '" + QueryCodeName +
                                         "')";
                System.out.println("#" + statementquery1);
                ///fout.println("#"+statementquery1);
                ResultSet resultSet1 = statement.executeQuery(statementquery1);
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
                char[] aaMap = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H',
                               'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y',
                               'V', 'X'};
                double[] expmatrix = {0.0701313443873091 ,0.0582393695201718 ,0.0359362961736045 ,0.0520743144385134 ,0.0172010453343506 ,0.0498574962335004 ,0.0796465136978452 ,0.0624720283551962 ,0.0241405228512130 ,0.0416989778376737 ,0.0934441156861220 ,0.0632334844952389 ,0.0213293464067050 ,0.0324554733241482 ,0.0651181982370858 ,0.0881672518230193 ,0.0524630941595624 ,0.0101093184162382 ,0.0244701177088640 ,0.0578116909136386};
                //double[] aaMapfreq = new double[windows_size];
                double freq = 0;
                for (int j = 0; j < weightmatrix.length; j++) {
                    for (int i = 0; i < aaMap.length - 1; i++) {
                        totalAAcount[j] += weightmatrix[j][aaMap[i]];
                    }
                }

                for (int i = 0; i < aaMap.length - 1; i++) {
                    //profilefout.print(aaMap[i]);
                    for (int j = 0; j < windows_size; j++) {

                        freq = ((weightmatrix[j][aaMap[i]]) / (totalAAcount[j])) +
                               1;

                        wm[i][j] = Math.log10((freq / expmatrix[i])) /
                                   Math.log10(2.0);

                        //  profilefout.print(","+aaMapfreq[i]);


                    }
                    //profilefout.println();
                }

                //fout.close();
                //profilefout.close();

                resultSet1.close();
                connection.close();
            }

        } // end try

        catch (ClassNotFoundException classNotFound) {
            classNotFound.printStackTrace();
            System.exit(1);
        } catch (NullPointerException nullpointerException) {
            nullpointerException.printStackTrace();
            System.exit(1);

        } catch (SQLException ex) {
            /** @todo Handle this exception */
            /*        } catch (IOException ex) {
             */
            /** @todo Handle this exception */
        } catch (NoClassDefFoundError ex) {

        }

        finally { // ensure statement and connection are closed properly
            try {
                statement.close();
                statement2.close();

                connection.close();

                connection2.close();
            } catch (Exception exception) { // end try
                exception.printStackTrace();
                System.exit(1);
            } // end catch
        } // end finally
    } // end main

    public void start2() {

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

                String ACC = null;
                String SEQUENCE = null;
                String KINASES = null;
                String LIKE = "LIKE";
                //int POSITION = 0;
                //int index = 0;
                String temp = null;
                //int numtemp = 0;
                //int LENGTH = (int) 0;
                //int count = 0;
                double[] totalAAcount = new double[windows_size];

                double weightmatrix[][] = new double[windows_size][128]; //windowssize;aa;

                ///fout.println("#"+statementquery1);

                int seqsize = 0;

                for (int i = 0; i < pwmseq.size(); i++) {
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
                double[] expmatrix = {0.0701313443873091 ,0.0582393695201718 ,0.0359362961736045 ,0.0520743144385134 ,0.0172010453343506 ,0.0498574962335004 ,0.0796465136978452 ,0.0624720283551962 ,0.0241405228512130 ,0.0416989778376737 ,0.0934441156861220 ,0.0632334844952389 ,0.0213293464067050 ,0.0324554733241482 ,0.0651181982370858 ,0.0881672518230193 ,0.0524630941595624 ,0.0101093184162382 ,0.0244701177088640 ,0.0578116909136386};
                //double[] aaMapfreq = new double[windows_size];
                double freq = 0;
                for (int j = 0; j < weightmatrix.length; j++) {
                    for (int i = 0; i < aaMap.length - 1; i++) {
                        totalAAcount[j] += weightmatrix[j][aaMap[i]];
                    }
                }

                for (int i = 0; i < aaMap.length - 1; i++) {
                    //profilefout.print(aaMap[i]);
                    for (int j = 0; j < windows_size; j++) {

                        freq = ((weightmatrix[j][aaMap[i]] + 0.05) /
                                (totalAAcount[j] + 1));

                        wm[i][j] = Math.log10((freq / expmatrix[i])) /
                                   Math.log10(2.0);

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

        } catch (NoClassDefFoundError ex) {

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
