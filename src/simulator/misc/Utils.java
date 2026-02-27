package simulator.misc;

import java.util.Random;

public class Utils {
	public static final Random RAND = new Random();

	public static double constrainValueInRange(double value, Object object, Object object2) {
		value = value > object2 ? object2 : value;
		value = value < object ? object : value;
		return value;
	}

	public static double getRandomizedParameter(double value, double tolerance) {
		assert (tolerance > 0 && tolerance <= 1);
		double t = (RAND.nextDouble() - 0.5) * 2 * tolerance;
		return value * (1 + t);
	}

}
