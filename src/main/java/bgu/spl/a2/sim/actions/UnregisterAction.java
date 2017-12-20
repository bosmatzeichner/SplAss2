package bgu.spl.a2.sim.actions;

import java.util.HashMap;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class UnregisterAction<R> extends Action<R> {
	private String courseToUnRegister;
	private CoursePrivateState toUnRegisterState;
	private HashMap<String, Integer> gradesList = ((StudentPrivateState) this.ownerActorState).getGrades();

	public UnregisterAction(String courseToUnRegister) {
		this.courseToUnRegister = courseToUnRegister;
		toUnRegisterState = (CoursePrivateState) actorThreadPool.getPrivateState(courseToUnRegister);

	}

	@Override
	protected void start() {

		if (gradesList.containsKey(courseToUnRegister)) {
			int availableSpots = ((CoursePrivateState) actorThreadPool.getPrivateState(courseToUnRegister))
					.getAvailableSpots();
			int regStudents = toUnRegisterState.getRegistered();

			// decrease the number of registered students
			toUnRegisterState.setAvailableSpots(availableSpots - 1);

			// increase the number of registered students
			toUnRegisterState.setRegisterd(regStudents + 1);

			// remove course from grades list
			gradesList.remove(courseToUnRegister);
		}

	}

}
