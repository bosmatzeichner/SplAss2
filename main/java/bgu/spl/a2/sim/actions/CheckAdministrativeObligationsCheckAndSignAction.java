package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CheckAdministrativeObligationsCheckAndSignAction extends Action<Boolean> {
	long sig;
	Computer computer;
	String[] administrativeObligations;
	
	public CheckAdministrativeObligationsCheckAndSignAction(Computer computer, String[] administrativeObligations) {
		this.computer = computer;
		this.administrativeObligations = administrativeObligations;
		setActionName("Check Administrative Obligations Action: Check and sign");
	}	
	@Override
	protected void start() {
		sig = computer.checkAndSign(administrativeObligations, ((StudentPrivateState)ownerActorState).getGrades());
		((StudentPrivateState)ownerActorState).setSignature(sig);
		complete(true);
	}
}
