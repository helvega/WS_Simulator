package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Constants;
import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectClosest;
import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;
import simulator.model.Sheep;

public class SheepBuilder extends Builder<Animal>{
	List <Builder<SelectionStrategy>> bss = new ArrayList<>();
	Factory<SelectionStrategy> strategy_factory;
//	{
//	  "type": "sheep",
//	  "data": {
//	    "mate_strategy": { … },
//	    "danger_strategy": { … },
//	    "pos": {
//	      "x_range": [100.0, 200.0],
//	      "y_range": [100.0, 200.0]
//	    }
//	  }
//	}

	public SheepBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
		bss.add(new SelectFirstBuilder(Constants.TYPE_SELECT_FIRST, Constants.DESC_SELECT_FIRST));
		bss.add(new SelectClosestBuilder(Constants.TYPE_SELECT_CLOSEST, Constants.DESC_SELECT_CLOSEST));
		bss.add(new SelectYoungestBuilder(Constants.TYPE_SELECT_YOUNGEST, Constants.DESC_SELECT_YOUNGEST));
		strategy_factory = new BuilderBasedFactory<SelectionStrategy>(bss);
		//initialize the BuilderBasedFactory that will create instances of SelectStrategy objects
	}

	protected Animal createInstance(JSONObject data) {
		Vector2D p = null;
		SelectionStrategy sel_mate = new SelectYoungest(), sel_danger = new SelectClosest(); //default strategies
		if(data.has("pos")) { // if it has position defined we need to extract the coordinates based on ranges
			JSONObject pos = data.getJSONObject("pos");
			int max_x = pos.getJSONArray("x_range").getInt(1);
			int min_x = pos.getJSONArray("x_range").getInt(0);
			int max_y = pos.getJSONArray("y_range").getInt(1);
			int min_y = pos.getJSONArray("y_range").getInt(0);
			double x = Utils.RAND.nextDouble(max_x - min_x) + min_x; //generate random positions based on the obtained ranges
			double y = Utils.RAND.nextDouble(max_y - min_y) + min_y;
			p = new Vector2D(x, y); //for passing to the constructor
		}
		
		if (data.has("mate_strategy")) { // set mate strategy if defined
			sel_mate = strategy_factory.createInstance(data.getJSONObject("mate_strategy"));
		}
		if (data.has("danger_strategy")) { // set danger strategy if defined
			sel_danger = strategy_factory.createInstance(data.getJSONObject("danger_strategy"));
		}

		return new Sheep(sel_mate, sel_danger, p); //return the analyzed sheep
	}

	void fillInData(JSONObject o) {
	}
}
