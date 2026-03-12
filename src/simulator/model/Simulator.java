package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.factories.Factory;

public class Simulator implements JSONable{
	private Factory<Animal> animalsFactory;
	private Factory<Region> regionsFactory;
	private RegionManager rgMngr;	//THe region manager of the simulation
	private List <Animal> participant; //All animals participating in the simulation
	private double time;
	
	public Simulator(int cols, int rows, int width, int height, Factory<Animal> animalsFactory, Factory<Region> regionsFactory) {
		rgMngr = new RegionManager(cols, rows, width, height);
		participant = new ArrayList<Animal>();
		this.animalsFactory = animalsFactory;
		this.regionsFactory = regionsFactory;
		time = 0.0;
	}
	
	public double getTime() {
		
		return time;
	}
	
	public List<? extends AnimalInfo> getAnimals(){
		return participant;
	}
	
	public MapInfo getMapInfo() {
		return rgMngr;
	}
	
	private void setRegion(int row, int col, Region r) {
		rgMngr.setRegion(row, col, r);
	}
	
	public void setRegion(int row, int col, JSONObject rJson) {
		setRegion(row, col, regionsFactory.createInstance(rJson));
	}
	
	private void addAnimal(Animal a) {
		participant.add(a);
		rgMngr.registerAnimal(a);
	}
	
	public void addAnimal(JSONObject aJson) {
		addAnimal(animalsFactory.createInstance(aJson));
	}
	
	public void advance(double dt) {
		time += dt;
		int aux = participant.size();
		for(int i = 0; i < aux; i++) { //remove dead animals
			if(participant.get(i).getState() == State.DEAD) {
				participant.remove(i);
				aux--;
			}
		}
		
		aux = participant.size();
		
		for(int i = 0; i < aux; i++) { 
			Animal a = participant.get(i);
			a.update(dt);
		}
		
		for (int i = 0; i < aux; i++) {
			if(participant.get(i).getState() != State.DEAD) {
				Animal a = participant.get(i);
				rgMngr.updateRegion(a, dt);
			}
		}
		
		
		for(int i = 0; i < aux; i++) { 
			Animal a = participant.get(i);
			if (a.isPregnant()){
				addAnimal(a.deliverBaby());
			}
		}	
		
		rgMngr.updateAllRegions(dt);
	}
	
	public JSONObject asJSON() {
		JSONObject jo = new JSONObject();
		jo.put("time", time);
		jo.put("state", rgMngr.asJSON());
		return jo;
	}
	
	public void reset(int cols, int rows, int width, int height) {
		participant = new ArrayList<Animal>(); //empty animal list
		rgMngr = new RegionManager(cols, rows, width, height); //replace with a new region manager
		time = 0.0; // set time to 0
	}
	
}
