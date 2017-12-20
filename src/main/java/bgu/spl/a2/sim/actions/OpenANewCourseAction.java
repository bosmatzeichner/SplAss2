package bgu.spl.a2.sim.actions;


import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpenANewCourseAction<R> extends Action<R> {
	private Action<Integer> emptyAction;
	private String newCourseId;
	private CoursePrivateState newCoursePrivateState;
	
	public OpenANewCourseAction(String newCourseId, List<String> preRequisites, int availableSpaces) {
		this.newCourseId = newCourseId;
		newCoursePrivateState.setAvailableSpots(availableSpaces);
		newCoursePrivateState.setPrerequisites(preRequisites);
		setActionName("OpenNewCourse");
	}
	
	@Override
	protected void start() {
		actorThreadPool.submit(emptyAction, newCourseId, newCoursePrivateState);
		((DepartmentPrivateState) ownerActorState).getCourseList().add(newCourseId);
	}

}
