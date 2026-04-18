package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {

	  // TODO Declare necessary fields.
	  Controller ctrl_;
	  String[] columns = {"Row", "Col", "Desc.", "CARNIVORE", "HERBIVORE"};
	  Object[][] regionData;
	  static int numRows = 0, numCols = 0, tableRows = 0;

	  RegionsTableModel(Controller ctrl) {
	    // TODO Initialize corresponding data structures.
		  numRows = ctrl.getNumRows();
		  numCols = ctrl.getNumCols();
		  tableRows = numRows * numCols;
		  
		  this.regionData = new Object[tableRows][columns.length];
		  
		  int pos = 0;
				  
		  for (int i = 0; i < numRows; i++) {
			  for (int j = 0; j < numCols; j++) {
				  pos = i * numCols + j;
				  regionData[pos][0] = i;
				  regionData[pos][1] = j;
				  regionData[pos][2] = "default";
				  regionData[pos][3] = 0;
				  regionData[pos][4] = 0;
			  }
		  }
		  
	    // TODO Register the 'this' object as an observer.
		  ctrl.addObserver(this);
		  
	  }
	  
	  // TODO The rest of methods.
	  
	  @Override
	  public String getColumnName(int columnIndex) {
		  return columns[columnIndex];
	  }

	  @Override
	  public int getRowCount() {
		// TODO Auto-generated method stub
		return tableRows;
	  }

	  @Override
	  public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	  }

	  @Override
	  public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return regionData[rowIndex][columnIndex];
	  }

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		  int aux = 0, tablePos = 0, pos = 0;
		  
		  for (int i = 0; i < numRows; i++) {
			  for (int j = 0; j < numCols; j++) {
				  pos = i * numCols + j;
				  regionData[pos][3] = 0;
				  regionData[pos][4] = 0;
			  }
		  }
		  
		  for (int i = 0; i < animals.size(); i++) {
			  AnimalInfo currentAnimal = animals.get(i);
			  tablePos = ((int)currentAnimal.getPosition().getY()/map.getRegionHeight())*numRows + (int)currentAnimal.getPosition().getX()/map.getRegionWidth();
			  if (tablePos < 0) tablePos = 0;
			  else if (tablePos >= tableRows) tablePos = tableRows - 1;
			  switch(currentAnimal.getDiet()) {			  
			  case HERBIVORE:
				  aux = (int)regionData[tablePos][4] + 1;
				  regionData[tablePos][4] = aux;
				  fireTableCellUpdated(tablePos, 4);
				  break;
				  
			  case CARNIVORE:
				  aux = (int)regionData[tablePos][3] + 1;
				  regionData[tablePos][3] = aux;
				  fireTableCellUpdated(tablePos, 3);
				  break;
			  }
		  }
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  int tablePos = 0, aux = 0, pos = 0;
		  AnimalInfo currentAnimal = a;
		  tablePos = ((int)currentAnimal.getPosition().getY()/map.getRegionHeight())*numRows + (int)currentAnimal.getPosition().getX()/map.getRegionWidth();
		  for (int i = 0; i < numRows; i++) {
			  for (int j = 0; j < numCols; j++) {
				  pos = i * numCols + j;
				  regionData[pos][3] = 0;
				  regionData[pos][4] = 0;
			  }
		  }
		  if (tablePos < 0) tablePos = 0;
		  else if (tablePos >= tableRows) tablePos = tableRows - 1;
		  switch(currentAnimal.getDiet()) {			  
		  case HERBIVORE:
			  aux = (int)regionData[tablePos][4] + 1;
			  regionData[tablePos][4] = aux;
			  fireTableCellUpdated(tablePos, 4);
			  break;
			  
		  case CARNIVORE:
			  aux = (int)regionData[tablePos][3] + 1;
			  regionData[tablePos][3] = aux;
			  fireTableCellUpdated(tablePos, 3);
			  break;
		  }
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		// TODO Auto-generated method stub
		int tablePos = row * numRows + col;
		regionData[tablePos][2] = "default"; //TODO esto está bien por ahora pero hay que cambiarlo
		onRegister(0.0, map, r.getAnimalsInfo());
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub
		onRegister(dt, map, animals);
	  }
	}