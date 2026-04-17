package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class MapWindow extends JFrame implements EcoSysObserver {

	  /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;
	  private Controller ctrl;
	  private AbstractMapViewer viewer;
	  private Frame parent;

	  public MapWindow(Frame parent, Controller ctrl) {
	    super("[MAP VIEWER]");
	    this.ctrl = ctrl;
	    this.parent = parent;
	    intiGUI();
	    ctrl.addObserver(this);
	  }

	private void intiGUI() {
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    // TODO Set contentPane to mainPanel.
	    
	    // Create the viewer and add it to mainPanel (in the center).
	    viewer = new MapViewer();
	    mainPanel.add(viewer, BorderLayout.CENTER);

	    // TODO In the windowClosing method, remove 'MapWindow.this' from the
	    //      observers.
	    // addWindowListener(new WindowListener() { ... });

	    pack();
	    if (this.parent != null)
	      setLocation(//
	             this.parent.getLocation().x + parent.getWidth()/2 - getWidth()/2,//
	             this.parent.getLocation().y + parent.getHeight()/2 - getHeight()/2);
	      setResizable(false);
	      setVisible(true);
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