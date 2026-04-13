package simulator.view;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {

	  private String title;
	  TableModel tableModel;

	  InfoTable(String title, TableModel tableModel) {
	    this.title = title;
	    this.tableModel = tableModel;
	    initGUI();
	  }

	  private void initGUI() {
	    // TODO change the panel's layout to BorderLayout()
	    // TODO add a titled border to the JPanel, with the text this.title
	    // TODO add a JTable (with a vertical scroll bar) that uses
	    //      this.tableModel
	  }
	}