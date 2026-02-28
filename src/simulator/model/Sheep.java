package simulator.model;

import simulator.misc.Utils;
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
		mateTarget = null;
	}

	@Override
	protected void setMateStateAction() {
		dangerSource = null;
	}

	@Override
	protected void setHungerStateAction() {
	}

	@Override
	protected void setDangerStateAction() {
		mateTarget = null;
	}

	@Override
	protected void setDeadStateAction() {
		dangerSource = null;
		mateTarget = null;
	}
	
	protected void doNormalAction(double dt) {
		if (pos.distanceTo(dest) < 8) {
			dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
		}
		move(speed * dt * Math.exp((energy - 100.0) * 0.007));
		age += dt;
		energy -= 20 * dt;
		if (energy < 0) energy = 0;
		if (energy > 100) energy = 100;
		desire += 20 * dt;
		if (desire < 0) desire = 0;
		if (desire > 100) desire = 100;
		
		if (dangerSource == null) {
			List<Animal> dangerous_animals = regionMngr.getAnimalsInRange(this, p -> p.getDiet() == Diet.CARNIVORE);
			if (!dangerous_animals.isEmpty()) {
				dangerSource = dangerStrategy.select(this, dangerous_animals);
				setState(State.DANGER);
			}
		}
		if (dangerSource == null) {
			if (desire > 65) {
				setState(State.MATE);
			}
		}
	}
	
	protected void doDangerAction(double dt) {
		if (dangerSource != null && dangerSource.getState() == State.DEAD) {
			dangerSource = null;
		}
		if (dangerSource == null) {
			if (pos.distanceTo(dest) < 8) {
				dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
			}
			move(speed*dt*Math.exp((energy-100.0)*0.007));
			age += dt;
			energy -= 20*dt;
			if (energy < 0) energy = 0;
			desire += 20*dt;
			if (desire > 100) desire = 100;
		}
		else {
			dest = pos.plus(pos.minus(dangerSource.getPosition()).direction());
			move(2.0*speed*dt*Math.exp((energy-100.0)*0.007));
			age += dt;
			energy -= 20.0*1.2*dt;
			if (energy < 0) energy = 0;
			else if (energy > 100) energy = 100;
			desire += 40*dt;
			if (desire > 100) desire = 100;
		}
		
		List<Animal> dangerous_animals = regionMngr.getAnimalsInRange(this, p -> p.getDiet() == Diet.CARNIVORE);
		if (dangerSource == null || !dangerous_animals.contains(dangerSource)) {
			if (!dangerous_animals.isEmpty()) {
				dangerSource = dangerStrategy.select(this, dangerous_animals);
				setState(State.DANGER);
			}
			if (dangerSource == null) {
				if (desire > 65) setState(State.MATE);
				else setState(State.NORMAL);
			}
		}
	}
	
	protected void doMateAction(double dt) {
		List<Animal> mate_partners = regionMngr.getAnimalsInRange(this, p -> p.getGeneticCode() == this.geneticCode);
		if ((mateTarget != null && mateTarget.getState() == State.DEAD) || !mate_partners.contains(mateTarget)) {
			mateTarget = null;
		}
		
		if (mateTarget == null) {
			if (!mate_partners.isEmpty()) 
				mateTarget = mateStrategy.select(this, mate_partners);
		}
		if (mateTarget == null) {
				if (pos.distanceTo(dest) < 8) {
					dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
				}
				move(speed*dt*Math.exp((energy-100.0)*0.007));
				age += dt;
				energy -= 20*dt;
				if (energy < 0) energy = 0;
				if (energy > 100) energy = 100;
				desire += 40*dt;
				if (desire > 100) desire = 100;
				if(desire < 0) desire = 0;
			}
		
		if (mateTarget != null) {
			dest = mateTarget.getPosition();
			move(2.0 * speed * dt * Math.exp((energy - 100.0) * 0.007));
			age += dt;
			energy -= 20.0 * 1.2 * dt;
			if (energy < 0) energy = 0;
			else if (energy > 100) energy = 100;
			desire += 40 * dt;
			if (desire > 100) desire = 100;
			
			if (pos.distanceTo(dest) > 8) {
				desire = mateTarget.desire = 0;
				if (Utils.RAND.nextInt(10) != 9 && baby == null) {
					baby = new Sheep(this, mateTarget);
				}
				mateTarget = null;
			}
		}
		
		List<Animal> dangerous_animals = regionMngr.getAnimalsInRange(this, p -> p.getDiet() == Diet.CARNIVORE);
		if (dangerSource == null && !dangerous_animals.isEmpty()) {
			dangerSource = dangerStrategy.select(this, dangerous_animals);
		}
		if (dangerSource == null) {
			if(desire < 65) setState(State.NORMAL);
		}
		else {
			setState(State.DANGER);
		}
	}

	@Override
	public void update(double dt) {
		
		if (state != State.DEAD) {
			switch(state) {
			case NORMAL:
				doNormalAction(dt);
				break;
				
			case DANGER:
				doDangerAction(dt);
				break;
				
			case MATE:
				doMateAction(dt);
				break;
				
			case HUNGER:
				break;
				
			case DEAD:
				break;
			}
			
			if (0 > pos.getX() || pos.getX() > regionMngr.getWidth() || 0 > pos.getY() || pos.getY() > regionMngr.getHeight()) {
				pos = fixPosition();	
				setState(State.NORMAL);
			}
			if(energy < 0.0 || age > 8.0) {
				setState(State.DEAD);
			}
			if (state != State.DEAD) {
				energy += regionMngr.getFood(this, dt);
			}
		}
	}

}
