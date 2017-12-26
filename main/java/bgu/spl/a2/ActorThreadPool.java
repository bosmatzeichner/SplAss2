package bgu.spl.a2;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {

	int numOfThreads;
	boolean isShutDown = false;

	ConcurrentHashMap<String, ConcurrentLinkedQueue<Action<?>>> qsOfActors;
	ConcurrentHashMap<String, PrivateState> privateStatesOfActors;
	Thread[] threads;
	VersionMonitor vMonitor = new VersionMonitor();

	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */

	public ActorThreadPool(int nthreads) {
		numOfThreads = nthreads;

		qsOfActors = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Action<?>>>();
		privateStatesOfActors = new ConcurrentHashMap<>();

		threads = new Thread[numOfThreads];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				while (!isShutDown) {
					int version = vMonitor.getVersion();
					for (Entry<String, ConcurrentLinkedQueue<Action<?>>> j : qsOfActors.entrySet()) {
						ConcurrentLinkedQueue<Action<?>> queueOfAnActor = (ConcurrentLinkedQueue<Action<?>>) j
								.getValue();
						if (!queueOfAnActor.isEmpty()) {
							Action<?> action = queueOfAnActor.poll();
							vMonitor.inc();
							PrivateState tempPriState = privateStatesOfActors.get(j.getKey());
							if (action != null) {
								action.handle(this, j.getKey(), tempPriState);
								tempPriState.addRecord(action.getActionName());
							}
						}
					}
					try {
						vMonitor.await(version);
					} catch (InterruptedException e) {	
						Thread.currentThread().interrupt();
					}
				}
			});
		}
	}

	/**
	 * getter for actors
	 * 
	 * @return actors
	 */
	public Map<String, PrivateState> getActors() {
		return privateStatesOfActors;
	}

	/**
	 * getter for actor's private state
	 * 
	 * @param actorId
	 *            actor's id
	 * @return actor's private state
	 */
	public PrivateState getPrivateState(String actorId) {
		return privateStatesOfActors.get(actorId);
	}

	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {
		if (!isShutDown) {
			if (!qsOfActors.containsKey(actorId)) {
				qsOfActors.put(actorId, new ConcurrentLinkedQueue<>());
				privateStatesOfActors.put(actorId, actorState);
			}
			qsOfActors.get(actorId).add(action);
			vMonitor.inc();
		}
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public void shutdown() throws InterruptedException {
		isShutDown = true;
		for (Thread i : threads) {
			i.interrupt();
		}
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		for (Thread i : threads) {
			i.start();
		}
	}

}
