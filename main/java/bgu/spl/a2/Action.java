package bgu.spl.a2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R>
 *            the action result type
 */
public abstract class Action<R> {
	protected ActorThreadPool actorThreadPool;
	private Promise<R> myPromise = new Promise<R>();
	private callback myCallback = null;
	protected AtomicReference<String> actionName = new AtomicReference<String>("");
	protected String ownerActorName;
	protected PrivateState ownerActorState;
	protected List<Action<?>> actions = new ArrayList<>();

	/**
	 * start handling the action - note that this method is protected, a thread
	 * cannot call it directly.
	 */
	protected abstract void start();

	/**
	 *
	 * start/continue handling the action
	 *
	 * this method should be called in order to start this action or continue
	 * its execution in the case where it has been already started.
	 *
	 * IMPORTANT: this method is package protected, i.e., only classes inside
	 * the same package can access it - you should *not* change it to
	 * public/private/protected
	 *
	 */
	/* package */ final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {
		if (myCallback == null) {
			actorThreadPool = pool;
			ownerActorName = actorId;
			ownerActorState = actorState;
			// System.out.println(getActionName());
			start();
		} else {
			// System.out.println(getActionName() + " CALLBACK");
			myCallback.call();
		}
	}

	/**
	 * add a callback to be executed once *all* the given actions results are
	 * resolved
	 * 
	 * Implementors note: make sure that the callback is running only once when
	 * all the given actions completed.
	 *
	 * @param actions
	 * @param callback
	 *            the callback to execute once all the results are resolved
	 */
	protected final void then(Collection<? extends Action<?>> actions, callback callback) {
		AtomicInteger numOfActions = new AtomicInteger(actions.size());
		myCallback = callback;
		for (Action<?> action : actions) {
			action.getResult().subscribe(() -> {
				if (numOfActions.decrementAndGet() == 0) {
					//actorThreadPool.qsOfActors.get(ownerActorName).add(this);
					actorThreadPool.submit(this, ownerActorName, ownerActorState);
				}
			});
		}
	}

	/**
	 * resolve the internal result - should be called by the action derivative
	 * once it is done.
	 *
	 * @param result
	 *            - the action calculated result
	 */
	protected final void complete(R result) {
		myPromise.resolve(result);
	}

	/**
	 * @return action's promise (result)
	 */
	public final Promise<R> getResult() {
		return myPromise;
	}

	/**
	 * Send an action to another actor
	 * 
	 * @param action
	 *            the action
	 * @param actorId
	 *            actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 * 
	 * @return promise that will hold the result of the sent action
	 */
	public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState) {
		actorThreadPool.submit(action, actorId, actorState);
		return getResult();
	}

	/**
	 * set action's name
	 * 
	 * @param actionName
	 */
	public void setActionName(String actionName) {
		this.actionName.getAndSet(actionName);
	}

	/**
	 * @return action's name
	 */
	public String getActionName() {
		return actionName.get();
	}
}
