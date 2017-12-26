package bgu.spl.a2.sim.actions;

import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseCheckIfCompatibleAction extends Action<Integer> {
	int grade;
	List<String> prerequisites;
	String courseName;
	
	public ParticipatingInCourseCheckIfCompatibleAction(List<String> prerequisites, int grade, String courseName) {
		this.grade = grade;
		this.prerequisites = prerequisites;
		this.courseName = courseName;
		setActionName("Participating In Course Action: Check If Compatible Action");
	}

	@Override
	protected void start() {
		boolean shouldEnlist = true;
		for(String prerequisite : prerequisites){
			if(!((StudentPrivateState)this.ownerActorState).getGrades().containsKey(prerequisite)){
				shouldEnlist = false;
				break;
			}
		}
		if(shouldEnlist) {
			((StudentPrivateState)this.ownerActorState).getGrades().put(courseName, grade);
			complete(1);
		}
		else complete(-1);
		
	}

}
