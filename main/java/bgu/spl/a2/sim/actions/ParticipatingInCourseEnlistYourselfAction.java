package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.*;

public class ParticipatingInCourseEnlistYourselfAction extends Action<Boolean> {
	String courseName;
	int grade;
	
	public ParticipatingInCourseEnlistYourselfAction(String courseName, int grade) {
		this.courseName = courseName;
		this.grade = grade;
		setActionName("Participating In Course Action: Enlist Yourself Action");
	}
	@Override
	protected void start() {
		((StudentPrivateState)ownerActorState).addGrade(courseName, grade);
		complete(true);
	}

}
