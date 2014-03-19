/*
 * class for target
 * group member: Luwei Zhang; Yufan Huang; Xiaotao Wang
 */
package project;

public class Target {
	private String name;
	private double xcord;
	private double ycord;
	public Target(String str, double x, double y){
		name = str;
		xcord = x;
		ycord =y;
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

}
