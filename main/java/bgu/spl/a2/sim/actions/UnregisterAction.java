package bgu.spl.a2.sim.actions;


import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class UnregisterAction extends Action<Boolean> {
	private String studentToUnregister;


	public UnregisterAction(String studentToUnregister) {
		this.studentToUnregister = studentToUnregister;
		
	}

	@Override
	protected void start() {
		ownerActorState.addRecord(getActionName());
		if (((CoursePrivateState) ownerActorState).getRegStudents().contains(studentToUnregister)) {
			
			((CoursePrivateState) ownerActorState).getRegStudents().remove(studentToUnregister);
			((CoursePrivateState) ownerActorState).unRegisterAndUpdateAvailables();
			
			UnregisterRemoveYourselfAction removeyourself = new UnregisterRemoveYourselfAction(ownerActorName);
			sendMessage(removeyourself, studentToUnregister, new StudentPrivateState());
			actions.clear();
			actions.add(removeyourself);
			then(actions, ()->{				
				complete(true);				
			});	
		}else{
			sendMessage(this, ownerActorName, ownerActorState);
		}
	}

}
