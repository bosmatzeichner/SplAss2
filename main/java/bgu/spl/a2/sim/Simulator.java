/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.Serialized.ActionSerialized;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

/**
 * A class describing the simulator for part 2 of the assignment
 */

public class Simulator {

	public static ActorThreadPool actorThreadPool;
	public static Warehouse myWarehouse;
	static Serialized json;
	static CountDownLatch latchy;

	/**
	 * Begin the simulation Should not be called before attachActorThreadPool()
	 */
	public static void start() {
		// -----------------------------------
		//System.out.println("--------------Phase1------------");
		ActionSerialized[] tempPhase = json.getPhaseA();
		latchy = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latchy);
		// -----------------------------------
		//System.out.println("--------------Phase2------------");
		tempPhase = json.getPhaseB();
		latchy = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latchy);
		// -----------------------------------
		//System.out.println("--------------Phase3------------");
		tempPhase = json.getPhaseC();
		latchy = new CountDownLatch(tempPhase.length);
		startPhase(tempPhase, latchy);
	}

	private static void startPhase(ActionSerialized[] tempPhase, CountDownLatch latchy) {
		// System.out.println("phase starting -------------");
		for (int i = 0; i < tempPhase.length; i++) {
			Action<?> tempAction = tempPhase[i].getActualAction();
			actorThreadPool.submit(tempAction, tempPhase[i].getDesignatedActor(),
					tempPhase[i].getDesignatedActorPrivateState());
			tempAction.getResult().subscribe(() -> {
				latchy.countDown();
			});
		}

		try {
			latchy.await();
		} catch (InterruptedException e) {
			System.out.println("THIS IS WHY IM HOT");
			return;
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be
	 * used to run the simulation
	 * 
	 * @param myActorThreadPool
	 *            - the ActorThreadPool which will be used by the simulator
	 */
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool) {
		actorThreadPool = myActorThreadPool;
	}

	/**
	 * shut down the simulation returns list of private states
	 */
	public static HashMap<String, PrivateState> end() {
		try {
			actorThreadPool.shutdown();
		} catch (InterruptedException e) {
		}
		HashMap<String, PrivateState> returnMap = new HashMap<String, PrivateState>(actorThreadPool.getActors());

		return returnMap;
	}

	public static void main(String[] args) {
		try {
			
			JsonReader jsonReader = new JsonReader(new FileReader("C:/USERS/NIlyasov/Desktop/input-example[1].json"));
			// JsonReader jsonReader = new JsonReader(new FileReader("C:/USERS/NIlyasov/Desktop/phaseonetwo.json"));
			//JsonReader jsonReader = new JsonReader(new FileReader(args[0]));
			Gson gson = new GsonBuilder().create();
			json = gson.fromJson(jsonReader, Serialized.class);
			
			myWarehouse = new Warehouse(json.getComputerSerialized());
			ActorThreadPool tempActorThreadPool = new ActorThreadPool(json.getThreads()+2000);
			attachActorThreadPool(tempActorThreadPool);
			
			actorThreadPool.start();
			
			start();

			HashMap<String, PrivateState> privateStateSummary = end();

			FileOutputStream fout = new FileOutputStream("result.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(privateStateSummary);
			oos.close();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
