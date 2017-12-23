package bgu.spl.a2.sim.actions;

import java.util.List;
import java.util.Map;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CheckAdministrativeObligationsAction<R> extends Action<R> {

	/*
	 * Behavior: The department's secretary have to allocate one of the
	 * computers available in the warehouse, and check for each student if he
	 * meets some administrative obligations. The computer generates a signature
	 * and save it in the private state of the students.
	 * 
	 * Actor: Must be initially submitted to the department's actor.
	 */

	private List<String> administrativeObligations;
	private Computer computer;
	private List<String> students;

	public CheckAdministrativeObligationsAction(Computer computer, List<String> administrativeObligations,
			List<String> students) {
		this.computer = computer;
		this.administrativeObligations = administrativeObligations;
		this.students = students;

	}

	@Override
	protected void start() {
		Promise<Computer> computerPromise = computer.mutex.down();
		computerPromise.subscribe(() -> {
			for (String student : students) {
				Map<String, Integer> coursesGrades = ((StudentPrivateState) actorThreadPool.getPrivateState(student))
						.getGrades();
				computer.checkAndSign(administrativeObligations, coursesGrades);
			}
			computer.mutex.up();
		});

	}
}
