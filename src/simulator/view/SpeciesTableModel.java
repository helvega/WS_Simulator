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
	  static final int numRows = 0, numCols = State.values().length + 1;
	  
	  public SpeciesTableModel(Controller ctrl) {
	    // Initialize corresponding data structures.
		  this.ctrl_ = ctrl;
		  
		  columns = new String[numCols];
		  
		  columns[0] = "Species";
		  
		  for (int i = 1; i < numCols; i++) {
			  columns[i] = State.values()[i - 1].toString();
		  }

		  animal_data = new Object[numRows][columns.length]; 
		  
	    // Register the 'this' object as an observer.
		  ctrl.addObserver(this);
	  }
	  
	  void addRow(String newSpecies){
		  Object[][] newTable = new Object[animal_data.length + 1][numCols];
		  
		  int newRowMax = animal_data.length;
		  
		  for (int i = 0; i < newRowMax; i++) {
			  newTable[i] = animal_data[i];
		  }
		  
		  newTable[newRowMax][0] = newSpecies;
		  
		  for (int i = 1; i < numCols; i++) {
			  newTable[animal_data.length][i] = 0;
		  }
		  
		  
		  animal_data = newTable;
		  this.fireTableRowsInserted(newRowMax, newRowMax);
		  
	  }
	  
	  @Override
	  public String getColumnName(int columnIndex) {
		  return columns[columnIndex];
	  }

	  @Override
	  public int getRowCount() {
		return animal_data.length;
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
		  animalSet(map, animals);
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		  animalSet(map, animals);
		  fireTableDataChanged();
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  int row = 0, col = 1; 
		  
		 while ( row < animal_data.length && animal_data[row][0] != a.getGeneticCode()) row++;
		 
		 if (row == animal_data.length) addRow(a.getGeneticCode());
		  
		 while ( col < columns.length && columns[col] != a.getState().toString()) col++;
		  
		  int aux = (int)animal_data[row][col] + 1 ;
		  animal_data[row][col] = aux;
		  fireTableCellUpdated(row, col);
	  }
	  

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		  animalSet(map, animals);
	  }
	  
	  private void animalSet(MapInfo map, List<AnimalInfo> animals) {
		  int row = 0, col = 1;
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  this.animal_data[i][j] = 0;
		  
		  for (int k = 0; k < animals.size(); k++) {
			  
			  while (row < animal_data.length && animal_data[row][0] != animals.get(k).getGeneticCode()) row++;
				 
			  if (row == animal_data.length) addRow(animals.get(k).getGeneticCode());
			  
			  while ( col < columns.length && columns[col] != animals.get(k).getState().toString()) col++;
			  
			  int aux = (int)animal_data[row][col] + 1 ;
			  animal_data[row][col] = aux;
			  fireTableCellUpdated(row, col);
			  
			  row = 0;
			  col = 1;
		  }
	  }
	}