package simulator.view;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;

class ControlPanel extends JPanel {

	  private Controller ctrl;
	  private ChangeRegionsDialog changeRegionsDialog;

	  private JToolBar toolaBar;
	  private JFileChooser fc;
	  private boolean stopped = true; // used for the buttons run/stop
	  private JButton quitButton, openButton, viewerButton, regionsButton, runButton, stopButton;
	  private double dt;

	  // TODO add fields here

	  ControlPanel(Controller ctrl) {
	    this.ctrl = ctrl;
	    initGUI();
	  }

	  private void initGUI() {
	    setLayout(new BorderLayout());
	    toolaBar = new JToolBar();
	    add(toolaBar, BorderLayout.PAGE_START);

	    // TODO create the different widgets (buttons, etc.) and add them to toolaBar.
	    //      All of them must have their corresponding tooltip. You can use
	    //      this.toolaBar.addSeparator() to add the vertical separator line
	    //      between the components that need it
	    
	    // Button to open files
	    this.toolaBar.add(Box.createGlue());
	    this.openButton = new JButton();
	    this.openButton.setToolTipText("Open");
	    this.openButton.setIcon(new ImageIcon("resources/icons/open.png"));
	    this.openButton.addActionListener((e) -> openFiles());
	    this.openButton.setToolTipText("Opens a file from which to load the data");
	    this.toolaBar.add(openButton);
	    this.toolaBar.addSeparator();
	    
	    // Button to open the map
	    this.toolaBar.add(Box.createGlue());
	    this.viewerButton = new JButton();
	    this.viewerButton.setToolTipText("Viewer");
	    this.viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
	 // TODO create an instance of MapWindow
	    this.viewerButton.addActionListener((e) -> ViewUtils.quit(this));
	    this.viewerButton.setToolTipText("Opens a map representation");
	    this.toolaBar.add(viewerButton);
	    
	    // Button to open the region viewer
	    this.regionsButton = new JButton();
	    this.regionsButton.setToolTipText("Regions");
	    this.regionsButton.setIcon(new ImageIcon("resources/icons/regions.png"));
	    this.regionsButton.addActionListener((e) -> this.changeRegionsDialog.open(ViewUtils.getWindow(this)));
	    this.regionsButton.setToolTipText("Opens the region viewer");
	    this.toolaBar.add(regionsButton);
	    this.toolaBar.addSeparator();
	    this.toolaBar.add(Box.createGlue());
	    
	    // Button to start the simulation
	    this.runButton = new JButton();
	    this.runButton.setToolTipText("Run");
	    this.runButton.setIcon(new ImageIcon("resources/icons/run.png"));
	    this.runButton.addActionListener((e) -> run());
	    this.runButton.setToolTipText("Runs the simulation");
	    this.toolaBar.add(runButton);
	    
	    // Button to stop the simulation
	    this.stopButton = new JButton();
	    this.stopButton.setToolTipText("Stop");
	    this.stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
	    this.stopButton.addActionListener((e) -> this.stopped = true);
	    this.stopButton.setToolTipText("Stops the simulation if it is already running");
	    this.toolaBar.add(stopButton);

	    // Quit Button
	    this.toolaBar.add(Box.createGlue()); // this aligns the button to the right
	    this.toolaBar.addSeparator();
	    this.quitButton = new JButton();
	    this.quitButton.setToolTipText("Quit");
	    this.quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
	    this.quitButton.addActionListener((e) -> ViewUtils.quit(this));
	    this.quitButton.setToolTipText("Closes the control panel");
	    this.toolaBar.add(quitButton);

	    fc = new JFileChooser();
	    this.fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));
	   
	    // TODO Initialize this.changeRegionsDialog with a corresponding instance.

	  } 
	  
	  private void enableButtons(boolean enabled) {
		  openButton.setEnabled(enabled);
	      viewerButton.setEnabled(enabled);
	      regionsButton.setEnabled(enabled);
	      runButton.setEnabled(enabled);
	      quitButton.setEnabled(enabled);
	  }
	  
	  private void run() {
		  this.stopped = false;
		  enableButtons(stopped);
		  
	      // TODO get n and dt
	      runSim(0, dt);
	  }
	  
	  private void runSim(int n, double dt) {
		  if (n > 0 && !this.stopped) {
		    try {
		      this.ctrl.advance(dt);
		      SwingUtilities.invokeLater(() -> runSim(n - 1, dt));
		      
		    } catch (Exception e) {
		      // TODO think of an error message
		      ViewUtils.showErrorMsg("Idk what to say here");
		      
		      this.stopped = true;
		      enableButtons(stopped);
		    }
		  } else {
		      this.stopped = true;
		      enableButtons(stopped);
		  }
		}
	  
	  private void openFiles() {
		  int returnVal = this.fc.showOpenDialog(ViewUtils.getWindow(this));
		  if (returnVal == JFileChooser.APPROVE_OPTION) {
			  File file = fc.getSelectedFile();
			  System.out.println("Loading: " + file.getName());
			  
			  // TODO convert to JSon
			  this.ctrl.reset(returnVal, returnVal, returnVal, returnVal);
		  } else {
			  System.out.println("Load cancelled by user.");
		  }
	  }
	  
	  // TODO The rest of methods.
	}

