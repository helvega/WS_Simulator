package simulator.factories;

import org.json.JSONObject;

import simulator.model.DefaultRegion;

public class DefaultRegionBuilder extends Builder<DefaultRegion>{  
	
//	{  
//	  "type" : "default",  
//	  "data" : { }  
//	}


	public DefaultRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected DefaultRegion createInstance(JSONObject data) {
		
		return new DefaultRegion();
	}
	
	@Override
	void fillInData(JSONObject o) {
		
	}
}
