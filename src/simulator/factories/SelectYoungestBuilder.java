package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;
import simulator.model.SelectYoungest;

public class SelectYoungestBuilder extends Builder<SelectYoungest>{ 
	
//	{  
//	  "type": "youngest",
//	  "data": {}  
//	}


public SelectYoungestBuilder(String typeTag, String desc) throws IllegalArgumentException {
	super(typeTag, desc);
	// TODO Auto-generated constructor stub
}

@Override
protected SelectYoungest createInstance(JSONObject data) {
	// TODO Auto-generated method stub
	return new SelectYoungest();
}

@Override
void fillInData(JSONObject o) {
	// TODO Auto-generated method stub
	
}
}