package bgu.spl.a2.sim;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.AddStudentAction;
import bgu.spl.a2.sim.actions.OpenANewCourseAction;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class Serialized {
	@SerializedName("threads")
	private int threads;
	public int getThreads() {
		return threads;
	}
	// Computer
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

	
	// --------------------- PHASES ----------------------
	@SerializedName("Phase 1")
	public ActionSerialized[] phaseA;
	@SerializedName("Phase 2")
	public ActionSerialized[] phaseB;
	@SerializedName("Phase 3")
	public ActionSerialized[] phaseC;

	// --------------------- PHASES: GETTERS -------------
	public ActionSerialized[] getPhaseA() {
		return phaseA;
	}
	public ActionSerialized[] getPhaseB() {
		return phaseB;
	}
	public ActionSerialized[] getPhaseC() {
		return phaseC;
	}
	// ---------------------------------------------------
	public class ActionSerialized{
		private Action<?> actualAction;
		
		public Action<?> getActualAction() {
			return actualAction;
		}
		
		private String designatedActor;

		public String getDesignatedActor() {
			return designatedActor;
		}
		
		private PrivateState designatedActorPrivateState;
		
		public PrivateState getDesignatedActorPrivateState() {
			return designatedActorPrivateState;
		}
		
		
		
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

		public ActionSerialized(String actionName, String departmentName, String courseName, String[] prerequisites, int courseCapacity){
			this.actionName = actionName;
			this.designatedActor = departmentName;
			this.designatedActorPrivateState = new DepartmentPrivateState();
			actualAction = new OpenANewCourseAction(courseName, Arrays.asList(prerequisites), courseCapacity);
		}
		public ActionSerialized(String actionName, String departmentName, String student){
			this.actionName = actionName;
			this.designatedActor = departmentName;
			this.designatedActorPrivateState = new DepartmentPrivateState();
			actualAction = new AddStudentAction(student);
		}		

	}


}
