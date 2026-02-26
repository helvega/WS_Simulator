package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
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

	@Override
	protected SelectYoungest createInstance(JSONObject data) {
		return new SelectYoungest();
	}

	@Override
	void fillInData(JSONObject o) {
	}
}