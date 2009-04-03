
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class KMeans {
	int state = 0;
	int initLimit = 1000;
	int rerunLimit = 5000;
	int miniGroupsize = 5;
	int KNum;
	int DataNum;
	int Dim;
	double ClusterMeans[][];//K X dim
	double DataPoints[][];//DataNum X dim
	int lableDataPoints[];//DataNum
	int RroupNum[];
	Vector[] kcluster;

	KMeans(Vector inDataPoints , int k){

		KNum = k;
		DataNum = inDataPoints.size();
		RroupNum = new int[KNum];
		DataPoints = new double[DataNum][];
		lableDataPoints = new int[DataNum];
		ClusterMeans = new double[KNum][];
		Iterator list = inDataPoints.iterator();
		int i = 0;
		while (list.hasNext()){
			DataPoints[i] = (double[])list.next();
			lableDataPoints[i]= (int)(Math.random()*(Integer.MAX_VALUE))%KNum;
			RroupNum[lableDataPoints[i]]++;
			i++;
		}
		Dim = DataPoints[0].length;
		ClusterMeans = new double[KNum][Dim];


	}
	KMeans(double[][] inDataPoints , int k){
		
		KNum = k;
		DataNum = inDataPoints.length;
		RroupNum = new int[KNum];
		DataPoints = new double[DataNum][];
		lableDataPoints = new int[DataNum];
		ClusterMeans = new double[KNum][];
		
		
		for (int i = 0; i < DataNum; i++){
			DataPoints[i] = inDataPoints[i];
			lableDataPoints[i]= (int)(Math.random()*(Integer.MAX_VALUE))%KNum;
			RroupNum[lableDataPoints[i]]++;
			
		}
		Dim = DataPoints[0].length;
		ClusterMeans = new double[KNum][Dim];


	}
	public int RunKMeans(){
		System.out.println("RunKMeans");
		if (KNum > 1){
			int Iteration = 0;
	        InitKMeans();
			while (CheckGroup(miniGroupsize)&&Iteration < initLimit){InitKMeans();Iteration++;}
			if (Iteration >= initLimit){state = -1;System.out.println("\nLimit!");}
	        boolean UpdateMeansState = UpdateMeans();
			int count = 1;
			
			while (UpdateMeansState && state == 0){
				if (rerunLimit == 0){
						System.out.println("\nHit re run Limit!!");state = -1 ;rerunLimit--;
					}
				ReCluster();
				System.out.print("_");
				Iteration = 0;
				while (CheckGroup(miniGroupsize)&& state == 0){
					rerunLimit--;
					InitKMeans();Iteration++; count = 1;
					if (Iteration >= initLimit){state = -1 ;System.out.println("\nLimit!!");break;}
				}
				UpdateMeansState  = UpdateMeans();
				count++;
			}
			System.out.println("\n"+count+" Round Over");
		}else if(KNum == 1){
			for (int k = 0 ; k < DataNum; k++ ){
				lableDataPoints[k]= 0;
				
			}
			for (int i = 0 ; i < DataNum; i++ ){
				for (int j = 0 ; j < Dim; j++){
					ClusterMeans[lableDataPoints[i]][j] += DataPoints[i][j];
				}

			}
			for (int i = 0 ; i < KNum; i++ ){
				for (int j = 0 ; j < Dim; j++){
					ClusterMeans[i][j] /= RroupNum[i];
				}

			}
			
		}else{
			
			System.out.println("Zero");
		}
		
		kcluster = new Vector[KNum];
		for (int k = 0 ; k < KNum; k++){
			kcluster[k] = new Vector();
		}
		for (int k = 0 ; k < DataNum; k++){
			kcluster[lableDataPoints[k]].addElement(k);
		}
		
		
		
		return state;

	}
	public void Initlable(){

		for (int k = 0 ; k < KNum; k++){
			RroupNum[k] = 0;
		}
          System.out.println("Reset the lables");
		for (int k = 0 ; k < DataNum; k++ ){
			lableDataPoints[k]= (int)(Math.random()*(Integer.MAX_VALUE))%KNum;
			RroupNum[lableDataPoints[k]]++;
		}
	}
	public void InitKMeans(){


		boolean flag = true;
		
		while (flag ){
			
			int[]Map =  randomMapGenerater(DataNum,true);
			double [] ori = new double[Dim];
			for (int i = 0 ; i < KNum; i++){
				for (int j = 0 ; j < Dim; j++){
					ClusterMeans[i][j] =  DataPoints[Map[i]][j];
				}

			}
			double Distence = 0.0;
			
			NofM nOfm = new NofM(2, KNum);
			boolean sameflag = false;
			do {
				int[] set = nOfm.next();
				
				Distence = Euclideandistance(ClusterMeans[set[0]-1],ClusterMeans[set[1]-1]);
				
				if (  Double.isNaN(Distence) ||  Double.isInfinite(Distence)){
					System.out.print(ClusterMeans[set[0]-1]+"TO"+ClusterMeans[set[1]-1]);
					BigDecimal b = new BigDecimal(Distence);
					BigDecimal one = new BigDecimal("1");
					Distence = b.divide(one,32,BigDecimal.ROUND_FLOOR).doubleValue();
					
				}
				if (  Distence == 0.0){
					sameflag = true;
				}
				
			} while(nOfm.hasNext());
			
			if ( sameflag){
				flag = true;
				System.out.print("i");
			}else{
				flag = false;
				System.out.print("I");
				ReCluster();
			}
			
		}
		

	}

	public void ReCluster(){
		//System.out.println("Clustering");

		double Distence[] = new double[KNum];
		for (int j = 0 ; j < KNum; j++){
			RroupNum[j] = 0;
		}

		for (int i = 0 ; i < DataNum; i++ ){

			for (int j = 0 ; j < KNum; j++){
				Distence[j] = Euclideandistance( DataPoints[i],ClusterMeans[j]);
			}

			lableDataPoints[i] = Selection_sort(Distence)[0];
			RroupNum[lableDataPoints[i]]++;
		}



	}
	public boolean CheckGroup(int min){
		boolean flag= false;
		int state = 0;
		for (int j = 0 ; j < KNum; j++){
			if (RroupNum[j] <= min){
				//System.out.println("Small Group size at Group "+RroupNum[j]);
				state++;

			}
		}

		if(state > 0){
			flag = true;
		}else{

			flag = false;
		}

		return flag;
	}
	public boolean UpdateMeans(){

		//System.out.println("UpdateMeans");
		double [][] OldMeans = new double[KNum][Dim];
		for (int i = 0 ; i < KNum; i++ ){
			for (int j = 0 ; j < Dim; j++){
				OldMeans[i][j] = ClusterMeans[i][j] ;
				ClusterMeans[i][j] = 0;
			}

		}

		for (int i = 0 ; i < DataNum; i++ ){
			for (int j = 0 ; j < Dim; j++){
				ClusterMeans[lableDataPoints[i]][j] += DataPoints[i][j];
			}

		}
		for (int i = 0 ; i < KNum; i++ ){
			for (int j = 0 ; j < Dim; j++){
				ClusterMeans[i][j] /= RroupNum[i];
			}

		}
		double sumofsquarederror = 0.0;
		for (int i = 0; i < KNum ; i++){

			sumofsquarederror += Euclideandistance(OldMeans[i],ClusterMeans[i]);

		}
		if (  Double.isNaN(sumofsquarederror) ||  Double.isInfinite(sumofsquarederror)){
			System.out.print(false);
		}

		 //BigDecimal b = new BigDecimal(sumofsquarederror);
	     //BigDecimal one = new BigDecimal("1");
	     //sumofsquarederror = b.divide(one,32,BigDecimal.ROUND_FLOOR).doubleValue();//

	     boolean state = false;
	     if (sumofsquarederror <= 0.0 ){
	    	 state = false;
	     }else{
	    	 state = true;
	     }



		return state;

	}
	public int[] GetReslable(){

		return lableDataPoints;
	}
        public double[][] GetKmeans(){

                return ClusterMeans;
        }

        public void PrintLables(){
            for (int i = 0 ; i < DataNum; i++ ){

            System.out.println(lableDataPoints[i]+",");


        }

        }
	public void PrintMeans(){

		for (int i = 0 ; i < KNum; i++ ){
			for (int j = 0 ; j < Dim; j++){
				System.out.print(ClusterMeans[i][j]+",");
			}
			System.out.print("\n");
		}

	}
	public void PrintMeans(PrintWriter out){

		for (int i = 0 ; i < KNum; i++ ){
			for (int j = 0 ; j < Dim; j++){
				out.print(ClusterMeans[i][j]+",");
			}
			out.print("\n");
		}

	}
        public void PrintGroupNums(PrintWriter out){

                for (int i = 0 ; i < KNum; i++ ){

                                out.print(RroupNum[i]+",");


                }
                out.print("\n");

        }
        public void PrintGroupNums(){

            for (int i = 0 ; i < KNum; i++ ){

                            System.out.print(RroupNum[i]+",");


            }
            System.out.print("\n");

    }


	double Euclideandistance(double[] invector1, double[] invector2) {
		double distance = 0;
		int vectorsize = invector1.length;
		for (int i = 0; i < vectorsize; i++) {
			distance += Math.pow((invector1[i] - invector2[i]), 2);
		}
		distance = Math.sqrt(distance);
		return distance;
	}
	int[] randomMapGenerater(int arraysize, boolean flag) {
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
	public static void main(String[] args) {

		Vector data = new Vector();
		int i = 100;
		while(i-- > 0){
			double one[] = new double[2];
			one[0] = 1;
			one[1] = Math.random();
			data.add(one);

		}
		i = 100;
		while(i-- > 0){
			double one[] = new double[2];
			one[0] = 1;
			one[1] = Math.random();
			data.add(one);

		}
               /* 
		i = 100000;
		while(i-- > 0){
			double one[] = new double[2];
			one[0] = Math.random();
			one[1] = Math.random();
			data.add(one);

		}
		i = 100000;
		while(i-- > 0){
			double one[] = new double[2];
			one[0] = (Math.random())*-1;
			one[1] = (Math.random());
			data.add(one);

		}
                
*/



		KMeans aaa = new KMeans(data , 10);
		aaa.RunKMeans();
		aaa.PrintMeans();



	}

}
    class NofM {
    private int m;
    private int[] set;
    private boolean first;
    private int position;

    public NofM(int n, int m) {
        this.m = m;
        first = true;
        position = n - 1;

        set = new int[n];
        for(int i = 0; i < n; i++)
            set[i] = i + 1;
    }

    public boolean hasNext() {
        return set[0] < m - set.length + 1;
    }

    public int[] next() {
        if(first) {
            first = false;
            return set;
        }

        if(set[set.length-1] == m)
            position--;
        else
            position = set.length - 1;

        set[position]++;

        // 調整右邊元素
        for(int i = position + 1; i < set.length; i++)
            set[i] = set[i-1] + 1;

        return set;
    }


}


