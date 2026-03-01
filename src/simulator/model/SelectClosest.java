package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class SelectClosest implements SelectionStrategy {

    public SelectClosest(){}

    public Animal select(Animal a, List<Animal> as) {
        Animal e;
        int closest_idx = 0;
        double current_pos_diff, closest = computeClosestDistance(a.getPosition(), as.get(0).getPosition());

        for(int i = 1; i < as.size(); i++){
            current_pos_diff = computeClosestDistance(a.getPosition(), as.get(i).getPosition());
            if (current_pos_diff < closest) {
                closest = current_pos_diff;
                closest_idx = i; 
            }
        }

        e = as.get(closest_idx);
        return e;
   }
    
   private double computeClosestDistance(Vector2D a, Vector2D as) {
	   double x = 0;
	   x = Math.pow(a.getX() - as.getX(), 2) + Math.pow(a.getY() - as.getY(), 2);
	   x = Math.sqrt(x);
	   return x;
   } 

}
