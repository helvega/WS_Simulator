package simulator.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegionManager implements AnimalMapView{
	
	private Map<Animal, Region> animalRegion;
	private Region[][] region;
	private int mapWidth;
	private int mapHeight;
	private int cols;
	private int rows;
	private int cellWidth;
	private int cellHeight;
	
	public RegionManager(int cols, int rows, int width, int height) {
		this.cols = cols;
		this.rows = rows;
		this.mapWidth = width;
		this.mapHeight = height;
		region = new Region[cols][rows];
		IntStream.range(0, rows).forEach(i -> Arrays.setAll(region[i], j -> new DefaultRegion())); //fill the matrix with DefaultRegions
		animalRegion = new HashMap<>(); //initialize map object	
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public int getWidth() {
		return mapWidth;
	}

	public int getHeight() {
		return mapHeight;
	}

	public int getRegionWidth() {
		return cellWidth;
	}

	public int getRegionHeight() {
		return cellHeight;
	}

	public double getFood(AnimalInfo a, double dt) {
		int x = (int) Math.floor(a.getPosition().getX() / cellWidth); //in order to know which cell corresponds just divide 
		//by the size of the cell to know the cell number
		int y = (int) Math.floor(a.getPosition().getY() / cellHeight);
		return region[x][y].getFood(a, dt);
	}

	public List<Animal> getAnimalsInRange(Animal e, Predicate<Animal> filter) {
		e.getSightRange();
		return null;
	}
	
	public void setRegion(int row, int col, Region r) {
		List<Animal> aux = region[row][col].getAnimals(); // get in a  variable the list of animals of the region that is about to be replaced
		for(Animal a : aux) { // for all animals
			animalRegion.replace(a, region[row][col], r); // replace the old region with the new region
		}
		region[row][col] = r;
	}
	
	public void registerAnimal(Animal a) {
		int x = (int) Math.floor(a.getPosition().getX() / cellWidth); //in order to know which cell corresponds just divide 
		//by the size of the cell to know the cell number
		int y = (int) Math.floor(a.getPosition().getY() / cellHeight);
		region[x][y].addAnimal(a);
		animalRegion.put(a, region[x][y]); //updates the map entries
		a.init(this);
	}
	
	public void unregisterAnimal(Animal a) {
		int x = (int) Math.floor(a.getPosition().getX() / cellWidth);
		int y = (int) Math.floor(a.getPosition().getY() / cellHeight);
		region[x][y].removeAnimal(a);
		animalRegion.remove(a, region[x][y]); //updates the map entries
	}
	
	public void updateAnimalRegion(Animal a) { //Check if an animal passed to other region
		int x = (int) Math.floor(a.getPosition().getX() / cellWidth);
		int y = (int) Math.floor(a.getPosition().getY() / cellHeight);
		Region currentRegion = animalRegion.get(a);
		Region newRegion = region[x][y];
		if(newRegion != currentRegion) {
			newRegion.addAnimal(a);
			animalRegion.put(a, newRegion); //updates the map entries
			currentRegion.removeAnimal(a);
			animalRegion.remove(a, currentRegion); //updates the map entries
		}
	}
	
	public void updateRegion(Animal a, double dt) {
		int x = (int) Math.floor(a.getPosition().getX() / cellWidth);
		int y = (int) Math.floor(a.getPosition().getY() / cellHeight);
		Region aux = animalRegion.get(a);
		aux.update(dt);
	}
	
	public void updateAllRegions(double dt) { // for all rows of the matrix, for all columns of the rows, update()
		for(Region[] row: region){
			for(Region col : row)
				col.update(dt);
		}
	}
	
	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
	    
		JSONArray ja = new JSONArray(); //JSON Array for regions
		
	    for(int ri = 0; ri < region.length; ri++) {
	    	for(int rj = 0; rj < region.length; rj++) { //for every region in the matrix
	    		JSONObject rng = new JSONObject(); //a region is an object to add to the array
	    		rng.put("row", ri); //put row
	    		rng.put("col", rj); //put col
	    		rng.put("data", region[ri][rj].asJSON()); //put region object
	    		ja.put(rng); //put total region info in the array
	    	}
	    }
	    
	    obj.put("regions", ja); //put the array in the object
	    
		return obj;
	}

}
