package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.*;

public class UnregisterRemoveYourselfAction extends Action<Boolean> {
	String courseToRemove;
	
	public UnregisterRemoveYourselfAction(String courseToRemove) {
		this.courseToRemove = courseToRemove;
		setActionName("Unregister Action: Remove Yourself Action");
	}
	@Override
	protected void start() {
		((StudentPrivateState)ownerActorState).removeGrade(courseToRemove);
		complete(true);		
	}
	
}
