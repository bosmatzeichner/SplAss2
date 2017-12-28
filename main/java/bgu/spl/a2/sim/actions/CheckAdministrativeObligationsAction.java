package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CheckAdministrativeObligationsAction extends Action<Boolean> {

	/*
	 * Behavior: The department's secretary have to allocate one of the
	 * computers available in the warehouse, and check for each student if he
	 * meets some administrative obligations. The computer generates a signature
	 * and save it in the private state of the students.
	 * 
	 * Actor: Must be initially submitted to the department's actor.
	 */

	private String[] administrativeObligations;
	private String[] students;
	private String computerType;
	private List<Action<?>> actions = new ArrayList<>();

	public CheckAdministrativeObligationsAction(String computerType, String[] administrativeObligations,
			String[] students) {
		this.computerType = computerType;
		this.administrativeObligations = administrativeObligations;
		this.students = students;
		

	}

	@Override
	protected void start() {
		ownerActorState.addRecord(getActionName());
		SuspendingMutex computerMutex = Simulator.myWarehouse.getComputer(computerType);
		Promise<Computer> computerPromise = computerMutex.down();
		computerPromise.subscribe(() -> {
		//	System.out.println("we are here --------");
			for (String student : students) {
				if (((DepartmentPrivateState) ownerActorState).getStudentList().contains(student)) {
					CheckAdministrativeObligationsCheckAndSignAction tempCheckAndSignAction = new CheckAdministrativeObligationsCheckAndSignAction(computerMutex.getComputer(),
							administrativeObligations);
					
					sendMessage(tempCheckAndSignAction, student, new StudentPrivateState());
					actions.add(tempCheckAndSignAction);
				}
			}
			then(actions, () -> {
				computerMutex.up();			
				complete(true);
			});
		});
	}
}
