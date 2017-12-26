package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class CheckAndSignAction extends Action<Boolean> {
	long sig;
	Computer computer;
	String[] administrativeObligations;
	
	public CheckAndSignAction(Computer computer, String[] administrativeObligations) {
		this.computer = computer;
		this.administrativeObligations = administrativeObligations;
	}	
	@Override
	protected void start() {
		sig = computer.checkAndSign(administrativeObligations, ((StudentPrivateState)ownerActorState).getGrades());
		((StudentPrivateState)ownerActorState).setSignature(sig);
	}
}
