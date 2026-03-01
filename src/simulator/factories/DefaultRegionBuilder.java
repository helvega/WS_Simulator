package simulator.factories;

import org.json.JSONObject;

import simulator.model.DefaultRegion;
import simulator.model.Region;

public class DefaultRegionBuilder extends Builder<Region>{  
	
//	{  
//	  "type" : "default",  
//	  "data" : { }  
//	}


	public DefaultRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
	}
	
	protected Region createInstance(JSONObject data) {
		
		return new DefaultRegion(); // this is a simple class that needs no special treatment to be created
	}

	void fillInData(JSONObject o) {
		
	}
}
