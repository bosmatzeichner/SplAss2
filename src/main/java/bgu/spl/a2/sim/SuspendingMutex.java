package bgu.spl.a2.sim;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.a2.Promise;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	Computer computer;
	AtomicBoolean isLocked = new AtomicBoolean(false);
	private LinkedList<Promise<Computer>> promisesQueue = new LinkedList<Promise<Computer>>();
	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		this.computer = computer;
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediately
	 * 
	 * @return a promise for the requested computer
	 */
	
	public Computer getComputer() {
		return computer;
	}
	public Promise<Computer> down(){
		Promise<Computer> tempPromise = new Promise<Computer>();
		if(isLocked.compareAndSet(false, true)){
			tempPromise.resolve(computer);
			return tempPromise;
		}
		
		promisesQueue.addLast(tempPromise);
		return tempPromise;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){
		if(promisesQueue.getFirst() != null){
			promisesQueue.pollFirst().resolve(computer);
		}
		else{
			isLocked.set(false);
		}
	}
}
