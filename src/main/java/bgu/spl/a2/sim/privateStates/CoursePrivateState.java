package bgu.spl.a2.sim.privateStates;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState {

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;

	/**
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		regStudents = new ArrayList<>();
		prequisites = new ArrayList<>();
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public void unRegisterAndUpdateAvailables() {
		// TODO Auto-generated method stub
		
	}

	public void addMoreAvailableSpaces(int howMuchMoreAvailablePlaces) {
		// TODO Auto-generated method stub
		
	}

	public void registerAndUpdateAvailables() {
		// TODO Auto-generated method stub
		
	}

	public void setAvailableSpots(int availableSpaces) {
		// TODO Auto-generated method stub
		
	}

	public void setPrerequisites(List<String> preRequisites) {
		// TODO Auto-generated method stub
		
	}

	}
