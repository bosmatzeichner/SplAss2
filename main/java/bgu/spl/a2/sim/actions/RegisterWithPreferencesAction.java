package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class RegisterWithPreferencesAction extends Action<Boolean> {
	public String[] preferedCourses;
	public String[] grades;
	
	public RegisterWithPreferencesAction(String[] preferedCourses, String[] grades) {
		this.preferedCourses = preferedCourses;
		this.grades = grades;
	}
	@Override
	protected void start() {
		CoursePrivateState tempCourse;
		for(int i = 0; i < preferedCourses.length; i++){
			tempCourse = (CoursePrivateState) actorThreadPool.getPrivateState(preferedCourses[i]);
			
			int availableSpots = tempCourse.getAvailableSpots();
			
			if (availableSpots > 0) {
				tempCourse.registerAndUpdateAvailables(); // Registered++, Available--
				
				// add the student to course
				tempCourse.getRegStudents().add(this.ownerActorName); // this.ownerActorName = Student's name

				// add the course to grades list
				((StudentPrivateState)ownerActorState).getGrades().put(ownerActorName, Integer.parseInt(grades[i]));
				
				complete(true);
				ownerActorState.addRecord("Register With Preferences");
				return;
			}
		}
		
	}
	
}
