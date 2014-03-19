/*
 * group member: Luwei Zhang; Yufan Huang; Xiaotao Wang
 */
package project;

import java.util.Comparator;

public class SortbyX implements Comparator<Object>{
	
	public int compare(Object obj1, Object obj2){
		Target target1 = (Target)obj1;
		Target target2 = (Target)obj2;
		if(target1.getX()>target2.getX())
			return 1;
		if(target1.getX()<target2.getX())
			return -1;
		else
			return 0;
		//return target1.getName().compareTo(target2.getName());
	}

}
