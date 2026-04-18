package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.factories.Factory;
import simulator.model.MapInfo.RegionData;

public class Simulator implements JSONable, Observable<EcoSysObserver>{
	private Factory<Animal> animalsFactory;
	private Factory<Region> regionsFactory;
	private RegionManager rgMngr;	//THe region manager of the simulation
	private List <Animal> participant; //All animals participating in the simulation
	private List<AnimalInfo> ai;
	private List<EcoSysObserver> observer;
	private double time;
	
	public Simulator(int cols, int rows, int width, int height, Factory<Animal> animalsFactory, Factory<Region> regionsFactory) {
		rgMngr = new RegionManager(cols, rows, width, height);
		participant = new ArrayList<Animal>();
		ai = new ArrayList<AnimalInfo>();
		this.animalsFactory = animalsFactory;
		this.regionsFactory = regionsFactory;
		time = 0.0;
		observer = new ArrayList<EcoSysObserver>();
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
		notifyOnRegionSet(r);
	}
	
	public void setRegion(int row, int col, JSONObject rJson) {
		setRegion(row, col, regionsFactory.createInstance(rJson));
	}
	
	private void addAnimal(Animal a) {
		participant.add(a);
		ai.add(a);
		rgMngr.registerAnimal(a);
		notifyOnAnimalAdded(a);
	}
	
	public void addAnimal(JSONObject aJson) {
		Animal a =  animalsFactory.createInstance(aJson);
		addAnimal(a);
		
	}
	
	public void advance(double dt) {
		time += dt;
		int aux = participant.size();
		for(int i = 0; i < aux; i++) { //remove dead animals
			if(participant.get(i).getState() == State.DEAD) {
				participant.remove(i);
				ai.remove(i);
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
		notifyOnAdvance(dt);
	}
	
	public JSONObject asJSON() {
		JSONObject jo = new JSONObject();
		jo.put("time", time);
		jo.put("state", rgMngr.asJSON());
		return jo;
	}
	
	public void reset(int cols, int rows, int width, int height) {
		participant = new ArrayList<Animal>(); //empty animal list
		ai = new ArrayList<AnimalInfo>();
		rgMngr = new RegionManager(cols, rows, width, height); //replace with a new region manager
		time = 0.0; // set time to 0
		notifyOnReset();
	}

	@Override
	public void addOberserver(EcoSysObserver o) {
		observer.add(o);
		notifyOnRegister(o);
	}

	@Override
	public void removeObserver(EcoSysObserver o) {
		boolean found = false;
		int idx = 0;
		for(idx = 0; found && idx < observer.size(); idx++) {
			if(observer.get(idx) == o) {
				found = true;
			}
		}
		if(found)
			observer.remove(idx);
	}
	
	private void notifyOnRegister(EcoSysObserver o) {
		o.onRegister(time, (MapInfo) rgMngr, ai);
	}
	
	private void notifyOnReset() {
		for(EcoSysObserver eso : observer) {
			eso.onReset(time, (MapInfo) rgMngr, ai);
		}
	}
	
	private void notifyOnAnimalAdded(AnimalInfo a) {
		for(EcoSysObserver eso : observer) {
			eso.onAnimalAdded(time, (MapInfo) rgMngr, ai, a);
		}
	}
	
	private void notifyOnRegionSet(Region r) {
		for(EcoSysObserver eso : observer) {
			eso.onRegionSet(rgMngr.getRows(), rgMngr.getCols(), (MapInfo) rgMngr, r);
		}
	}
	
	private void notifyOnAdvance(double dt) {				
		for(EcoSysObserver eso : observer) {
			eso.onAdvance(time, (MapInfo) rgMngr, ai, dt);
		}
	}
	
}
