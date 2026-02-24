package simulator.factories;

import org.json.JSONObject;

import simulator.model.Sheep;

public class SheepBuilder extends Builder<Sheep>{
	
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
	// TODO Auto-generated constructor stub
}

@Override
protected Sheep createInstance(JSONObject data) {
	// TODO Auto-generated method stub
	return null;
}
}
