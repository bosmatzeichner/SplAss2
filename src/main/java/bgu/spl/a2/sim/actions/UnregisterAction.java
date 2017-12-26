package bgu.spl.a2.sim.actions;

import java.util.HashMap;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class UnregisterAction extends Action<Boolean> {
	private String studentToUnregister;
	private StudentPrivateState toUnRegisterState;
	private HashMap<String, Integer> gradesList;

	public UnregisterAction(String studentToUnregister) {
		this.studentToUnregister = studentToUnregister;
		toUnRegisterState = (StudentPrivateState) actorThreadPool.getPrivateState(studentToUnregister);
		gradesList = toUnRegisterState.getGrades();

	}

	@Override
	protected void start() {

		if (((CoursePrivateState) ownerActorState).getRegStudents().contains(studentToUnregister)) {
			((CoursePrivateState) ownerActorState).unRegisterAndUpdateAvailables();

			

			// remove course from grades list
			gradesList.remove(ownerActorName); // Deleting the name of the course from the STUDENTS grades list
			((CoursePrivateState) ownerActorState).getRegStudents().remove(studentToUnregister);
			
			complete(true);
		}

	}

}
