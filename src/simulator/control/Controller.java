package simulator.control;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.view.SimpleObjectViewer;
import simulator.view.SimpleObjectViewer.ObjInfo;

import simulator.model.AnimalInfo;
import simulator.model.MapInfo;
import simulator.model.Simulator;

public class Controller {
	Simulator sim;
	
	public Controller(Simulator sim) {
		this.sim = sim;
	}
	
	public void loadData(JSONObject data) { //load data JSONObject passed from main
		JSONArray animals = data.getJSONArray("animals"); //animals go first, so we take JSONArray of animals
		for(int z = 0; z < animals.length(); z++) { // we traverse the array of JSONObjects
			JSONObject aux = animals.getJSONObject(z); // every JSONObject is a block specifying animals
			for (int i = 0; i < aux.getInt("amount"); i++) { //we take the integer amount so we know how many animals we have to create
				sim.addAnimal(aux.getJSONObject("spec")); //we create an instance of an animal with "spec" as data
			}
		}
		
		if (data.has("region")) { //Regions go second
			JSONArray region = data.getJSONArray("region"); //Regions is also an array of JSONObjects
			for(int z = 0; z < region.length(); z++) { //We traverse the array
				JSONArray range_r = region.getJSONObject(z).getJSONArray("row"); //first take rows and columns range
				JSONArray range_c = region.getJSONObject(z).getJSONArray("col");
				for (int i = range_r.getInt(0); i < range_r.getInt(1); i++)
					for (int j = range_c.getInt(0); j < range_c.getInt(1); j++) // then we fill the range of r and c with the kind of region defined
						sim.setRegion(i, j, region.getJSONObject(z).getJSONObject("spec")); //"spec" as data
			}
		}
	}
	
	public void run(double t, double dt, boolean sv, OutputStream out) { //it must write to out a JSON structure of the following form:
		/*
		 *  {
		 *   "in": initState,
		 *   "out": finalState
 		 *	}
		 */
		
		SimpleObjectViewer view = null;  //the object that will act as an interface to see the simulation
		if (sv) {  //if the simple view mode is selected
		   MapInfo m = sim.getMapInfo();  //take the info of the map created to create the sv
		   view = new SimpleObjectViewer("[ECOSYSTEM]", m.getWidth(), m.getHeight(), m.getCols(), m.getRows());  
		   view.update(toAnimalsInfo(sim.getAnimals()), sim.getTime(), dt);  //add all the JSONs previously initialized
		}

		// Where initState is the result returned by sim.asJSON() before entering the loop, and finalState is the result returned by sim.asJSON() upon exiting the loop
		while(sim.getTime() < t) {
			sim.advance(dt); //this is really the simulation running
			if (sv) view.update(toAnimalsInfo(sim.getAnimals()), sim.getTime(), dt); //we see the simulation representation here

		}
		if (sv) view.close();
	}
	
	private List<ObjInfo> toAnimalsInfo(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size()); //use a list of the interface ObjInfo belonging to sv to make it possible to see the simulation
		for (AnimalInfo a : animals)
			ol.add(new ObjInfo(a.getGeneticCode(), (int) a.getPosition().getX(), (int) a.getPosition().getY(), (int)Math.round(a.getAge())+2));
			//formatting correctly the animal info
		return ol;
	}

	
}
