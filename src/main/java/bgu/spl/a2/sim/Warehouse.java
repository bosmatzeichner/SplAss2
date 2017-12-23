package bgu.spl.a2.sim;

import java.util.HashMap;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
	
	HashMap<String ,SuspendingMutex> computers;
	
}
