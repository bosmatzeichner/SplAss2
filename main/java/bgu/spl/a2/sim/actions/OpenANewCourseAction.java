package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpenANewCourseAction extends Action<Boolean> {
	private CreateNewActorAction emptyAction = new CreateNewActorAction();
	private String newCourseId;
	private CoursePrivateState newCoursePrivateState = new CoursePrivateState();

	public OpenANewCourseAction(String newCourseId, List<String> preRequisites, int availableSpaces) {
		this.newCourseId = newCourseId;
		System.out.println("NEW COURSE ID: " + newCourseId);
		newCoursePrivateState.setAvailableSpots(availableSpaces);
		newCoursePrivateState.setPrerequisites(preRequisites);
	}

	@Override
	protected void start() {
		sendMessage(emptyAction, newCourseId, newCoursePrivateState);
		if (!((DepartmentPrivateState) ownerActorState).getCourseList().contains(newCourseId)) {
			((DepartmentPrivateState) ownerActorState).getCourseList().add(newCourseId);
		}
		
		complete(true);
	}
}
