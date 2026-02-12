package simulator.model;

import simulator.misc.Utils;

public class DynamicSupplyRegion extends Region {

	private double foodAmount; //Amount of food in the region
	private double growthFactor; //Growth factor to increase or decrease amount of food
	
	public DynamicSupplyRegion(double initFood, double growthFactor) {
		this.foodAmount = initFood; // initial amount of food passed as parameter
		this.growthFactor = growthFactor; // growth factor 
	}
	
	public void update(double dt) { 
		if(Utils.RAND.nextDouble() > 0.5) // with a probability of 50%
			foodAmount += dt*growthFactor;
	}

	public double getFood(AnimalInfo a, double dt) {
		if(a.getDiet() == Diet.CARNIVORE)
			return 0.0;
		else {
			double aux = Math.min(foodAmount, 60.0 * Math.exp( - Math.max(0, herbivore() - 5.0) * 2.0) * dt);
			foodAmount -= aux;
			return aux;
		}
	}

}
