package simulator.control;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	public void loadData(JSONObject data) {
		//TODO...
	}
	
	public void run(double t, double dt, boolean sv, OutputStream out) { //it must write to out a JSON structure of the following form:
		/*
		 *  {
		 *   "in": initState,
		 *   "out": finalState
 		 *	}
		 */
		
		SimpleObjectViewer view = null;  
		if (sv) {  
		   MapInfo m = sim.getMapInfo();  
		   view = new SimpleObjectViewer("[ECOSYSTEM]", m.getWidth(), m.getHeight(), m.getCols(), m.getRows());  
		   view.update(toAnimalsInfo(sim.getAnimals()), sim.getTime(), dt);  
		}

		// Where initState is the result returned by sim.asJSON() before entering the loop, and finalState is the result returned by sim.asJSON() upon exiting the loop
		while(sim.getTime() < t) {
			sim.advance(dt);
			if (sv) view.update(toAnimalsInfo(sim.getAnimals()), sim.getTime(), dt);

		}
		if (sv) view.close();
	}
	
	private List<ObjInfo> toAnimalsInfo(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size());
		for (AnimalInfo a : animals)
			ol.add(new ObjInfo(a.getGeneticCode(), (int) a.getPosition().getX(), (int) a.getPosition().getY(), (int)Math.round(a.getAge())+2));
		return ol;
	}

	
}
