package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseAction extends Action<Integer> {

	private String studentName;
	private int grade;
	public int availableSpots;

	public ParticipatingInCourseAction(String studentName, int grade) {
		this.studentName = studentName;
		this.grade = grade;

	}

	@Override
	protected void start() {
		ownerActorState.addRecord(getActionName());
		availableSpots = ((CoursePrivateState) ownerActorState).getAvailableSpots();

		if (!((CoursePrivateState) ownerActorState).getRegStudents().contains(studentName)) {
			if (availableSpots > 0) {

				Action<Integer> checkIfCompatible = new ParticipatingInCourseCheckIfCompatibleAction(
						((CoursePrivateState) ownerActorState).getPrequisites(), availableSpots, ownerActorName);

				this.sendMessage(checkIfCompatible, studentName, new StudentPrivateState());

				actions.clear();
				actions.add(checkIfCompatible);
				then(actions, () -> {
					availableSpots = ((CoursePrivateState) ownerActorState).getAvailableSpots();
					if (availableSpots > 0 && ((Integer) actions.get(0).getResult().get()) == 1) {

						((CoursePrivateState) ownerActorState).registerAndUpdateAvailables();
						((CoursePrivateState) ownerActorState).getRegStudents().add(studentName);
						ParticipatingInCourseEnlistYourselfAction enlistYourself = new ParticipatingInCourseEnlistYourselfAction(
								ownerActorName, grade);
						sendMessage(enlistYourself, studentName, new StudentPrivateState());

						actions.clear();
						actions.add(enlistYourself);

						then(actions, () -> {
							complete(1);
						});
					} else {
						complete(-1);
					}
				});
			} else
				complete(-1);
		} else {
			sendMessage(this, ownerActorName, ownerActorState);
		}
	}
}
