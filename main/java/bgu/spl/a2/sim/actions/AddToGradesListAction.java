package bgu.spl.a2.sim.actions;

import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddToGradesListAction extends Action<Boolean> {
	int grade;
	public AddToGradesListAction(List<String> prerequisites, int grade) {
		this.grade = grade;
	}

	@Override
	protected void start() {
		((StudentPrivateState)this.ownerActorState).getGrades().put(ownerActorName, grade);
		complete(true);
	}

}
