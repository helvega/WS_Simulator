package simulator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

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
		this.cellWidth = Math.ceilDiv(mapWidth, rows);
		this.cellHeight = Math.ceilDiv(mapHeight, cols);
		region = new Region[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++)
				region[i][j] = new DefaultRegion();
		}
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
		List<Animal> animals = new ArrayList();
		
		double sr = (int) e.getSightRange(); // sight range
		
		int minR = (int) (e.getPosition().getX() - sr) / cellWidth; //minimun and maximun region rows
		int maxR = (int) (e.getPosition().getX() + sr) / cellWidth;
		if(maxR > region.length - 1) //correct any wrong value
			maxR = region.length - 1;
		if(minR < 1)
			minR = 1;
		
		int minC = (int) (e.getPosition().getY() - sr) / cellHeight; //minimun and maximun region cols
		int maxC = (int) (e.getPosition().getY() + sr) / cellHeight;
		if(minC > region.length - 1) //correct any wrong value
			minC = region.length - 1;
		if (minC < 0)
			minC = 0;
		if(maxC < 1)
			maxC = 1;
		
		for(int i = minR; i <= maxR && i < rows; i++) { //for possible regions
			for(int j = minC; j <= maxC && j < cols; j++) {
				for(Animal a: region[i][j].getAnimals()) //get animals of that region
					if(!a.getPosition().equals(e.getPosition())) //it's not the animal itself
						animals.add(a);
			}
		}
		
		return animals;
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
		if(x < 0)
			x = 0;
		if (y < 0)
			y = 0;
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
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++)
				region[i][j].update(dt);
		}
	}
	
	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
	    
		JSONArray ja = new JSONArray(); //JSON Array for regions
		JSONArray an = new JSONArray(); //JSON Array for animals
		
	    for(int ri = 0; ri < region.length; ri++) {
	    	for(int rj = 0; rj < region.length; rj++) { //for every region in the matrix
	    		JSONObject rng = new JSONObject(); //a region is an object to add to the array
	    		JSONObject anm = new JSONObject();
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
