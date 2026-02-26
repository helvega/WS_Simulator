package simulator.factories;

import org.json.JSONObject;

import simulator.model.DynamicSupplyRegion;
import simulator.model.Region;

public class DynamicSupplyRegionBuilder extends Builder<Region>{  
	
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
	protected Region createInstance(JSONObject data) {
		DynamicSupplyRegion dsr = null;
		return dsr;
	}
	
	@Override
	void fillInData(JSONObject o) {
		// TODO Auto-generated method stub
		
	}
}
