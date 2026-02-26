package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
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

	@Override
	protected SelectFirst createInstance(JSONObject data) {
		return new SelectFirst();
	}

	@Override
	void fillInData(JSONObject o) {
	}

}
