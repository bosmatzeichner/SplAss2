package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.Arrays;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.*;

public class RegisterWithPreferencesAction extends Action<Boolean> {
	public ArrayList<String> preferedCourses;
	public ArrayList<String> grades;
	public ParticipatingInCourseAction tempAction;

	public RegisterWithPreferencesAction(String[] preferedCourses, String[] grades) {
		this.preferedCourses = new ArrayList<String>(Arrays.asList(preferedCourses));
		this.grades = new ArrayList<String>(Arrays.asList(grades));
	}
	
	public RegisterWithPreferencesAction(ArrayList<String> preferedCourses, ArrayList<String> grades) {
		this.preferedCourses = preferedCourses;
		this.grades = grades;
}
@Override
	protected void start() {
	/*
	 * The logic is:
	 * We try to register to the first course in the 'Prefered courses list' which has 1->n prefered courses
	 * If it isn't possible, create a new RegisterWithPreferences action, only now with the rest of the list
	 */
		if(preferedCourses.size() > 0){
			ParticipatingInCourseAction tempAction = new ParticipatingInCourseAction(ownerActorName, Integer.parseInt(grades.get(0)));
			sendMessage(tempAction, preferedCourses.get(0), new CoursePrivateState());		
			preferedCourses.remove(0);
			grades.remove(0);
			actions.add(tempAction);
			then(actions, ()->{
				if(tempAction.getResult().get() == 1){
					complete(true);
				}
				else
				{
					RegisterWithPreferencesAction keepOnTryingToRegister = new RegisterWithPreferencesAction(preferedCourses, grades);
					sendMessage(keepOnTryingToRegister, preferedCourses.get(0), new CoursePrivateState());
				}
			});
		}
		else complete(true);
	}

}
