package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class Region implements Entity, FoodSupplier, RegionInfo { //implements this three interfaces
	protected List<Animal> animal;
	
	public Region() {
		animal = new ArrayList<Animal>();
	}
	
	final void addAnimal(Animal a) {
		animal.add(a);
	}
	
	final void removeAnimal(Animal a) {
		//TODO
	}
	
	final List<Animal> getAnimals(){
		return animal;
	}
	
	public JSONObject asJSON() {
		//TODO
		return null;
	}
	
	public int herbivore() { //counts how many herbivore animals are in the region
		int count = 0;
		for(int i = 0; i < animal.size(); i++) {
			if(animal.get(i).getDiet() == Diet.HERBIVORE)
				count++;
		}
		return count;
	}
}
