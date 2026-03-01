package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;

public class SelectYoungestBuilder extends Builder<SelectionStrategy>{ 
	
//	{  
//	  "type": "youngest",
//	  "data": {}  
//	}


	public SelectYoungestBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}
	
	protected SelectionStrategy createInstance(JSONObject data) {
		return new SelectYoungest();//these are simple classes that need no special treatment
	}
	
	void fillInData(JSONObject o) {
		
	}
}