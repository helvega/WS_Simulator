package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Messages;
import simulator.model.DynamicSupplyRegion;
import simulator.model.Region;

public class DynamicSupplyRegionBuilder extends Builder<Region>{  
	
//	 {
//		   "type": "dynamic",
//		   "desc": "Dynamic food supply",
//		   "data": {
//		     "factor": "food increase factor (optional, default 2.0)",
//		     "food": "initial amount of food (optional, default 100.0)"
//		   }
//		 }


	public DynamicSupplyRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}

	protected Region createInstance(JSONObject data) {
		double factor = data.optDouble("factor", 2.0); //analyze the two needed variables to create the instance of this kind of region
		double food = data.optDouble("food", 1000.0);
		return new DynamicSupplyRegion(food, factor, super.getTypeTag());
	}

	void fillInData(JSONObject o) {
		o.put("type", "dynamic");
		o.put("desc", Messages.DYNAMIC_SUPPLY_REGION);
		JSONObject jo = new JSONObject();
		jo.put("factor", Messages.DYNAMIC_FACTOR);
		jo.put("food", Messages.DYNAMIC_FOOD);
		
		o.put("data", jo);
	}
}
