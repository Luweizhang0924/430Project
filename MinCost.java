/*
 * group member: Luwei Zhang; Yufan Huang; Xiaotao Wang
 */

package project;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MinCost {
	static ArrayList<Target> arr_tar = new ArrayList<Target>(); //store the target objects
	static ArrayList<Sensor> arr_sen = new ArrayList<Sensor>(); //store the sensor objects
	static Stack<Sensor> optStack = new Stack<Sensor>();
	static ArrayList<ArrayList<TValue>> tar_arr = new ArrayList<ArrayList<TValue>>(); // 3 dimension to record (Ui,Di) for each target
	static int min_cost=0;
	
	static double y1;		//upper y
	static double y2;		//lower y
	static Sensor upInfinit = new Sensor("su",10000,10000,0);
	static Sensor lowInfinit = new Sensor("sl",-10000,-10000,0);
	
	public static void findOPT1(Target t){  // find the opt1
		double x = t.getX();
		double y = t.getY();
		int p=0;
		ArrayList<TValue> t1 = new ArrayList<TValue>(); //record the values comes from the upper and lower disk
		
		ArrayList<Sensor> upper = new ArrayList<Sensor>();
		ArrayList<Sensor> lower = new ArrayList<Sensor>();
		for (int i =0; i<arr_sen.size();i++){		//check all the sensors to determine which can be added 
			Sensor s = arr_sen.get(i);
			double sx= s.getX();
			double sy= s.getY();
			if (Math.sqrt((x-sx)*(x-sx)+(y-sy)*(y-sy))<=1){
				if(sy>=y1){
					upper.add(s);		// add to upper disks
				}
				else if(sy<=y2){
					lower.add(s);		//add to lower disks
				}
				else {
					System.out.println("Error! the sensor is in the trip");
				}
			}
			
		}
		upper.add(upInfinit);
		lower.add(lowInfinit); // add lower infinity
		
		for (int i=0;i<upper.size();i++){
			Sensor s1 = upper.get(i);
			for (int j=0;j<lower.size();j++){
				Sensor s2 = lower.get(j);
				if(s1.getCost()!=0 || s2.getCost()!= 0 ){
					 t1.add(new TValue(s1,s2,s1.getCost()+s2.getCost(),p));
				}
			}
		}
		tar_arr.add(t1);
	}
	
	/*
	 * method to check whether (Ui,Li) domiante another (Ui-1,Li-1)
	 */
	public static boolean dominate(TValue v1, TValue v2,Target t){
		
		/*
		 *  check intersection points
		 */
		boolean upper_doma = false;
		boolean lower_doma = false;
		
		double x = t.getX();
		double y = t.getY();
		double up_insec =0;
		double lp_insec =0;
		boolean up_inter = true;   //boolean to notify whether the previous chosen upper disk intersect with X=tx
		boolean lp_inter = true;	 //boolean to notify whether the previous chosen lower disk intersect with X=tx
		// calculate intersection for previous chosen sensor
		double up_sx= v2.getUp().getX();
		double up_sy= v2.getUp().getY();
		if(Math.sqrt((x-up_sx)*(x-up_sx)+(y-up_sy)*(y-up_sy))<=1){
			up_insec = up_sy- Math.sqrt(1-(up_sx-x)*(up_sx-x)); // upper disk intersection
		}
		else {
			up_inter = false;
		}
		double lp_sx = v2.getLo().getX();
		double lp_sy = v2.getLo().getY();
		if(Math.sqrt((x-lp_sx)*(x-lp_sx)+(y-lp_sy)*(y-lp_sy))<=1){
			lp_insec = lp_sy + Math.sqrt(1-(lp_sx-x)*(lp_sx-x)); // upper disk intersection
		}
		else {
			lp_inter =false;
		}
		/*
		 * check whether dominate other disks
		 */
		double sy = v1.getUp().getY();
		double sx = v1.getUp().getX();
		if(up_inter){
			double u_inter = sy- Math.sqrt(1-(sx-x)*(sx-x));
			if(u_inter < up_insec){	// if the intersection point is lower than previous then add this sensor
				upper_doma=true;
			}
			else if(u_inter == up_insec && sx <= up_sx  ){	// if the intersection is the same, the sensor locates on the left of previous, add
				upper_doma=true;
			}
		}//end if
		else{
			upper_doma=true;		// add to upper disks
		} //end else
		/*
		 * check lower disk whether dominate previous lower disk
		 */
		
		double syl = v1.getLo().getY();
		double sxl = v1.getLo().getX();
		if(lp_inter){
			double l_inter = syl + Math.sqrt(1-(sxl-x)*(sxl-x));
			if(l_inter > lp_insec ){
				lower_doma = true;
			}
			else if (l_inter == lp_insec && sxl <= lp_sx){
				lower_doma = true;
			}
		}//end if
		else{
			lower_doma = true; //add to lower disks
		}//end else
	
		
		if(upper_doma && lower_doma){
			
			return true;
		}
		return false;
	}
	
	/*
	 * when the number of target is bigger than 2
	 */
	public static void findOPTi(Target t, int cu){  // find the opti
		double x = t.getX();
		double y = t.getY();
		
		ArrayList<TValue> temp = new ArrayList<TValue>(); //record the values comes from the upper and lower disk
		ArrayList<TValue> ti = new ArrayList<TValue>(); //record the values for the current target
		
		//boolean hasExist = false;  //check whether this target has been covered by previous chosen sensors
		ArrayList<Sensor> upper = new ArrayList<Sensor>();
		ArrayList<Sensor> lower = new ArrayList<Sensor>();
		
		
		for (int i =0; i<arr_sen.size();i++){		//check all the sensors to determine which can be added 
			Sensor s = arr_sen.get(i);
			double sx= s.getX();
			double sy= s.getY();
			if (Math.sqrt((x-sx)*(x-sx)+(y-sy)*(y-sy))<=1){
				if(sy>=y1){
					upper.add(s);		// add to upper disks
				}
				else if(sy<=y2){
					lower.add(s);		//add to lower disks
				}
				else {
					System.out.println("Error! the sensor is in the trip");
				}
			}
			
		}
		upper.add(upInfinit);
		lower.add(lowInfinit); // add lower infinity
		int p=0;
		for (int i=0;i<upper.size();i++){
			Sensor s1 = upper.get(i);
			for (int j=0;j<lower.size();j++){
				Sensor s2 = lower.get(j);
				if(s1.getCost()!=0 || s2.getCost()!= 0 ){
					 temp.add(new TValue(s1,s2,s1.getCost()+s2.getCost(),p));
				}
			}
		}
		
		ArrayList<TValue> previous = tar_arr.get(cu-1);
		
		int m=0;
		int min=0;
		int best=0;
		
		
		for(int i=0; i<temp.size();i++){
			boolean first =true;
			boolean hasDoma =false;
			for(int j=0; j<previous.size();j++){
				if(dominate(temp.get(i),previous.get(j),t)){
					hasDoma=true;
					int n= previous.get(j).getCost();
					if(temp.get(i).getUp().getName().equals(previous.get(j).getUp().getName())){
						n=n-previous.get(j).getUp().getCost();
					}
					if(temp.get(i).getLo().getName().equals(previous.get(j).getLo().getName())){
						n=n- previous.get(j).getLo().getCost();
					}
					min = n + temp.get(i).getUp().getCost()+temp.get(i).getLo().getCost();
					if(first ){
						best =min;
						m=j;
						first =false;
					}
					if(min<best){
						best = min;
						m =j;   // here to record the j
					}
						
				}
			}
			if( hasDoma ){
				ti.add(new TValue(temp.get(i).getUp(),temp.get(i).getLo(),best,m));
			}
	
		}
		
		tar_arr.add(ti);				
	}
	
	
	/*
	 * find the minimum cost  and the sensors of S'subset 
	 */
	
	public static void finalMin(ArrayList<ArrayList<TValue>> arr){
		int i = arr.size()-1;
		ArrayList<TValue> lastone = arr.get(i);
		int j=0;
		int minCost = 0;
		int current=0;
		 for(int m=0;m<lastone.size();m++){
			 current = lastone.get(m).getCost();
			 if(m==0){
				 minCost =current;
			 }
			 if(current<minCost){
				 minCost = current;
				 j=m;
			 }
		 }
		 System.out.println("the minimum cost should be: "+ minCost);
		 /*
		  * find the subset of sensors
		  */
		 for(int n=arr.size()-1;n>=0;n--){
			 Sensor su = arr.get(n).get(j).getUp();
			 Sensor sl = arr.get(n).get(j).getLo();
			 if(!su.getName().equals("su") && !optStack.contains(su) ){
				 optStack.push(su); 
			 }
			 if(!sl.getName().equals("sl") && !optStack.contains(sl)){
				 optStack.push(sl);
			 }
			 j=arr.get(n).get(j).getPos();
		 }
		 System.out.println();
		 System.out.println("the subset of S contains: ");
		 for(Sensor s:optStack){
			 System.out.println(s.getName());
		 }
		
	}
	
	/*
	 * ________________________________________________________________________________________
	 * main method staring here
	 */
	public static void main(String[] args) throws Exception{
		
		
		
		System.out.println("Please input test file !");
		Scanner scann = new Scanner(System.in);
		String input_file;
		input_file = scann.nextLine();
		try{
		FileReader input = new FileReader(input_file); 
		Scanner scan = new Scanner(input);
		
		while (scan.hasNext()){ // add objects to array list
			String new_line = scan.nextLine();
			String[] token = new_line.split(" ");
			if(new_line.startsWith("s")){
				arr_sen.add(new Sensor(token[0], Double.parseDouble(token[1]),Double.parseDouble(token[2]), Integer.parseInt(token[3])));
			}
			else if(new_line.startsWith("t")){
				arr_tar.add(new Target(token[0], Double.parseDouble(token[1]),Double.parseDouble(token[2])));
			}
			else if (token[0].equals("y1")){
				y1 = Double.parseDouble(token[1]);
			}
			else
				y2=Double.parseDouble(token[1]);
			
		}
		}
		catch(FileNotFoundException e){
			System.out.println("sry your file cannot be found");
			
		} //finishing storing the file
		
		// sort targets by their X-coordinate
		SortbyX sor = new SortbyX();
		Collections.sort(arr_tar,sor);
		
		/*// test sorting 
		for(int i =0; i<arr_tar.size();i++){
			System.out.println(arr_tar.get(i).getName()+" "+arr_tar.get(i).getX());
		}
		*/
		findOPT1(arr_tar.get(0));
		
		for(int i=1;i<arr_tar.size()-1;i++){
			findOPTi(arr_tar.get(i),i);
		}
		
		finalMin(tar_arr);
		

	}
}


