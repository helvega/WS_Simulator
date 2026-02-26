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
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Region createInstance(JSONObject data) {
		
		return new DefaultRegion();
	}
	
	@Override
	void fillInData(JSONObject o) {
		
	}
}
