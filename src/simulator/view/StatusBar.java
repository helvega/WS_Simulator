package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class StatusBar extends JPanel implements EcoSysObserver {

	  // TODO Add necessary fields.

	  StatusBar(Controller ctrl) {
	    initGUI();
	    // TODO Register the 'this' object as an observer.
	  }

	  private void initGUI() {
	    this.setLayout(new FlowLayout(FlowLayout.LEFT));
	    this.setBorder(BorderFactory.createBevelBorder(1));

	    // TODO Create several JLabels for the time, the number of animals, and the
	    //      dimension and add them to the panel. You can use the following code
	    //      to add a vertical separator:
	    //
	    //     JSeparator s = new JSeparator(JSeparator.VERTICAL);
	    //     s.setPreferredSize(new Dimension(10, 20));
	    //     this.add(s);
	  }

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub
		
	  }

	  // TODO The rest of methods.
	}
