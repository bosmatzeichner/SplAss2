/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.Serialized.ActionSerialized;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

/**
 * A class describing the simulator for part 2 of the assignment
 */

public class Simulator {

	public static ActorThreadPool actorThreadPool;
	public static Warehouse myWarehouse;
	static Serialized json;
	
	
	/**
	 * Begin the simulation 
	 * Should not be called before attachActorThreadPool()
	 */
	public static void start(){
		ActionSerialized[] tempPhase = json.getPhaseA();
		CountDownLatch latch = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latch);
		
		tempPhase = json.getPhaseB();
		latch = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latch);
		
		tempPhase = json.getPhaseC();
		latch = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latch);
		
	}
	
	private static void startPhase(ActionSerialized[] tempPhase, CountDownLatch latchy){
		for (int i = 0; i < tempPhase.length; i++) {
			Action<?> tempAction = tempPhase[i].getActualAction();
			actorThreadPool.submit(tempAction, tempPhase[i].getDesignatedActor(), tempPhase[i].getDesignatedActorPrivateState());
			tempAction.getResult().subscribe(()->{
				latchy.countDown();
			});
		}
		
		try {
			latchy.await();
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	 * 
	 * @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	 */
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		actorThreadPool = myActorThreadPool;
	}

	/**
	 * shut down the simulation
	 * returns list of private states
	 */
	public static HashMap<String,PrivateState> end(){
		try {
			actorThreadPool.shutdown();
		} catch (InterruptedException e) {}
		
		return (HashMap<String, PrivateState>) actorThreadPool.getActors();
	}


	public static void main(String [] args){
		BufferedReader bufReader = null;
		try {
			//System.out.println (args[0].length());
			//bufReader = new BufferedReader(new FileReader(args[0]));
			bufReader = new BufferedReader(new FileReader("C:/USERS/NIlyasov/Desktop/input-example[1].json"));
			Gson gson = new GsonBuilder().create();
			Serialized json = gson.fromJson(bufReader, Serialized.class);
			//realJAS= new Source(json.getThreads(), json.getTools(), json.getPlans(), json.getRequests());
			myWarehouse = new Warehouse(json.getComputerSerialized());
			ActorThreadPool tempActorThreadPool = new ActorThreadPool(json.getThreads());
			attachActorThreadPool(tempActorThreadPool);
			
			//System.out.println ("Flag 1");
			//System.out.println(realJAS.getRequests()[0][0]);
			
			actorThreadPool.start();
			start();
			
			HashMap<String, PrivateState> privateStateSummary = end(); 
			
			
			for(Map.Entry<String, PrivateState> i : privateStateSummary.entrySet()){
				if(i.getKey() == "Course"){
					
				}
			}

			//System.out.println ("Flag 2");
			//int size = Output.size();
			FileOutputStream fout = new FileOutputStream("result.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(privateStateSummary);
			oos.close();
			
		

			// Read a wave from intputFile, Add a manufacturing task for all products in the current wave to the task queue.
			// Proceed to the next wave once all products in the current wave have been manufactured.	
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("BUSTED");
		}
		
	}









}
