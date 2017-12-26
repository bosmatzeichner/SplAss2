package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudentAction extends Action<Boolean> {

	private CreateNewActorAction emptyAction = new CreateNewActorAction();
	
	private String newStudentId;
	private StudentPrivateState newStudentPrivateState = new StudentPrivateState();

	public AddStudentAction(String newStudentId) {
		this.newStudentId = newStudentId;
	}

	@Override
	protected void start() {
		sendMessage(emptyAction, newStudentId, newStudentPrivateState);
		if(!((DepartmentPrivateState) ownerActorState).getStudentList().contains(newStudentId)){
			((DepartmentPrivateState) ownerActorState).getStudentList().add(newStudentId);
		}
		
		
		complete(true);
	}
}
