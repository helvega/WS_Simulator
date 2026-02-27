package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;
import simulator.model.Sheep;

public class SheepBuilder extends Builder<Animal>{
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
		strategy_factory = new BuilderBasedFactory<SelectionStrategy>();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Animal createInstance(JSONObject data) {
		Sheep e = null;		
		Vector2D p = null;
		SelectionStrategy sel_mate = new SelectYoungest(), sel_danger = new SelectYoungest();
		if(data.has("pos")) {
			JSONObject pos = data.getJSONObject("pos");
			double x = Utils.RAND.nextDouble((pos.getJSONArray("x").getInt(1)) - pos.getJSONArray("x").getInt(0)) + pos.getJSONArray("x").getInt(0);
			double y = Utils.RAND.nextDouble((pos.getJSONArray("y").getInt(1)) - pos.getJSONArray("y").getInt(0)) + pos.getJSONArray("y").getInt(0);
			p = new Vector2D(x, y);
		}
		else {
			p = new Vector2D(0, 0);
		}
		
		if (data.has("mate_strategy")) {
			sel_mate = strategy_factory.createInstance(data.getJSONObject("mate_strategy"));
		}
		if (data.has("danger_strategy")) {
			sel_danger = strategy_factory.createInstance(data.getJSONObject("danger_strategy"));
		}

		e = new Sheep(sel_mate, sel_danger, p);
		
		return e;
	}

	@Override
	void fillInData(JSONObject o) {
		// TODO Auto-generated method stub
		
	}
}
