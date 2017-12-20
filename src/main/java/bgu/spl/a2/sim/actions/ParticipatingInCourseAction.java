package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ParticipatingInCourseAction<R> extends Action<R> {

	private String courseToRegister;
	private CoursePrivateState toRegisterState;
	private int grade;

	public ParticipatingInCourseAction(String courseToRegister, int grade) {
		this.courseToRegister = courseToRegister;
		this.grade = grade;
		toRegisterState = ((CoursePrivateState) actorThreadPool.getPrivateState(courseToRegister));

	}

	@Override
	protected void start() {
		int availableSpots = ((CoursePrivateState) actorThreadPool.getPrivateState(courseToRegister))
				.getAvailableSpots();
		int regStudents = toRegisterState.getRegistered();

		if (availableSpots > 0) {
			// decrease the number of registered students
			toRegisterState.setAvailableSpots(availableSpots - 1);

			// increase the number of registered students
			toRegisterState.setRegisterd(regStudents + 1);

			// add the student to course
			toRegisterState.getRegStudents().add(this.ownerActorName);

			// add the course to grades list
			((StudentPrivateState) ownerActorState).getGrades().put(courseToRegister, grade);
		}
	}

}
