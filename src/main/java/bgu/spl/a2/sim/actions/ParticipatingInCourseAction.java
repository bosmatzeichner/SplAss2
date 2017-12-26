package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseAction extends Action<Boolean> {

	private String studentName;
	private int grade;
	private List<Action<?>> actions = new ArrayList<>();
	public int availableSpots;

	public ParticipatingInCourseAction(String studentName, int grade) {
		this.studentName = studentName;
		this.grade = grade;

	}

	@Override
	protected void start() {
		availableSpots = ((CoursePrivateState) ownerActorState).getAvailableSpots();

		if (availableSpots > 0) {

			Action<Integer> checkIfCompatible = new CheckIfCompatibleAction(
					((CoursePrivateState) ownerActorState).getPrequisites(), availableSpots, ownerActorName);

			this.sendMessage(checkIfCompatible, studentName, new StudentPrivateState());

			actions.add(checkIfCompatible);
			then(actions, () -> {
				availableSpots = ((CoursePrivateState) ownerActorState).getAvailableSpots();
				if (availableSpots > 0) {
					if (((Integer) actions.get(0).getResult().get()) == 1) {
						((CoursePrivateState) ownerActorState).registerAndUpdateAvailables(); // Registered++,
						// Available--
						// add the student to course
						((CoursePrivateState) ownerActorState).getRegStudents().add(this.ownerActorName);
						EnlistYourselfAction enlistYourself = new EnlistYourselfAction(ownerActorName, availableSpots);
						sendMessage(enlistYourself, studentName, new StudentPrivateState());
						
						actions.add(enlistYourself);
						
						then(actions, ()->{
							complete(true);
						});
					} 
				}
			});			
		}
	}
}
