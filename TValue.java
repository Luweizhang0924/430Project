/*
 * group member: Luwei Zhang; Yufan Huang; Xiaotao Wang
 */
package project;

public class TValue {
	Sensor upper;
	Sensor lower;
	int cost;
	int pos;
	public TValue(Sensor u, Sensor l, int co, int p){
		upper = u;
		lower =l;
		cost = co;
		pos =p;
		
	}
	public Sensor getUp(){
		return upper;
	}
	
	public Sensor getLo(){
		return lower;
	}
	
	public int getCost(){
		return cost;
	}
	public int getPos(){
		return pos;
	}

}
