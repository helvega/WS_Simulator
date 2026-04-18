package simulator.view;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.Diet;
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
	  static int numMapRows = 0, numMapCols = 0, tableRows = 0, tableCols = 3 + Diet.values().length + 1;

	  public RegionsTableModel(Controller ctrl) {
		  int pos = 0;
		  
		  this.ctrl_ = ctrl;
		  
		  for (int i = 3; i < tableCols - 1; i++) {
			  columns[i] = Diet.values()[i - 3].toString();
		  }
		  
		  for (int i = 0; i < numMapRows; i++) {
			  for (int j = 0; j < numMapCols; j++) {
				  pos = i * numMapCols + j;
				  regionData[pos][0] = i;
				  regionData[pos][1] = j;
				  regionData[pos][2] = "default";
				  for (int k = 3; k < tableCols; k++) {
					  regionData[pos][k] = 0;
				  }
			  }
		  }
		  
		  
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
		  numMapRows = map.getRows();
		  numMapCols = map.getCols();
		  tableRows = numMapRows * numMapCols;
		  regionData = new Object[tableRows][5];
		  
		  for (int i = 0; i < numMapRows; i++) {
			  for (int j = 0; j < numMapCols; j++) {
				  pos = i * numMapCols + j;
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
		  numMapRows = map.getRows();
		  numMapCols = map.getCols();
		  tableRows = numMapRows * numMapCols;
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
	        
		  tablePos = (row * numMapCols + col);
		  
		  int i = 3;
		  
		  while(i < tableCols && columns[i] != a.getDiet().toString()) {
				  i++;
		  }
		  aux = (int)regionData[tablePos][i] + 1;
		  regionData[tablePos][i] = aux;
		  fireTableCellUpdated(tablePos, i);

	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		  int tablePos = row * numMapCols + col;
		  regionData[tablePos][2] =  r.tag();
		  fireTableCellUpdated(tablePos, 2);
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
		  for (int i = 0; i < numMapRows; i++) {
			  for (int j = 0; j < numMapCols; j++) {
				  pos = i * numMapCols + j;
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
		        
			  tablePos = (row * numMapCols + col);
			  
			  int j = 3;
			  
			  while(j < tableCols && columns[j] != animals.get(i).getDiet().toString()) {
					  j++;
			  }
			  aux = (int)regionData[tablePos][j] + 1;
			  regionData[tablePos][j] = aux;
			  fireTableCellUpdated(tablePos, j);
		  }
	  }
	  
	}