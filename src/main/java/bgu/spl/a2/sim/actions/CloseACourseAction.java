package bgu.spl.a2.sim.actions;

import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class CloseACourseAction<R> extends Action<R> {
	String courseToClose;
	List<String> studentsToUnRegister;
	private List<Action<?>> actions;

	public CloseACourseAction(String courseToClose) {
		this.courseToClose = courseToClose;
		studentsToUnRegister = ((CoursePrivateState) this.ownerActorState).getRegStudents();
		for (String studentToUnRegister : studentsToUnRegister) {
			Action<Boolean> unRegister = new UnregisterAction<Boolean>(studentToUnRegister);
			actorThreadPool.submit(unRegister, courseToClose, actorThreadPool.getPrivateState(courseToClose));
			actions.add(unRegister);
		}

	}

	@Override
	protected void start() {
		((CoursePrivateState)actorThreadPool.getPrivateState(courseToClose)).setAvailableSpots(-1);
		then(actions, ()->{
			((DepartmentPrivateState) ownerActorState).getCourseList().remove(courseToClose);
			
		});
	}

}
