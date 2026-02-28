package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;
import simulator.model.Wolf;

public class WolfBuilder extends Builder<Animal>{
	Factory<SelectionStrategy> strategy_factory;
//	{
//	  "type": "wolf",
//	  "data": {
//	    "mate_strategy" : { … },
//	    "hunt_strategy" : { … },
//	    "pos" : {
//	      "x_range" : [100.0, 200.0],
//	      "y_range" : [100.0, 200.0]
//	    }
//	  }
//	}


	public WolfBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Animal createInstance(JSONObject data) {
		Wolf w = null;	
		Vector2D p = null;
		
		SelectionStrategy sel_mate = new SelectYoungest(), sel_hunt = new SelectYoungest();
		if(data.has("pos")) {
			JSONObject pos = data.getJSONObject("pos");
			int max_x = pos.getJSONArray("x_range").getInt(1);
			int min_x = pos.getJSONArray("x_range").getInt(0);
			int max_y = pos.getJSONArray("y_range").getInt(1);
			int min_y = pos.getJSONArray("y_range").getInt(0);
			double x = Utils.RAND.nextDouble(max_x - min_x) + min_x;
			double y = Utils.RAND.nextDouble(max_y - min_y) + min_y;
			p = new Vector2D(x, y);
		}
		else {
			p = new Vector2D(0, 0);
		}
		
		if (data.has("mate_strategy")) {
			sel_mate = strategy_factory.createInstance(data.getJSONObject("mate_strategy"));
		}
		if (data.has("hunt_strategy")) {
			sel_hunt = strategy_factory.createInstance(data.getJSONObject("danger_strategy"));
		}

		w = new Wolf(sel_mate, sel_hunt, p);
		
		return w;
	}

	@Override
	void fillInData(JSONObject o) {
		// TODO Auto-generated method stub
		
	}
}
