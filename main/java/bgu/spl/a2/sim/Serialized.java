package bgu.spl.a2.sim;


import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;

import bgu.spl.a2.sim.privateStates.*;


public class Serialized {
	@SerializedName("threads")
	private int threads;
	public int getThreads() {
		return threads;
	}
	// --------------------------------------- COMPUTER -------------------------------------------------
	@SerializedName("Computers")
	private ComputerSerialized[] computerSerialized;
	public ComputerSerialized[] getComputerSerialized() {
		return computerSerialized;
	}
	public class ComputerSerialized{
		@SerializedName("Type")
		private String computerType;
		@SerializedName("Sig Fail")
		private long failSig;
		@SerializedName("Sig Success")
		private long successSig;

		public ComputerSerialized(String type, long fail, long success){
			computerType = type;
			failSig = fail;
			successSig = success;
		}

		public String getType(){
			return computerType;
		}
		public long getFail(){
			return failSig;
		}
		public long getSuccess(){
			return successSig;
		}

	}
	// --------------------------------------- PHASES ---------------------------------------------------
	@SerializedName("Phase 1")
	public ActionSerialized[] phaseA;
	@SerializedName("Phase 2")
	public ActionSerialized[] phaseB;
	@SerializedName("Phase 3")
	public ActionSerialized[] phaseC;
/*
	public void phaseToString(ActionSerialized[] phase){
		for(ActionSerialized i : phase){
			System.out.println("*ACTION NAME*" + i.actionName);
			System.out.println("*DEPARTMENT NAME*" + i.departmentName);
			System.out.println("*SPACE*" + i.courseCapacity);
			System.out.println("*PREREQUISITIES*" + i.prerequisites.toString());
			System.out.println("*COURSE NAME*" + i.courseName);
			//System.out.println(i.getActualAction().getActionName());
			
		}
	}
	*/
	
	// ----------------------------------  PHASES: GETTERS ----------------------------------------------
	public ActionSerialized[] getPhaseA() {
		return phaseA;
	}
	public ActionSerialized[] getPhaseB() {
		return phaseB;
	}
	public ActionSerialized[] getPhaseC() {
		return phaseC;
	}
	// ---------------------------------------------------------------------------------------------------
	public class ActionSerialized{
		private Action<?> actualAction;
		
		public Action<?> getActualAction() {
			switch (actionName) {
			case "Open Course":{
				this.designatedActor = departmentName;
				this.designatedActorPrivateState = new DepartmentPrivateState();
				actualAction = new OpenANewCourseAction(courseName, Arrays.asList(prerequisites), courseCapacity);
				break;
			}
			case "Add Student":{
				this.designatedActor = departmentName;
				this.designatedActorPrivateState = new DepartmentPrivateState();
				actualAction = new AddStudentAction(student);
				break;
			}
			case "Participate In Course":{
				this.designatedActor = courseName;
				this.designatedActorPrivateState = new CoursePrivateState();
				
				int grade;
				if(grades[0] == "-") grade = -1;
				else grade = Integer.parseInt(grades[0]);
				
				actualAction = new ParticipatingInCourseAction(student, grade);
				break;
			}
			case "Add Spaces":{
				this.designatedActor = courseName;
				this.designatedActorPrivateState = new CoursePrivateState();
				actualAction = new OpeningNewPlacesInCourseAction(moreToCourseCapacity);
				break;
			}
			case "Register With Preferences":{
				this.designatedActor = student;
				this.designatedActorPrivateState = new StudentPrivateState();
				actualAction = new RegisterWithPreferencesAction(preferences, grades);
				break;
			}
			case "Close Course":{
				this.designatedActor = departmentName;
				this.designatedActorPrivateState = new DepartmentPrivateState();
				actualAction = new CloseACourseAction(courseName);
				break;
			}
			case "Unregister":{
				this.designatedActor = courseName;
				this.designatedActorPrivateState = new CoursePrivateState();
				actualAction = new UnregisterAction(student);
				break;
			}
			case "Administrative Check":{
				this.designatedActor = departmentName;
				this.designatedActorPrivateState = new DepartmentPrivateState();
				actualAction = new CheckAdministrativeObligationsAction(computerType, conditions, students);
				break;
			}
			default:
				break;
			}
			actualAction.setActionName(actionName);
			return actualAction;
		}
		
		// ---------------
		private String designatedActor;
		public String getDesignatedActor() {
			return designatedActor;
		}
		// ---------------
		private PrivateState designatedActorPrivateState;
		public PrivateState getDesignatedActorPrivateState() {
			return designatedActorPrivateState;
		}
		// ---------------
		@SerializedName("Action")
		String actionName;
		@SerializedName("Department")
		String departmentName;		
		@SerializedName("Course")
		String courseName;
		@SerializedName("Space")
		int courseCapacity;
		@SerializedName("Prerequisites")
		String[] prerequisites;
		@SerializedName("Student")
		String student;
		@SerializedName("Students")
		String[] students;
		@SerializedName("Grade")
		String[] grades;
		@SerializedName("Number")
		int moreToCourseCapacity;
		@SerializedName("Preferences")
		String[] preferences;
		@SerializedName("Conditions")
		String[] conditions;
		@SerializedName("Computer")
		String computerType;
	}

}
