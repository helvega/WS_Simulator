package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

import simulator.control.Controller;

class ControlPanel extends JPanel {

	  private Controller ctrl;
	  private ChangeRegionsDialog changeRegionsDialog;

	  private JToolBar toolaBar;
	  private JFileChooser fc;
	  private boolean stopped = true; // used for the buttons run/stop
	  private JButton quitButton, openButton, viewerButton, regionsButton, runButton, stopButton;
	  private JSpinner steps;
	  private JTextField deltaTime;
	  private double dt;

	  // TODO add fields here

	  ControlPanel(Controller ctrl) {
	    this.ctrl = ctrl;
	    initGUI();
	  }

	  private void initGUI() {
	    setLayout(new BorderLayout());
	    toolaBar = new JToolBar();
	    toolaBar.setPreferredSize(new Dimension(200, 40));
	    add(toolaBar, BorderLayout.PAGE_START);
	    

	    // TODO create the different widgets (buttons, etc.) and add them to toolaBar.
	    //      All of them must have their corresponding tooltip. You can use
	    //      this.toolaBar.addSeparator() to add the vertical separator line
	    //      between the components that need it
	    
	    // Button to open files
	    this.openButton = new JButton();
//	    this.openButton.setToolTipText("Open"); Not really used
	    this.openButton.setIcon(new ImageIcon("resources/icons/open.png"));
	    this.openButton.addActionListener((e) -> openFiles());
	    this.openButton.setToolTipText("Opens a file from which to load the data");
	    this.toolaBar.addSeparator();
	    this.toolaBar.add(openButton);
	    
	    
	    // Button to open the map
	    this.toolaBar.add(Box.createGlue());
	    this.viewerButton = new JButton();
//	    this.viewerButton.setToolTipText("Viewer"); Not really used
	    this.viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
	 // TODO create an instance of MapWindow
	    this.viewerButton.addActionListener((e) -> new MapWindow(ViewUtils.getWindow(this), ctrl));
	    this.viewerButton.setToolTipText("Opens a map representation");
	    this.toolaBar.add(viewerButton);
	    
	    // Button to open the region viewer
	    this.regionsButton = new JButton();
//	    this.regionsButton.setToolTipText("Regions"); Not really used
	    this.regionsButton.setIcon(new ImageIcon("resources/icons/regions.png"));
	    this.regionsButton.addActionListener((e) -> this.changeRegionsDialog.open(ViewUtils.getWindow(this)));
	    this.regionsButton.setToolTipText("Opens the region viewer");
	    this.toolaBar.add(regionsButton);
	    this.toolaBar.addSeparator();
	    this.toolaBar.add(Box.createGlue());
	    
	    // Button to start the simulation
	    this.runButton = new JButton();
//	    this.runButton.setToolTipText("Run"); Not really used
	    this.runButton.setIcon(new ImageIcon("resources/icons/run.png"));
	    this.runButton.addActionListener((e) -> run());
	    this.runButton.setToolTipText("Runs the simulation");
	    this.toolaBar.add(runButton);
	    
	    // Button to stop the simulation
	    this.stopButton = new JButton();
//	    this.stopButton.setToolTipText("Stop"); Not really used
	    this.stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
	    this.stopButton.addActionListener((e) -> this.stopped = true);
	    this.stopButton.setToolTipText("Stops the simulation if it is already running");
	    this.toolaBar.add(stopButton);
	    
	    // Spinner and Field
	    JLabel stepText = new JLabel("Steps:");
	    stepText.setToolTipText("Sets number of steps to the simulation");
	    this.toolaBar.add(stepText);
	    this.steps = new JSpinner();
	    this.steps.setPreferredSize(new Dimension(50, 10));
	    this.toolaBar.add(steps);
	    JLabel deltaText = new JLabel("Delta-time:");
	    deltaText.setToolTipText("Time in seconds for each step");
	    this.toolaBar.add(deltaText);
	    this.deltaTime = new JTextField();
	    this.toolaBar.add(deltaTime);
	    
	    
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
	   
	    //Initialize this.changeRegionsDialog with a corresponding instance.
	    this.changeRegionsDialog = new ChangeRegionsDialog(ctrl);

	  } 
	  
	  private void enableButtons(boolean enabled) {
		  openButton.setEnabled(enabled);
	      viewerButton.setEnabled(enabled);
	      regionsButton.setEnabled(enabled);
	      runButton.setEnabled(enabled);
	      quitButton.setEnabled(enabled);
	      steps.setEnabled(enabled);
	      deltaTime.setEnabled(enabled);
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
		      // Think of an error message
		      ViewUtils.showErrorMsg(e.getMessage());
		      
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

