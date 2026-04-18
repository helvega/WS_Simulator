package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
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
		  
		this.setLayout(new BorderLayout());
		  
	    // TODO add a titled border to the JPanel, with the text this.title
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		  
	    // TODO add a JTable (with a vertical scroll bar) that uses
	    //      this.tableModel
		  
		JTable newTable = new JTable(tableModel);
		
		newTable.setShowHorizontalLines(false);
		newTable.setShowVerticalLines(false);
		
		
		this.add(newTable);
		  
		this.add(new JScrollPane(newTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		this.setBorder(BorderFactory.createTitledBorder(b, this.title));
	  }
	}