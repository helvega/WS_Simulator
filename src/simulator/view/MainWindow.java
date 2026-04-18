package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import simulator.control.Controller;

public class MainWindow extends JFrame {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;

	  public MainWindow(Controller ctrl) {
	    super("[ECOSYSTEM SIMULATOR]");
	    this.ctrl = ctrl;
	    initGUI();
	  }

	  private void initGUI() {
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    mainPanel.setPreferredSize(new Dimension(500, 500));
	    setContentPane(mainPanel);

	    // 	create a ControlPanel and add it to PAGE_START of mainPanel
	    JPanel controlPanel = new ControlPanel(this.ctrl);
	    mainPanel.add(controlPanel, BorderLayout.PAGE_START);

	    // 	 	create a StatusBar and add it to PAGE_END of mainPanel
	    JPanel statusBar = new StatusBar(this.ctrl);
	    mainPanel.add(statusBar, BorderLayout.PAGE_END);

	    // A panel for the tables (it uses vertical BoxLayout)
	    JPanel contentPanel = new JPanel();
	    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	    mainPanel.add(contentPanel, BorderLayout.CENTER);

	    // 		create the species table and add it to contentPanel.
	    //      Use setPreferredSize(new Dimension(500, 250)) to set the size.
	    InfoTable speciesTable = new InfoTable("Species", new SpeciesTableModel(ctrl));
	    speciesTable.setPreferredSize(new Dimension(500, 100));
	    contentPanel.add(speciesTable);

	    // 	Create the regions table and add it to contentPanel.
	    //      Usa setPreferredSize(new Dimension(500, 250)) to set the size.
	    InfoTable regionsTable = new InfoTable("Regions", new RegionsTableModel(ctrl));
	    regionsTable.setPreferredSize(new Dimension(500, 250));
	    contentPanel.add(regionsTable);

	    // 	Call ViewUtils.quit(MainWindow.this) in the windowClosing method.
	    this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		if(e.getID() == WindowEvent.WINDOW_CLOSING) {
	    			ViewUtils.quit(MainWindow.this);
	    		}
	    	}
	    });

	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack();
	    setVisible(true);
	    this.setResizable(false);
	   }
	}