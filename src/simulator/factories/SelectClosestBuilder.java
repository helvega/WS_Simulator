package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
import simulator.model.SelectClosest;

public class SelectClosestBuilder extends Builder<SelectClosest>{ 
	
//	{  
//	  "type": "closest",
//	  "data": {}
//	}


	public SelectClosestBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}
	
	@Override
	protected SelectClosest createInstance(JSONObject data) {
		return new SelectClosest();
	}

	@Override
	void fillInData(JSONObject o) {
		
	}
}
