package simulator.model;

import simulator.misc.Vector2D;

public class Sheep extends Animal {
	//It is a class representing a sheep. It is an herbivorous animal with genetic code "Sheep". 
	//It is an animal that does not hunt other animals, only eats what the region it is in provides, 
	//and can mate with other animals with the same genetic code.
	
	private Animal dangerSource; // another animal that is considered a danger at a given moment
	private SelectionStrategy dangerStrategy; // another animal that is considered a danger at a given moment
	
	public Sheep(SelectionStrategy mateStrategy, SelectionStrategy dangerStrategy,  Vector2D pos) {
		super("sheep", Diet.HERBIVORE, 40.0, 35.0, mateStrategy, pos);
		this.dangerStrategy = dangerStrategy;
		dangerSource = null;
	}
	
	protected Sheep(Sheep p1, Animal p2) {
		super(p1, p2);
		this.dangerStrategy = p1.dangerStrategy;
		dangerSource = null;
	}

	public void update() {
		if(state != State.DEAD && state != null) {
			if(state == State.DANGER) {}
				//one must ask the region manager for the list of carnivorous animals in the visual field, 
				//using the getAnimalsInRange method, and then choose one using the corresponding selection strategy
			else if(state == State.MATE) {}
				//one must ask the region manager for the list of carnivorous animals in the visual field, 
				//using the getAnimalsInRange method, and then choose one using the corresponding selection strategy
		}
	}
	
	@Override
	protected void setNormalStateAction() {
		dangerSource = null;
		mateTarjet = null;
		// TODO...

	}

	@Override
	protected void setMateStateAction() {
		dangerSource = null;
		// TODO...
	}

	@Override
	protected void setHungerStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setDangerStateAction() {
		mateTarjet = null;
		// TODO...

	}

	@Override
	protected void setDeadStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2D getPosition() {
		
		return pos;
	}

	@Override
	public String getGeneticCode() {
		// TODO Auto-generated method stub
		return null;
	}

	public Diet getDiet() {
		return diet;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSightRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAge() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2D getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPregnant() {
		// TODO Auto-generated method stub
		return false;
	}

}
