package simulator.view;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;

class ControlPanel extends JPanel {

	  private Controller ctrl;
	  private ChangeRegionsDialog changeRegionsDialog;

	  private JToolBar toolaBar;
	  private JFileChooser fc;
	  private boolean stopped = true; // used for the buttons run/stop
	  private JButton quitButton;

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
	    //      between the components that need it.

	    // Quit Button
	    this.toolaBar.add(Box.createGlue()); // this aligns the button to the right
	    this.toolaBar.addSeparator();
	    this.quitButton = new JButton();
	    this.quitButton.setToolTipText("Quit");
	    this.quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
	    this.quitButton.addActionListener((e) -> ViewUtils.quit(this));
	    this.toolaBar.add(quitButton);

	    // TODO Initialize this.fc with an instance of JFileChooser. To make it always
	    //      open in the examples folder you can use:
	    //
	    //      this.fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));
	   
	    // TODO Initialize this.changeRegionsDialog with a corresponding instance.

	  } 
	  // TODO The rest of methods.
	}

