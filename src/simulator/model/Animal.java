package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public abstract class Animal {
	
	//Constants for Animals
	final static double INIT_ENERGY = 100.0;
	final static double MUTATION_TOLERANCE = 0.2;
	final static double NEARBY_FACTOR = 60.0;
	final static double COLLISION_RANGE = 8;
	final static double HUNGER_DECAY_EXP_FACTOR = 0.007;
	final static double MAX_ENERGY = 100.0;
	final static double MAX_DESIRE = 100.0;
	
	//Attributes for Animals
	protected String geneticCode; //a non-empty character string representing the genetic code. 
	//Each subclass will assign a different value to this field. In principle, it is used to know 
	//if 2 animals can mate or not
	protected Diet diet; //indicates if the animal is an herbivore or carnivore
	protected State state; //the current state of the animal
	protected Vector2D dest;// the destination of the animal (the animal always has a destination, 
	//and when it reaches it, it chooses another, or changes it depending on whether it is following 
	protected Vector2D pos; // position of the animal
	//another animal or being chased by another animal)
	protected double energy; //the energy of the animal. When it reaches 0.0 the animal dies
	protected double speed; //the speed of the animal
	protected double age; //the age of the animal. When it reaches a maximum (depending on the type of animal) 
	//the animal dies
	protected double desire; //the desire of the animal, which changes during the simulation. We will use it to 
	//decide if an animal enters (or exits) a mating state
	protected double sightRange; //the radius of the animal's visual field (to decide which animals it can see)
	protected Animal mateTarjet; //a reference to an animal with which it wants to mate
	protected Animal baby; //a reference indicating if the animal is carrying a baby that hasn't been born yet
	protected AnimalMapView regionMngr; //is the region manager to be able to query information or perform 
	//corresponding operations (see the section The Region Manager). When we create the object, we initialize them 
	//to null, until the region manager initializes the animal by calling its init method
	protected SelectionStrategy mateStrategy; //is the selection strategy to search for a partner
	
	protected Animal(String geneticCode, Diet diet, double sightRange, double initSpeed, SelectionStrategy mateStrategy, Vector2D pos) throws IllegalArgumentException {
		if (geneticCode.isEmpty() || sightRange <= 0 || initSpeed <= 0 || mateStrategy == null)
			throw new IllegalArgumentException(); //maybe we could do the check in a auxiliary function so we can specify the variable 
											      //that gives rise to the exception without having a row of if-else here 
		//Initialize
		this.geneticCode = geneticCode;
		this.diet = diet;
		this.sightRange = sightRange;
		this.speed = Utils.getRandomizedParameter(initSpeed, 0.1);
		this.mateStrategy = mateStrategy;
		this.pos = pos;
		this.state = State.NORMAL;
		this.energy = INIT_ENERGY;
		this.desire = 0.0;
		this.dest = this.mateTarjet = this.baby = this.regionMngr = null;
	}//for creating initial objects (God (us) created them)
	
	protected Animal(Animal p1, Animal p2) {
		this.dest = this.mateTarjet = this.baby = this.regionMngr = null;
		this.state = State.NORMAL;
		this.desire = 0.0;	
		this.geneticCode = p1.geneticCode;
		this.diet = p1.diet;
		this.mateStrategy = p2.mateStrategy;
		this.energy = (p1.energy + p2.energy) / 2;
		this.pos = p1.pos.plus(Vector2D.getRandomVector(-1,1).scale(60.0*(Utils.RAND.nextGaussian()+1)));
		this.sightRange = Utils.getRandomizedParameter((p1.sightRange + p2.sightRange) / 2, 0.2);
		this.speed = Utils.getRandomizedParameter((p1.speed+p2.speed)/2, 0.2);
	}//when a star (animal) is born
	
	public void init(AnimalMapView regMngr) { // the region manager will invoke this method when adding the animal to the simulation
		this.regionMngr = regMngr;
		if(this.pos == null)
			this.pos = Vector2D.getRandomVector(-1,1).scale(60.0*(Utils.RAND.nextGaussian()+1));
		else {
			//TODO... I don't know how to use the code provided to adjust the position. It has to be used in 2D Vector class, in regionManager class, here??
		}
		this.dest = Vector2D.getRandomVector(-1,1).scale(60.0*(Utils.RAND.nextGaussian()+1)); // next position where the animal moves. I just used the position way of random
	}
	
	Animal deliverBaby() { //The simulator will invoke this method so that animals are born. Returns the attribute baby;
		Animal baby = this.baby;
		baby = null;
		return baby;
	}
	
	protected void move(double speed) { //To move with some speed
		 pos = pos.plus(dest.minus(pos).direction().scale(speed));
	}
	
	 protected void setState(State state) { // to react according to state
		 	this.state = state;
		     switch (state) {
		     case NORMAL:
		         setNormalStateAction();
		         break;
		     case HUNGER:
		    	 setHungerStateAction();
		    	 break;
		     case MATE:
		    	 setMateStateAction();
		    	 break;
		     case DANGER:
		    	 setDangerStateAction();
		    	 break;
		     case DEAD:
		    	 setDeadStateAction();
		    	 break;
		    default:
		    	break;
		     }
		 }
	 
	// To implement in each distinct animal
	abstract protected void setNormalStateAction();
	abstract protected void setMateStateAction();
	abstract protected void setHungerStateAction();
	abstract protected void setDangerStateAction();
	abstract protected void setDeadStateAction();
	
	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
	    
	    JSONArray pos = new JSONArray();
	    pos.put(28.90696391797469);
	    pos.put(22.009772194487613);
	    
	    obj.put("pos", pos);
	    obj.put("gcode", "Sheep");
	    obj.put("diet", "HERBIVORE");
	    obj.put("state", "NORMAL");
	    
	    return obj;
	}
	


}
