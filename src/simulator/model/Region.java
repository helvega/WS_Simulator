package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
		boolean searching = true;
		int i = 0;
		
		while(i < animal.size() && searching) {
			if(animal.get(i).getPosition() == a.getPosition())
				searching = false;
			else
				i++;
		}
		
		if(i < animal.size())
			animal.remove(i);
	}
	
	final List<Animal> getAnimals(){
		return animal;
	}
	
	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
	    
	    JSONArray animals = new JSONArray(); //creates an empty array
	    
	    for(Animal a: animal) { // puts every animal as JSON objects in the array
	    	animals.put(a.asJSON());
	    }
	    
	    obj.put("animala", animals); // puts in the JSON object, the key and the array
	    
		return obj;
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
