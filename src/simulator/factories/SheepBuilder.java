package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Constants;
import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectClosest;
import simulator.model.SelectFirst;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Animal createInstance(JSONObject data) {
		Sheep e = null;		
		Vector2D p = null;
		SelectionStrategy sel_mate = new SelectFirst(), sel_danger = new SelectClosest();
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
		if (data.has("danger_strategy")) {
			sel_danger = strategy_factory.createInstance(data.getJSONObject("danger_strategy"));
		}

		e = new Sheep(sel_mate, sel_danger, p);
		
		return e;
	}

	@Override
	void fillInData(JSONObject o) {
	}
}
