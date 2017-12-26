package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;



public class Computer {
	
	String computerType;
	
	long failSig;

	long successSig;
	
	public Computer(String computerType) {
		this.computerType = computerType;
	}
	
	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 */
	public long checkAndSign(String[] courses, Map<String, Integer> coursesGrades){
		for(String i: courses){
			if(!coursesGrades.containsKey(i) || coursesGrades.get(i) < 56){
				return failSig;
			}
		}
		
		return successSig;
	}
}
