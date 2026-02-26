package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
import simulator.model.SelectFirst;

public class SelectFirstBuilder extends Builder<SelectFirst> {
	
//	{  
//	  "type": "first", 
//	  "data": {}  
//	}

	public SelectFirstBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectFirst createInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return new SelectFirst();
	}

	@Override
	void fillInData(JSONObject o) {
		// TODO Auto-generated method stub
		
	}

}
