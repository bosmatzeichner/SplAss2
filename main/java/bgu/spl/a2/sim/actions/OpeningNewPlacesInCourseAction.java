package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlacesInCourseAction extends Action<Boolean> {
	int howMuchMoreAvailablePlaces;
	
	public OpeningNewPlacesInCourseAction(int howMuchMoreAvailablePlaces) {
		this.howMuchMoreAvailablePlaces = howMuchMoreAvailablePlaces;
		
	}
	@Override
	protected void start() {
		ownerActorState.addRecord(getActionName());
		((CoursePrivateState) ownerActorState).addMoreAvailableSpaces(howMuchMoreAvailablePlaces);
		complete(true);
	}

}
