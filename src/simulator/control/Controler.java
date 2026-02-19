package simulator.control;

import java.io.OutputStream;

import org.json.JSONObject;

import simulator.model.Simulator;

public class Controler {
	Simulator sim;
	
	public Controler(Simulator sim) {
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
		// Where initState is the result returned by sim.asJSON() before entering the loop, and finalState is the result returned by sim.asJSON() upon exiting the loop
		while(sim.getTime() < t) {
			sim.advance(dt);
		}
		//Additionally, if the value of sv is true, the simulation must be shown using the object viewer
	}
	
}
