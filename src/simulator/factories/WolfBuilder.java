package simulator.factories;

import org.json.JSONObject;

import simulator.model.Wolf;

public class WolfBuilder extends Builder<Wolf>{
	
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
	protected Wolf createInstance(JSONObject data) {
		
		return null;
	}

	@Override
	void fillInData(JSONObject o) {
		// TODO Auto-generated method stub
		
	}
}
