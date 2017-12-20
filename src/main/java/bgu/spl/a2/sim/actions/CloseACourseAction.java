package bgu.spl.a2.sim.actions;

import java.util.Collection;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CloseACourseAction<R> extends Action<R> {
	String courseToClose;
	List<String> studentsToUnRegister;
	private Collection<? extends Action<?>> actions;

	public CloseACourseAction(String courseToClose) {
		this.courseToClose = courseToClose;
		studentsToUnRegister = ((CoursePrivateState) this.ownerActorState).getRegStudents();
		for (String studentToUnRegsiter : studentsToUnRegister) {
			Action<Boolean> unRegister = new UnregisterAction<Boolean>(ownerActorName);
		}

	}

	@Override
	protected void start() {

		then(actions, callback);
	}

}
