package bgu.spl.a2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * an abstract class that represents private states of an actor
 * it holds actions that the actor has executed so far 
 * IMPORTANT: You can not add any fields to this class.
 */
@SuppressWarnings("serial")
public abstract class PrivateState implements Serializable {
	
	// holds the actions' name what were executed
	private List<String> history = new ArrayList<String>();

	public List<String> getLogger(){
		return history;
	}
	
	/**
	 * add an action to the records
	 *  
	 * @param actionName
	 */
	public void addRecord(String actionName){
		synchronized(this) {
			history.add(actionName);
		}
	}
	
	
}
