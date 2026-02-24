package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;

public class SelectYoungestBuilder extends Builder<Animal>{ 
	
//	{  
//	  "type": "youngest",
//	  "data": {}  
//	}


public SelectYoungestBuilder(String typeTag, String desc) throws IllegalArgumentException {
	super(typeTag, desc);
	// TODO Auto-generated constructor stub
}

@Override
protected Animal createInstance(JSONObject data) {
	// TODO Auto-generated method stub
	return null;
}
}