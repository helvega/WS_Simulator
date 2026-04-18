package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class StatusBar extends JPanel implements EcoSysObserver {

	  private Controller ctrl;
	
	  JLabel time, animals, dimensions;
	  
	  public StatusBar(Controller ctrl) {
	    initGUI();
	    this.ctrl = ctrl;
	    ctrl.addObserver(this);
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
	    
	    this.time = new JLabel("Time: 0");
	    this.time.setHorizontalAlignment(JLabel.LEFT);
	    this.add(time);
	    JSeparator s = new JSeparator(JSeparator.VERTICAL);
	    s.setPreferredSize(new Dimension(10, 20));
	    this.add(s);
	    
	    this.animals = new JLabel("Total Animals: 0");
	    this.animals.setHorizontalAlignment(JLabel.CENTER);
	    this.add(animals);
	    this.add(s);
	    
	    this.dimensions = new JLabel("Dimensions: 0x0 0x0");
	    this.dimensions.setHorizontalAlignment(JLabel.RIGHT);
	    this.add(dimensions);
	  }

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		  setValues(time, map, animals);
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		  setValues(time, map, animals);
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  setValues(time, map, animals);
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		  setValues(time, map, animals);
	  }
	  
	  private void setValues(double time, MapInfo map, List<AnimalInfo> animals) {
			this.time.setText("Time: " + String.format("%.3f", time));
			this.animals.setText("Total Animals: " + animals.size());
			int cols = map.getCols();
			int rows = map.getRows();
			int width = map.getWidth();
			int height = map.getHeight();
			dimensions.setText(String.valueOf(width) + "x" + String.valueOf(height) + " " + String.valueOf(cols) + "x"
					+ String.valueOf(rows));
	}
}
