package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Messages;
import simulator.model.DefaultRegion;
import simulator.model.Region;

public class DefaultRegionBuilder extends Builder<Region>{  
	
//	 {
//		   "type": "default",
//		   "desc": "Infinite food supply",
//		   "data": {}
//		 }



	public DefaultRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}
	
	protected Region createInstance(JSONObject data) {
		
		return new DefaultRegion(super.getTypeTag()); // this is a simple class that needs no special treatment to be created
	}

	void fillInData(JSONObject o) {
		o.put("type", "default");
		o.put("desc", Messages.DEFAULT_REGION);
		o.put("data", " ");
	}
}
