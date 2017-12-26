package bgu.spl.a2.sim;

import java.util.HashMap;

import bgu.spl.a2.sim.Serialized.ComputerSerialized;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {
	HashMap<String, SuspendingMutex> computersWithTheirMutexes = new HashMap<String, SuspendingMutex>();
	
	public Warehouse(ComputerSerialized[] computers){
		for(ComputerSerialized i : computers){
			addAComputer(i.getSuccess(), i.getFail(), i.getType());
		}
	}
	
	public void addAComputer(long successSignature, long failSignature, String computerType){
		Computer computer = new Computer(computerType);
		computer.successSig = successSignature;
		computer.failSig = failSignature;
		
		SuspendingMutex susMut = new SuspendingMutex(computer);
		
		computersWithTheirMutexes.put(computer.computerType, susMut);
	}
	
	public SuspendingMutex getComputer(String computerType){
		return computersWithTheirMutexes.get(computerType);
	}
	
	
	
}
