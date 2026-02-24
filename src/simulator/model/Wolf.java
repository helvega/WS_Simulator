package simulator.model;

import simulator.misc.Vector2D;

public class Wolf extends Animal {

	protected Wolf(Animal p1, Animal p2) {
		super(p1, p2);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGeneticCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diet getDiet() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	protected void setNormalStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMateStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setHungerStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setDangerStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setDeadStateAction() {
		// TODO Auto-generated method stub

	}

}
