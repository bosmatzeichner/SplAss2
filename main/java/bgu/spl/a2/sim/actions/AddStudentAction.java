package bgu.spl.a2.sim.actions;

import java.util.ArrayList;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudentAction extends Action<Boolean> {

	private EmptyAction emptyAction = new EmptyAction();
	private ArrayList<Action<Boolean>> emptyActions = new ArrayList<>();
	
	private String newStudentId;
	private StudentPrivateState newStudentPrivateState = new StudentPrivateState();

	public AddStudentAction(String newStudentId) {
		this.newStudentId = newStudentId;
		setActionName("Add Student");
	}

	@Override
	protected void start() {
		sendMessage(emptyAction, newStudentId, newStudentPrivateState);
		emptyActions.add(emptyAction);
		then(emptyActions, ()->{
			if(!((DepartmentPrivateState) ownerActorState).getStudentList().contains(newStudentId)){
				((DepartmentPrivateState) ownerActorState).getStudentList().add(newStudentId);
			}
			complete(true);
		});
	}
}
