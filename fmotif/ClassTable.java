package fmotif;

public class ClassTable extends LoopHandler {
    private double DataPoints[][];
    private double ClusterMeans[][];
    private int lableDataPoints[];
    private int RroupNum[];
    
    public ClassTable(int th,int ini, int end ,double DataPoints1[][],double ClusterMeans1[][],int lableDataPoints1[],int RroupNum1[]) {
    	super(ini, end, th);
    	
    	DataPoints = DataPoints1;
    	ClusterMeans = ClusterMeans1;
    	lableDataPoints = lableDataPoints1;
    	RroupNum = RroupNum1;
    	
    	
        
    }

    public void loopDoRange(int start, int end) {
    	int KNum = ClusterMeans.length;
    	double Distence[] = new double[KNum];
		//System.out.println("do from "+start+" to "+end);
    	for (int i = start ; i < end; i++ ){
			double mindis = Double.MAX_VALUE;
			for (int j = 0 ; j < KNum; j++){
				Distence[j] = Euclideandistance( DataPoints[i],ClusterMeans[j]);
				if (mindis >= Distence[j]){
					mindis = Distence[j];
					lableDataPoints[i] = j;
				}
			}
			//System.out.print("do from"+start+" to "+end);
			synchronized(this){
				RroupNum[lableDataPoints[i]]++;
			}
			}
    	//System.out.println("done from "+start+" to "+end);
    }    

    public void todo() {
    	
        loopProcess();
        
    }

    public static void main(String args[]) {
       
     
        System.out.println("Done");
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
	public void ShowArray(float in1[][]){
		for(int i = 0 ; i < in1.length ; i++){
			System.out.printf("R% 2d: ",(i+1));
			for(int j = 0 ; j < in1[i].length ; j++){
				System.out.printf("%8.3f,",in1[i][j]);
			}
			System.out.println();
		}
	}
	public void ShowArray(float in1[]){
		for(int i = 0 ; i < in1.length ; i++){
			System.out.printf("%03f,",in1[i]);
			}
		System.out.println();
	}
}

