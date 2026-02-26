package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectFirst;
import simulator.model.SelectionStrategy;

public class SelectFirstBuilder extends Builder<SelectionStrategy> {
	
//	{  
//	  "type": "first", 
//	  "data": {}  
//	}

	public SelectFirstBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
		
	}

	protected SelectFirst createInstance(JSONObject data) {
		
		return new SelectFirst();
	}

	void fillInData(JSONObject o) {
		
	}

}
