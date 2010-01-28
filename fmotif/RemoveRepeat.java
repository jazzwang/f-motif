package fmotif;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


public class RemoveRepeat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader PostiveFin;
		Set<String> sequenceset = new HashSet<String>();
		try {
			//PSP_A_H_PKA
			String filename = "./PTMDATA/Seq_positive_ALL_PKC_S_13_phosphoELM_1208.txt";
			PostiveFin = new BufferedReader(new FileReader(filename));
			String temp = null;
			 while (PostiveFin.ready()) {
				 temp = PostiveFin.readLine();
				 // sequenceset.add(temp.toUpperCase().substring(1,temp.length()-1));
				 sequenceset.add(temp);
	         }
			 
	         PostiveFin.close();
	         
             PrintWriter GroupNumOut = new PrintWriter(new BufferedWriter(
            		new FileWriter(filename+"_N.txt")));
             Iterator it = sequenceset.iterator();
             while (it.hasNext()) {
            	 GroupNumOut.println((String)it.next());
             }
             GroupNumOut.close();
	         
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void start(Vector<String> in ){
		Set<String> sequenceset = new HashSet<String>();
		Iterator it = in.iterator();
        while (it.hasNext()) {
        	sequenceset.add((String)it.next());
        }
        in.clear();
        it = sequenceset.iterator();
        while (it.hasNext()) {
        	in.add((String)it.next());
        }
       		
	}
	

}
