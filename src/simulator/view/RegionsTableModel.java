package simulator.view;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.MapInfo.RegionData;
import simulator.model.RegionInfo;
import simulator.misc.Vector2D;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {

	  // TODO Declare necessary fields.
	  Controller ctrl_;
	  String[] columns = {"Row", "Col", "Desc.", "CARNIVORE", "HERBIVORE"};
	  Object [][] regionData;
	  static int numRows = 0, numCols = 0, tableRows = 0;

	  public RegionsTableModel(Controller ctrl) {
	    // Register the 'this' object as an observer.
		  ctrl.addObserver(this);
		  
	  }
	  
	  @Override
	  public String getColumnName(int columnIndex) {
		  return columns[columnIndex];
	  }

	  @Override
	  public int getRowCount() {
		return tableRows;
	  }

	  @Override
	  public int getColumnCount() {
		return columns.length;
	  }

	  @Override
	  public Object getValueAt(int rowIndex, int columnIndex) {
		return regionData[rowIndex][columnIndex];
	  }

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		  int pos = 0;
		  numRows = map.getRows();
		  numCols = map.getCols();
		  tableRows = numRows * numCols;
		  regionData = new Object[tableRows][5];
		  
		  for (int i = 0; i < numRows; i++) {
			  for (int j = 0; j < numCols; j++) {
				  pos = i * numCols + j;
				  regionData[pos][0] = i;
				  regionData[pos][1] = j;
				  regionData[pos][3] = 0;
				  regionData[pos][4] = 0;
			  }
		  }
		  
		 regionSet(map);
		 animalSet(map, animals);
		 fireTableDataChanged();
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		  numRows = map.getRows();
		  numCols = map.getCols();
		  tableRows = numRows * numCols;
		  regionSet(map);
		  animalSet(map, animals);
		  fireTableDataChanged();
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		  int tablePos = 0, aux = 0;
		  AnimalInfo currentAnimal = a;
		  int row = (int) currentAnimal.getPosition().getY()/map.getRegionHeight();
		  int col = (int)currentAnimal.getPosition().getX()/map.getRegionWidth();
		  
		  row = Math.max(0, Math.min(row, map.getRows() - 1));
	      col = Math.max(0, Math.min(col, map.getCols() - 1));
	        
		  tablePos = (row * numCols + col);
		  
		  
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
		  int tablePos = row * numCols + col;
		  regionData[tablePos][2] =  r.tag();
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		  animalSet(map, animals);
		  fireTableDataChanged();
	  }
	  
	  private void regionSet(MapInfo map) {
		  Iterator<RegionData> iterator = map.iterator();
		  for(int i = 0; i < tableRows; i++) {
			  RegionData r = iterator.next();
			  onRegionSet(r.row(), r.col(), map, r.r());
		  }
	  }
	  
	  private void animalSet(MapInfo map, List<AnimalInfo> animals) {
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
			  int row = (int) currentAnimal.getPosition().getY()/map.getRegionHeight();
			  int col = (int)currentAnimal.getPosition().getX()/map.getRegionWidth();
			  
			  row = Math.max(0, Math.min(row, map.getRows() - 1));
		      col = Math.max(0, Math.min(col, map.getCols() - 1));
		        
			  tablePos = (row * numCols + col);
			  
			  switch(currentAnimal.getDiet()) {			  
			  case HERBIVORE:
				  aux = (int)regionData[tablePos][4] + 1;
				  regionData[tablePos][4] = aux;
				  break;
				  
			  case CARNIVORE:
				  aux = (int)regionData[tablePos][3] + 1;
				  regionData[tablePos][3] = aux;
				  break;
			  }
		  }
	  }
	  
	}