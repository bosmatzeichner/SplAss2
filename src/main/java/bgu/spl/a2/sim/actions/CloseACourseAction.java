package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CloseACourseAction extends Action<Boolean> {
	String courseToClose;
	List<String> studentsToUnRegister;
	private List<Action<?>> actions = new ArrayList<>();
	private List<Action<?>> unregisterActions = new ArrayList<>();

	public CloseACourseAction(String courseToClose) {
		this.courseToClose = courseToClose;
		studentsToUnRegister = ((CoursePrivateState) this.ownerActorState).getRegStudents();

	}

	@Override
	protected void start() {
		CloseACourseCloseMeAction tempAction = new CloseACourseCloseMeAction();
		sendMessage(tempAction, courseToClose, new CoursePrivateState());
		actions.add(tempAction);
		then(actions, ()->{
			studentsToUnRegister = ((CoursePrivateState) this.ownerActorState).getRegStudents();
			for (String studentToUnRegister : studentsToUnRegister) {
				Action<Boolean> unRegister = new UnregisterAction(studentToUnRegister);
				sendMessage(unRegister, studentToUnRegister, new StudentPrivateState());
				unregisterActions.add(unRegister);
			}
			then(unregisterActions, () -> {
				((DepartmentPrivateState) ownerActorState).getCourseList().remove(courseToClose);
				complete(true);
			});
		});
		
		
		
		
	}

}
