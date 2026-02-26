package simulator.misc;

public class Constants {
    // used in Animal and subclasses
	final static double INIT_ENERGY = 100.0;
	final static double MUTATION_TOLERANCE = 0.2;
	final static double NEARBY_FACTOR = 60.0;
	final static double COLLISION_RANGE = 8;
	final static double HUNGER_DECAY_EXP_FACTOR = 0.007;
	final static double MAX_ENERGY = 100.0;
	final static double MAX_DESIRE = 100.0;

    // used in Sheep
	final static String SHEEP_GENETIC_CODE = "Sheep";
	final static double INIT_SIGHT_SHEEP = 40;
	final static double INIT_SPEED_SHEEP = 35;
	final static double BOOST_FACTOR_SHEEP = 2.0;
	final static double MAX_AGE_SHEEP = 8;
	final static double FOOD_DROP_BOOST_FACTOR_SHEEP = 1.2;
	final static double FOOD_DROP_RATE_SHEEP = 20.0;
	final static double DESIRE_THRESHOLD_SHEEP = 65.0;
	final static double DESIRE_INCREASE_RATE_SHEEP = 40.0;
	final static double PREGNANT_PROBABILITY_SHEEP = 0.9;

    // used in Wolf
	final static String WOLF_GENETIC_CODE = "Wolf";
	final static double INIT_SIGHT_WOLF = 50;
	final static double INIT_SPEED_WOLF = 60;
	final static double BOOST_FACTOR_WOLF = 3.0;
	final static double MAX_AGE_WOLF = 14.0;
	final static double FOOD_THRSHOLD_WOLF = 50.0;
	final static double FOOD_DROP_BOOST_FACTOR_WOLF = 1.2;
	final static double FOOD_DROP_RATE_WOLF = 18.0;
	final static double FOOD_DROP_DESIRE_WOLF = 10.0;
	final static double FOOD_EAT_VALUE_WOLF = 50.0;
	final static double DESIRE_THRESHOLD_WOLF = 65.0;
	final static double DESIRE_INCREASE_RATE_WOLF = 30.0;
	final static double PREGNANT_PROBABILITY_WOLF = 0.75;

	// used in DefaultRegion
	final static double FOOD_EAT_RATE_HERBS = 60.0;
	final static double FOOD_SHORTAGE_TH_HERBS = 5.0;
	final static double FOOD_SHORTAGE_EXP_HERBS = 2.0;

	// used in DynamicSupplyRegion
	final static double INIT_FOOD = 100.0;
	final static double FACTOR = 2.0;
	
	// desc for builders
	public final static String DESC_SELECT_FIRST = "build a Select Firs class";
	public final static String TYPE_SELECT_FIRST = "first";
	public final static String DESC_SELECT_CLOSEST = "build a Select Closest class";
	public final static String TYPE_SELECT_CLOSEST = "closest";
	public final static String DESC_SELECT_YOUNGEST = "build a Select Youngest class";
	public final static String TYPE_SELECT_YOUNGEST = "youngest";
	public final static String DESC_SHEEP_BUILDER = "build a Sheep class";
	public final static String TYPE_SHEEP_BUILDER = "sheep";
	public final static String DESC_WOLF_BUILDER = "build a Wolf class";
	public final static String TYPE_WOLF_BUILDER = "wolf";
	public final static String DESC_DEFAULT_REGION_BUILDER= "build a Default Region class";
	public final static String TYPE_DEFAULT_REGION_BUILDER = "default";
	public final static String DESC_DYNAMIC_REGION_BUILDER= "build a Dynamic Supply Regiion class";
	public final static String TYPE_DYNAMIC_REGION_BUILDER = "dynamic";

}
