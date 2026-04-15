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

	  // TODO Add necessary fields.
	
	  JLabel time, animals, dimensions;
	  
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
		// TODO Auto-generated method stub
		this.time.setText("Time: " + time);
		this.animals.setText("Total Animals: " + animals.size());
		this.dimensions.setText("Dimensions: " + map.getRegionWidth() + "x" + map.getRegionHeight() + " " + map.getCols() + map.getHeight());
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		 this.time.setText("Time: " + time);
		 this.animals.setText("Total Animals: " + animals.size());
		 this.dimensions.setText("Dimensions: " + map.getRegionWidth() + "x" + map.getRegionHeight() + " " + map.getCols() + map.getHeight());
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub
		 this.time.setText("Time: " + time);
		 this.animals.setText("Total Animals: " + animals.size());
		 this.dimensions.setText("Dimensions: " + map.getRegionWidth() + "x" + map.getRegionHeight() + " " + map.getCols() + map.getHeight());
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		 this.dimensions.setText("Dimensions: " + map.getRegionWidth() + "x" + map.getRegionHeight() + " " + col + row);
		
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub
		 this.time.setText("Time: " + time);
		 this.animals.setText("Total Animals: " + animals.size());
		 this.dimensions.setText("Dimensions: " + map.getRegionWidth() + "x" + map.getRegionHeight() + " " + map.getCols() + map.getHeight());
		
	  }

	  // TODO The rest of methods.
	}
