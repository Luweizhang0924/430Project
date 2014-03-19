/*
 * group member: Luwei Zhang; Yufan Huang; Xiaotao Wang
 */
package project;

public class Sensor {
	private String name;
	private double xcord;
	private double ycord;
	private int cost;
	public Sensor(String str, double x, double y, int cost){
		name = str;
		xcord = x;
		ycord =y;
		this.cost = cost;
	}

	public String getName(){
		return name;
	}
	public double getX(){
		return xcord;
	}
	
	public double getY(){
		return ycord;
	}
	
	public int getCost(){
		return cost;
	}
}
