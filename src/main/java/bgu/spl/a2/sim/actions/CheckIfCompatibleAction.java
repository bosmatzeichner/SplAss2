package bgu.spl.a2.sim.actions;

import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CheckIfCompatibleAction extends Action<Integer> {
	int grade;
	List<String> prerequisites;
	String courseName;
	
	public CheckIfCompatibleAction(List<String> prerequisites, int grade, String courseName) {
		this.grade = grade;
		this.prerequisites = prerequisites;
		this.courseName = courseName;
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
