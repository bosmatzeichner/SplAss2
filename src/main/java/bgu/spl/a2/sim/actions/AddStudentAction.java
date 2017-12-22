package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudentAction<R> extends Action<R> {

	private Action<Integer> emptyAction;
	private String newStudentId;
	private StudentPrivateState newStudentPrivateState = new StudentPrivateState();

	public AddStudentAction(String newStudentId) {
		this.newStudentId = newStudentId;
		setActionName("AddStudentAction");
	}

	@Override
	protected void start() {
		this.actorThreadPool.submit(emptyAction, newStudentId, newStudentPrivateState);
		((DepartmentPrivateState) ownerActorState).getStudentList().add(newStudentId);
	}
}
