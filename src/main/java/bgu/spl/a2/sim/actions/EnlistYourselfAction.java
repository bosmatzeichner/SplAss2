package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.*;

public class EnlistYourselfAction extends Action<Boolean> {
	String courseName;
	int grade;
	
	public EnlistYourselfAction(String courseName, int grade) {
		this.courseName = courseName;
		this.grade = grade;
	}
	@Override
	protected void start() {
		((StudentPrivateState)ownerActorState).getGrades().put(courseName, grade);
		complete(true);
	}

}
