package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class CloseACourseCloseMeAction extends Action<Boolean> {
	
	public CloseACourseCloseMeAction() {
		setActionName("Close A Course Action: Close Me Action");
	}
	@Override
	protected void start() {
		((CoursePrivateState)ownerActorState).setAvailableSpots(-1);
		complete(true);		
	}

}
