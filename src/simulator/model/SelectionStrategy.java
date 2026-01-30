package simulator.model;

import java.util.List;

public interface SelectionStrategy {  
	Animal select(Animal a, List<Animal> as);  
	//Animal selectFirst(Animal a, List<Animal> as); I don't know where these functions must be implemented
	//Animal selectClosest(Animal a, List<Animal> as);
	//Animal selectYounger(Animal a, List<Animal> as);
	//Animal selectNerd(Animal a, List<Animal> as);
}
