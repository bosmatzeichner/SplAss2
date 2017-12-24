package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlacesInCourseAction<R> extends Action<R> {
	int howMuchMoreAvailablePlaces;
	public OpeningNewPlacesInCourseAction(int howMuchMoreAvailablePlaces) {
		this.howMuchMoreAvailablePlaces = howMuchMoreAvailablePlaces;
	}
	@Override
	protected void start() {
		((CoursePrivateState) ownerActorState).addMoreAvailableSpaces(howMuchMoreAvailablePlaces);
	}

}
