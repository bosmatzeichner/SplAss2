package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseAction<R> extends Action<R> {
	
	private String studentName;
	private int grade;

	public ParticipatingInCourseAction(String name, int grade) {
		this.studentName = name;
		this.grade = grade;

	}

	@Override
	protected void start() {
		int availableSpots = ((CoursePrivateState) ownerActorState).getAvailableSpots();
		
		if (availableSpots > 0) {
			((CoursePrivateState) ownerActorState).registerAndUpdateAvailables(); // Registered++, Available--
			
			// add the student to course
			((CoursePrivateState) ownerActorState).getRegStudents().add(this.ownerActorName);

			// add the course to grades list
			Action<Boolean> addToGradesListAction = new AddToGradesListAction(((CoursePrivateState) ownerActorState).getPrequisites());
			
			
			this.sendMessage(addToGradesListAction, studentName, actorState);
		}
	}

}
