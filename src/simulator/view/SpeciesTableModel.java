package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;
import simulator.model.State;

class SpeciesTableModel extends AbstractTableModel implements EcoSysObserver {

	  // Declare necessary fields.
	  Controller ctrl_;
	  String[] columns;
	  Object[][] animal_data;
	  static final int numRows = 2, numCols = State.values().length + 1;
	  
	  SpeciesTableModel(Controller ctrl) {
	    // Initialize corresponding data structures.
		  this.ctrl_ = ctrl;
		  
		  columns = new String[numCols];
		  
		  columns[0] = "Species";
		  
		  for (int i = 1; i < numCols; i++) {
			  columns[i] = State.values()[i - 1].toString();
		  }

		  animal_data = new Object[numRows][columns.length]; 
		  
		  animal_data[0][0] = "Wolfs";
		  animal_data[1][0] = "Sheeps";
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  animal_data[i][j] = 0;
		  
	    // Register the 'this' object as an observer.
		  ctrl.addObserver(this);
	  }
	  
	  @Override
	  public String getColumnName(int columnIndex) {
		  return columns[columnIndex];
	  }

	  @Override
	  public int getRowCount() {
		return numRows;
	  }

	  @Override
	  public int getColumnCount() {
		return columns.length;
	  }

	  @Override
	  public Object getValueAt(int rowIndex, int columnIndex) {
		return animal_data[rowIndex][columnIndex];
	  }
	  
	  @Override
	  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		  this.animal_data[rowIndex][columnIndex] = aValue;
	  };

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		  int row = 0, col = 0;
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  this.animal_data[i][j] = 0;
		  
		  for (int k = 0; k < animals.size(); k++) {
			  
			  switch(animals.get(k).getGeneticCode()) {
			  case "wolf":
				  row = 0;
				  break;
				  
			  case "sheep":
				  row = 1;
				  break;
				  
			  }
			  
			  switch(animals.get(k).getState()) {
			  case NORMAL:
				  col = 1;
				  break;
				  
			  case MATE:
				  col = 2;
				  break;
				  
			  case HUNGER:
				  col = 3;
				  break;
				  
			  case DANGER:
				  col = 4;
				  break;
				  
			  case DEAD:
				  col = 5;
				  break;
			  }
			  
			  int aux = (int)animal_data[row][col] + 1 ;
			  this.animal_data[row][col] = aux;
			  fireTableCellUpdated(row, col);
		  }
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  int row = 0, col = 0; 
		  switch(a.getGeneticCode()) {
		  case "wolf":
			  row = 0;
			  break;
			  
		  case "sheep":
			  row = 1;
			  break;
			  
		  }
		  
		  switch(a.getState()) {
		  case NORMAL:
			  col = 1;
			  break;
			  
		  case MATE:
			  col = 2;
			  break;
			  
		  case HUNGER:
			  col = 3;
			  break;
			  
		  case DANGER:
			  col = 4;
			  break;
			  
		  case DEAD:
			  col = 5;
			  break;
		  }
		  
		  int aux = (int)animal_data[row][col] + 1 ;
		  animal_data[row][col] = aux;
		  fireTableCellUpdated(row, col);
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		  int row = 0, col = 0;
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  this.animal_data[i][j] = 0;
		  
		  for (int k = 0; k < animals.size(); k++) {
			  
			  switch(animals.get(k).getGeneticCode()) {
			  case "wolf":
				  row = 0;
				  break;
				  
			  case "sheep":
				  row = 1;
				  break;
				  
			  }
			  
			  switch(animals.get(k).getState()) {
			  case NORMAL:
				  col = 1;
				  break;
				  
			  case MATE:
				  col = 2;
				  break;
				  
			  case HUNGER:
				  col = 3;
				  break;
				  
			  case DANGER:
				  col = 4;
				  break;
				  
			  case DEAD:
				  col = 5;
				  break;
			  }
			  
			  int aux = (int)animal_data[row][col] + 1 ;
			  animal_data[row][col] = aux;
			  fireTableCellUpdated(row, col);
		  }
	  }
	}