package simulator.model;

import simulator.misc.Messages;

public class DefaultRegion extends Region {
	
	public DefaultRegion(){} //Empty Constructor
	
	public void update(double dt) {
		//Does nothing
	}
	
	public double getFood(AnimalInfo a, double dt) {
		if(a.getDiet() == Diet.CARNIVORE)
			return 0.0;
		else 
			return 60.0 * Math.exp(-Math.max(0, herbivore() - 5.0) * 2.0) * dt;
	}

	@Override
	public String toString() {
		
		return Messages.DEFAULT_REGION;
	}
}
