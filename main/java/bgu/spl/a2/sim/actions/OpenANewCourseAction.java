package bgu.spl.a2.sim.actions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class OpenANewCourseAction extends Action<Boolean> {
	private EmptyAction emptyAction = new EmptyAction();
	private ArrayList<Action<Boolean>> emptyActions = new ArrayList<>();
	private String newCourseId;
	private CoursePrivateState newCoursePrivateState;
	
	public OpenANewCourseAction(String newCourseId, List<String> preRequisites, int availableSpaces) {
		this.newCourseId = newCourseId;
		newCoursePrivateState.setAvailableSpots(availableSpaces);
		newCoursePrivateState.setPrerequisites(preRequisites);
		setActionName("Open Course");
	}
	
	@Override
	protected void start() {
		emptyActions.add(emptyAction);
		sendMessage(emptyAction, newCourseId, newCoursePrivateState);
		then(emptyActions, ()->{
			if(!((DepartmentPrivateState) ownerActorState).getCourseList().contains(newCourseId)){
				((DepartmentPrivateState) ownerActorState).getCourseList().add(newCourseId);
			}
			complete(true);
			ownerActorState.addRecord(getActionName());
		});
		
	}

}
