package simulator.model;

import java.util.List;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Wolf extends Animal {
	
	private Animal huntTarget; // another animal that is considered a danger at a given moment
	private SelectionStrategy huntingStrategy; // another animal that is considered a danger at a given moment
	
	public Wolf(SelectionStrategy mateStrategy, SelectionStrategy huntingStrategy,  Vector2D pos) {
		super("wolf", Diet.CARNIVORE, 50.0, 60.0, mateStrategy, pos);
		this.huntingStrategy = huntingStrategy;
	}
	
	protected Wolf(Wolf p1, Animal p2) {
		super(p1, p2);
		this.huntingStrategy = p1.huntingStrategy;
		huntTarget = null;
	}
	
	@Override
	protected void setNormalStateAction() {
		huntTarget = null;
		mateTarget = null;
		// TODO...

	}

	@Override
	protected void setMateStateAction() {
		huntTarget = null;
		// TODO...
	}

	@Override
	protected void setHungerStateAction() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setDangerStateAction() {
		mateTarget = null;
		// TODO...

	}

	@Override
	protected void setDeadStateAction() {
		huntTarget = null;
		mateTarget = null;
	}
	
	protected void doNormalAction(double dt) {
		if (pos.distanceTo(dest) < 8) {
			dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
		}
		move(speed*dt*Math.exp((energy-100.0)*0.007));
		age += dt;
		energy -= 18*dt;
		if (energy < 0) energy = 0;
		else if (energy > 100) energy = 100;
		desire += 30*dt;
		if (desire < 0) desire = 0;
		else if (desire > 100) desire = 100;
		
		if (energy < 50) {
			setState(State.HUNGER);
		}
		
		else if (desire > 65) {
			setState(State.MATE);
		}
	}
	
	protected void doHungerAction(double dt) {
		List<Animal> preys = regionMngr.getAnimalsInRange(this, p -> p.getDiet() == Diet.HERBIVORE);
		if (huntTarget == null || huntTarget.getState() == State.DEAD || preys.contains(huntTarget)) {
			huntTarget = null;
			if (!preys.isEmpty()) {
				huntTarget = huntingStrategy.select(this, preys);
			}
		}
		if (huntTarget == null) {
			if (pos.distanceTo(dest) < 8) {
				dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
			}
			move(speed*dt*Math.exp((energy-100.0)*0.007));
			age += dt;
			energy -= 18*dt;
			if (energy < 0) energy = 0;
			desire += 30*dt;
			if (desire > 100) desire = 100;
		}
		else {
			dest = huntTarget.getPosition();
			move(3.0 * speed * dt * Math.exp((energy - 100.0) * 0.007));
			age += dt;
			energy -= 18.0*1.2*dt;
			if (energy < 0) energy = 0;
			else if (energy > 100) energy = 100;
			desire += 30*dt;
			if (desire < 0) desire = 0;
			else if (desire > 100) desire = 100;
			if (pos.distanceTo(huntTarget.getPosition()) < 8) {
				huntTarget.setState(State.DEAD);
				huntTarget = null;
				energy += 50;
				if (energy < 0) energy = 0;
				else if (energy > 100) energy = 100;
			}
		}
		
		if (energy > 50) {
			if (desire > 65) setState(State.MATE);
			else setState(State.NORMAL);
		}
	}
	
	protected void doMateAction(double dt) {
		if (mateTarget != null && mateTarget.getState() == State.DEAD) {
			mateTarget = null;
		}
		
		if (mateTarget == null) {
			List<Animal> mate_partners = regionMngr.getAnimalsInRange(this, p -> p.getGeneticCode() == this.geneticCode);
			if (!mate_partners.isEmpty()) mateTarget = mateStrategy.select(this, mate_partners);
		}
		if (mateTarget == null) {
				if (pos.distanceTo(dest) > 8) {
					dest = Vector2D.getRandomVector(0, regionMngr.getWidth() > regionMngr.getHeight() ? regionMngr.getWidth() : regionMngr.getHeight());
				}
				move(speed*dt*Math.exp((energy-100.0)*0.007));
				age += dt;
				energy -= 18*dt;
				if (energy < 0) energy = 0;
				desire += 30*dt;
				if (desire > 100) desire = 100;
			}
		
		if (mateTarget != null) {
			dest = mateTarget.getPosition();
			move(3.0*speed*dt*Math.exp((energy-100.0)*0.007));
			age += dt;
			energy -= 18.0*1.2*dt;
			if (energy < 0) energy = 0;
			desire += 30*dt;
			if (desire > 100) desire = 100;
			
			if (pos.distanceTo(dest) < 8) {
				desire = mateTarget.desire = 0;
				if (Utils.RAND.nextInt(100) < 75 && baby == null) {
					baby = new Wolf(this, mateTarget);
				}
				energy -= 10;
				mateTarget = null;
			}
		}
		
		if (energy < 50) {
			setState(State.HUNGER);
		}
		else if(desire < 65) setState(State.NORMAL);
	}

	@Override
	public void update(double dt) {
		
		if (state != State.DEAD) {
			
			switch(state) {
			case NORMAL:
				doNormalAction(dt);
				break;
				
			case HUNGER:
				doHungerAction(dt);
				break;
				
			case MATE:
				doMateAction(dt);
				break;
				
			case DANGER:
				break;
				
			case DEAD:
				break;
			}
			
			if (0 > pos.getX() || pos.getX() > regionMngr.getWidth() || 0 > pos.getY() || pos.getY() > regionMngr.getHeight()) {
				pos = fixPosition();
				setState(State.NORMAL);
			}
			
			if(energy <= 0.0 || age > 14.0) {
				setState(State.DEAD);
			}
			
			if (state != State.DEAD) {
				energy += regionMngr.getFood(this, dt);
			}
		}
	}

}
