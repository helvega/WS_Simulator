package simulator.model;

import simulator.misc.Vector2D;
import java.util.List;

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
	
	protected void doNormalAction(double dt) {
		if (pos.distanceTo(dest) > 8) {
			pos = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
		}
		move(speed*dt*Math.exp((energy-100.0)*0.007));
		age += dt;
		energy -= 20*dt;
		if (energy < 0) energy = 0;
		else if (energy > 100) energy = 100;
		desire -= 20*dt;
		if (desire < 0) desire = 0;
		else if (desire > 100) desire = 100;
		if (dangerSource == null) {
			List<Animal> dangerous_animals = regionMngr.getAnimalsInRange(this, p -> p.getDiet() == Diet.CARNIVORE);
			if (!dangerous_animals.isEmpty()) {
				dangerSource = dangerStrategy.select(this, dangerous_animals);
				setDangerStateAction();
			}
		}
		if (dangerSource == null) {
			if (desire > 65) {
				setMateStateAction();
			}
		}
	}
	
	protected void doDangerAction(double dt) {
		if (dangerSource != null && dangerSource.getState() == State.DEAD) {
			dangerSource = null;
		}
		if (dangerSource == null) {
			if (pos.distanceTo(dest) > 8) {
				pos = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
			}
			move(speed*dt*Math.exp((energy-100.0)*0.007));
			age += dt;
			energy -= 20*dt;
			if (energy < 0) energy = 0;
			else if (energy > 100) energy = 100;
			desire -= 20*dt;
			if (desire < 0) desire = 0;
			else if (desire > 100) desire = 100;
		}
		else {
			
		}
	}
	
	protected void doMateAction(double dt) {
		
	}

	@Override
	public void update(double dt) {
		if (state != State.DEAD) {
			if (0 > pos.getX() || pos.getX() > regionMngr.getWidth() || 0 > pos.getY() || pos.getY() > regionMngr.getHeight()) {
				while (pos.getX() >= regionMngr.getWidth()) pos = pos.plus(new Vector2D(pos.getX() - regionMngr.getWidth(), 0));  
				while (pos.getX() < 0) pos = pos.plus(new Vector2D(pos.getX() + regionMngr.getWidth(), 0));  
				while (pos.getY() >= regionMngr.getHeight()) pos = pos.plus(new Vector2D(pos.getY() - regionMngr.getHeight(), 0));  
				while (pos.getY() < 0) pos = pos.plus(new Vector2D(pos.getY() + regionMngr.getHeight(), 0));
				state = State.NORMAL;
			}
			if(energy < 0.0 || age > 8.0) {
				state = State.DEAD;
			}
			if (state != State.DEAD) {
				energy += regionMngr.getFood(this, dt);
			}
			
		}
	}

}
