package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;
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
	    // Set contentPane to mainPanel.
	    mainPanel.setPreferredSize(new Dimension(ctrl.getMapWidth(), ctrl.getMapHeight()));
	    setContentPane(mainPanel);
	    // Create the viewer and add it to mainPanel (in the center).
	    viewer = new MapViewer();
	    mainPanel.add(viewer, BorderLayout.CENTER);

	    //		In the windowClosing method, remove 'MapWindow.this' from the
	    //      observers.
	    this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				ctrl.removeObserver(MapWindow.this);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			} });

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
		  viewer.reset(time, map, animals);
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		viewer.reset(time, map, animals);
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  viewer.update(animals, time);
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		  
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		viewer.update(animals, time);
	  }
	}