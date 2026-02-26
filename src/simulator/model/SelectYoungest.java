package simulator.model;

import java.util.List;

public class SelectYoungest implements SelectionStrategy{

    public SelectYoungest(){}
    
    public Animal select(Animal a, List<Animal> as) {
        Animal e = null;
        double youngest_age = 1200;

        for(Animal an: as){
           if(an.getAge() < youngest_age) {
                youngest_age = an.getAge();
                e = an;
           }           
        }

        return e;
    }


}
