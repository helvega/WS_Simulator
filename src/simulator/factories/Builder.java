package simulator.factories;

import org.json.JSONObject;

import simulator.model.Animal;

public abstract class Builder<T> {  
	private String typeTag;  
	private String desc;

	public Builder(String typeTag, String desc) throws IllegalArgumentException {
    if (typeTag == null || desc == null || typeTag.isBlank() || desc.isBlank())  
			throw new IllegalArgumentException("Invalid type/desc");  
		this.typeTag = typeTag;  
		this.desc = desc;
	}

	public String getTypeTag() {  
		return typeTag;  
	}

	public JSONObject getInfo() {  
		JSONObject info = new JSONObject();  
		info.put("type", typeTag);  
		info.put("desc", desc);  
		JSONObject data = new JSONObject();  
		fillInData(data);
		info.put("data", data);  
		return info;  
	}

	protected void fillInData(JSONObject o) {  
		// subclasses will override this method 
	}

	@Override  
	public String toString() {  
		return desc;  
	}

	protected abstract T createInstance(JSONObject data);  
	
	static public class SelectFirstBuilder extends Builder<Animal>{ //SELECT FIRST BUILDER -------------------------------------->
		
//			{  
//			  "type": "first", 
//			  "data": {}  
//			}

		public SelectFirstBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	static public class SelectClosestBuilder extends Builder<Animal>{ //SELECT CLOSEST BUILDER -------------------------------------->
		
//			{  
//			  "type": "closest",
//			  "data": {}
//			}


		public SelectClosestBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	static public class SelectYoungestBuilder extends Builder<Animal>{ //SELECT YOUNGEST BUILDER -------------------------------------->
		
//			{  
//			  "type": "youngest",
//			  "data": {}  
//			}


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
	
	static public class SheepBuilder extends Builder<Animal>{ //SHEEP BUILDER -------------------------------------->
		
//			{
//			  "type": "sheep",
//			  "data": {
//			    "mate_strategy": { … },
//			    "danger_strategy": { … },
//			    "pos": {
//			      "x_range": [100.0, 200.0],
//			      "y_range": [100.0, 200.0]
//			    }
//			  }
//			}


		public SheepBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	static public class WolfBuilder extends Builder<Animal>{ //WOLF BUILDER -------------------------------------->
		
//			{
//			  "type": "wolf",
//			  "data": {
//			    "mate_strategy" : { … },
//			    "hunt_strategy" : { … },
//			    "pos" : {
//			      "x_range" : [100.0, 200.0],
//			      "y_range" : [100.0, 200.0]
//			    }
//			  }
//			}


		public WolfBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	static public class DefaultRegionBuilder extends Builder<Animal>{ //DEFAULT REGION BUILDER -------------------------------------->
		
//			{  
//			  "type" : "default",  
//			  "data" : { }  
//			}


		public DefaultRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	static public class DynamicSupplyRegionBuilder extends Builder<Animal>{ //DYNAMIC SUPPLY REGION BUILDER -------------------------------------->
		
//			{
//			  "type" : "dynamic",
//			  "data" : {
//			     "factor" : 2.5,
//			     "food" : 1250.0
//			   }
//			}


		public DynamicSupplyRegionBuilder(String typeTag, String desc) throws IllegalArgumentException {
			super(typeTag, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Animal createInstance(JSONObject data) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
