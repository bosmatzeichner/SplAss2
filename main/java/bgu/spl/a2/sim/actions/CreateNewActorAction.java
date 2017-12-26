package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;

public class CreateNewActorAction extends Action<Boolean> {
	
	public CreateNewActorAction(){
		actionName.set("Create New Actor Action");
	}
	@Override
	protected void start() {
		complete(true);		
	}

}
