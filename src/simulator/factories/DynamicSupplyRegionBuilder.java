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

	protected Region createInstance(JSONObject data) {
		double factor = data.optDouble("factor", 2.0); //analyze the two needed variables to create the instance of this kind of region
		double food = data.optDouble("food", 1000.0);
		return new DynamicSupplyRegion(food, factor);
	}

	void fillInData(JSONObject o) {
	}
}
