package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.launcher.Main;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class ChangeRegionsDialog extends JDialog implements EcoSysObserver {

	  private DefaultComboBoxModel<String> regionsModel;
	  private DefaultComboBoxModel<String> fromRowModel;
	  private DefaultComboBoxModel<String> toRowModel;
	  private DefaultComboBoxModel<String> fromColModel;
	  private DefaultComboBoxModel<String> toColModel;

	  private DefaultTableModel dataTableModel;
	  private Controller ctrl;
	  private List<JSONObject> regionsInfo;

	  private String[] headers = { "Key", "Value", "Description" };

	  // TODO if necessary, add the attributes here.
	  ChangeRegionsDialog(Controller ctrl) {
	    super((Frame)null, true);
	    this.ctrl = ctrl;
	    initGUI();
	    // TODO Register the 'this' object as an observer.
	  }

	  private void initGUI() {
	    setTitle("Change Regions");
	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    setContentPane(mainPanel);

	    // TODO create several panels to organize the visual components in the
	    //      dialog, and add them to mainpanel. E.g., one for the help text,
	    //      one for the table, one for the comboboxes, and one for the buttons.

	    // TODO create the help text that appears at the top of the dialog and
	    //      add it to the corresponding dialog panel (See the Figures section).

	    // this.regionsInfo will be used to set the information in the table.
	    this.regionsInfo = Main.RnFactory.getInfo();

	    // this.dataTableModel is a table model that includes all the parameters of
	    // the region.
	    this.dataTableModel = new DefaultTableModel() {
	      @Override
	      public boolean isCellEditable(int row, int column) {
	        // TODO Make only column 1 editable.
	    	  return true;
	      }
	    };
	    this.dataTableModel.setColumnIdentifiers(this.headers);

	    // TODO Create a JTable that uses dataTableModel, and add it to the dialog.

	    // this.regionsModel is a combobox model that includes the region types.
	    this.regionsModel = new DefaultComboBoxModel<>();

	    // TODO Add the description of all regions to regionsModel. For that
	    //      use the "desc" or "type" key of the JSONObjects in regionsInfo,
	    //      since these give us information about what the factory can create.

	    // TODO Create a combobox that uses regionsModel and add it to the dialog.

	    // TODO Create 4 combobox models for this.fromRowModel, this.toRowModel,
	    //      this.fromColModel and this.toColModel.

	    // TODO Create 4 comboboxes that use these models and add them to the dialog.

	    // TODO Create the OK and Cancel buttons and add them to the dialog.

	    setPreferredSize(new Dimension(700, 400)); // You can use a different size.
	    pack();
	    setResizable(false);
	    setVisible(false);
	  }

	  public void open(Frame parent) {
	    setLocation(//
	       parent.getLocation().x + parent.getWidth() + 2 - getWidth() / 2, //
	       parent.getLocation().y + parent.getHeight() + 2 - getHeight() / 2);
	    pack();
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
