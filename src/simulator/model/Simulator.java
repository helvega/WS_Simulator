package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.factories.Factory;
import simulator.misc.Utils;
import simulator.misc.Vector2D;

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
			
			else {
				Animal a = participant.get(i);
				a.update(dt);
				if(a.isPregnant()) { //don't know if we have to add baby after update, before or it doesn't matter
					addAnimal(a.deliverBaby());
				}
				rgMngr.updateRegion(a, dt); //does it make sense to update one region at every animal and then, update all regions at once???? 
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
	
}
