package simulator.factories;

import org.json.JSONObject;

import simulator.model.DynamicSupplyRegion;

public class DynamicSupplyRegionBuilder extends Builder<DynamicSupplyRegion>{  
	
//	{
//	  "type" : "dynamic",
//	  "data" : {
//	     "factor" : 2.5,
//	     "food" : 1250.0
//	   }
//	}


public DynamicSupplyRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
	super(typeTag, desc);
	// TODO Auto-generated constructor stub
}

@Override
protected DynamicSupplyRegion createInstance(JSONObject data) {
	// TODO Auto-generated method stub
	return null;
}
}
