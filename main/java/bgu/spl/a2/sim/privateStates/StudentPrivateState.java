package bgu.spl.a2.sim.privateStates;

import java.util.HashMap;

import bgu.spl.a2.PrivateState;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState{

	private HashMap<String, Integer> grades;
	private long signature;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
		grades = new HashMap<String, Integer>();
	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}
	
	public void setSignature(long signature) {
		this.signature = signature;
	}
	
	public void addGrade(String course, int grade){
		grades.put(course, grade);
	}
	
	public void removeGrade(String course){
		grades.remove(course);
	}
}
