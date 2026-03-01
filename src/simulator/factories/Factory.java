package simulator.factories;

import java.util.List;
import org.json.JSONObject;

public interface Factory<T>{ 
		public T createInstance(JSONObject info); //throw IncorrectInfo;   //receives a JSON structure describing the object 
												  //to create, and returns an instance of the corresponding class 
												  //an instance of a subtype of T
		public List<JSONObject> getInfo(); // returns a list of JSON objects describing what can be created by the factory
}
