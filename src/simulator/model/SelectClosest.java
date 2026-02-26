package simulator.model;

import static java.lang.Math.abs;
import java.util.List;

public class SelectClosest implements SelectionStrategy {

    public SelectClosest(){}

    public Animal select(Animal a, List<Animal> as) {
        Animal e;
        int closest_idx = 0;
        double current_pos_diff, closest = abs(a.pos.getX() - as.get(0).pos.getX()) + abs(a.pos.getY() - as.get(0).pos.getY());

        for(int i = 1; i < as.size(); i++){
            current_pos_diff = abs(a.pos.getX() - as.get(i).pos.getX()) + abs(a.pos.getY() - as.get(i).pos.getY());
            if (current_pos_diff < closest) {
                closest = current_pos_diff;
                closest_idx = i; 
            }
        }

        e = as.get(closest_idx);
        return e;
    }

}
