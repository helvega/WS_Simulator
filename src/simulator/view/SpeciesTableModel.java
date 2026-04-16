package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class SpeciesTableModel extends AbstractTableModel implements EcoSysObserver {

	  // TODO Declare necessary fields.
	  Controller ctrl_;
	  String[] columns = {"Species, NORMAL, MATE, HUNGER, DANGER, DEAD"};
	  Integer[][] animal_rows;
	  static final int numRows = 2;
	  
	  SpeciesTableModel(Controller ctrl) {
	    // TODO Initialize corresponding data structures.
		  this.ctrl_ = ctrl;
		  
		  int k = 0; 
		  animal_rows = new Integer[numRows][columns.length]; 
		  for(int i=0; i<rows.length; i++) 
			  for(int j=0; j<columns.length; j++) 
				  rows[i][j] = new Integer(k++);
		  
	    // TODO Register the 'this' object as an observer.
	  }
	  // TODO The rest of methods.

	  @Override
	  public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	  }

	  @Override
	  public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	  }

	  @Override
	  public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
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
	}