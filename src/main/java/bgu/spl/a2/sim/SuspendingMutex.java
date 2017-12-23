package bgu.spl.a2.sim;

import java.util.LinkedList;

import bgu.spl.a2.Promise;

/**
 * 
 * this class is related to {@link Computer} it indicates if a computer is free
 * or not
 * 
 * Note: this class can be implemented without any synchronization. However,
 * using synchronization will be accepted as long as the implementation is
 * blocking free.
 *
 */
public class SuspendingMutex {

	Computer computer;
	boolean isLocked = false;
	private LinkedList<Promise<Computer>> promisesQueue = new LinkedList<Promise<Computer>>();

	/**
	 * Constructor
	 * 
	 * @param computer
	 */
	public SuspendingMutex(Computer computer) {
		this.computer = computer;
	}

	/**
	 * Computer acquisition procedure Note that this procedure is non-blocking
	 * and should return immediately
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down() {
		// tries to lock the computer, if it succeed it returns a resolved
		// promise (with computer) else it returns not resolved promise and add
		// it to the queue

		Promise<Computer> promise = new Promise<Computer>();
		if (isLocked) {
			promisesQueue.addLast(promise);
			return promise;
		}
		isLocked = true;
		promise.resolve(computer);
		return promise;
	}

	/**
	 * Computer return procedure releases a computer which becomes available in
	 * the warehouse upon completion
	 */
	public void up() {
		isLocked = true;
		((Promise<Computer>) promisesQueue.pollFirst()).resolve(computer);

	}
}
