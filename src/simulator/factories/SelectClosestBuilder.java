package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
import simulator.model.SelectClosest;
import simulator.model.SelectionStrategy;

public class SelectClosestBuilder extends Builder<SelectionStrategy>{ 
	
//	{  
//	  "type": "closest",
//	  "data": {}
//	}


	public SelectClosestBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}
	
	protected SelectionStrategy createInstance(JSONObject data) {
		return new SelectClosest();
	}

	void fillInData(JSONObject o) {
		
	}
}
