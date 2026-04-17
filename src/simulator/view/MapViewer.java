package simulator.view;

import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.MapInfo;
import simulator.model.State;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * An incomplete version of  the map viewer, to be completed by students.
 */

@SuppressWarnings("serial")
public class MapViewer extends AbstractMapViewer {
	
	// Width/height of the simulation -- they will always be equal to the size
	// of the component
	private int width;
	private int height;

	// Number of rows/cols of the simulation (regions) 
	private int rows;
	private int cols;

	// Width/height of a region
	int rWidth;
	int rHeight;

	// We show the animals that have this state. The possible values of currSrare
	// are: null and the values returned by Animal.State.values(). If it is null we
	// show all animals.
	State currState;
	List<State> wheel;
	int pc = 0;
	// The value of these attributes are the list of animals and the time that we
	// have received in the notification (those will be shown).
	volatile private Collection<AnimalInfo> objs;
	volatile private Double time;

	// An auxiliary class to store information about species.
	private static class SpeciesInfo {
		private Integer count;
		private Color color;

		public SpeciesInfo(Color color) {
			count = 0;
			this.color = color;
		}
	}

	// A map with the information for each species.

	Map<String, SpeciesInfo> kindsInfo = new HashMap<>();

	// The font to be used for drawing text.
	private Font textFont = new Font("Arial", Font.BOLD, 12);

	// Indicates if the 'help' information is visible or hidden.
	private boolean showHelp;

	public MapViewer() {
		initGUI();
	}

	private void initGUI() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'h':
					showHelp = !showHelp;
					repaint();
					break;
				case 's':
					//      Change currState to the next option (in a circular way). After null
					//      comes the first element of Animal.State.values(), and after the last of 
					//      these values comes null.
					currState = wheel.get(pc);
					pc = (pc >= 5 ? 0 : pc + 1);
					repaint();
				default:
				}
			}

		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				// This is needed to capture keystroke when the mouse is over this component. 
				requestFocus(); 
			}
		});
		
		wheel = new ArrayList<>(6);
		for(State s : State.values()) {
			wheel.add(s);
		}
		
		wheel.add(null); //null is the sixth element of the wheel
		
		// By default, we show all animals.
		currState = null;

		// By default, the 'help' message is visible.
		showHelp = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Change the font to be used when drawing text.
		g.setFont(textFont);

		// Draw a white background.
		gr.setBackground(Color.WHITE);
		gr.clearRect(0, 0, width, height);

		// Draw the animals, the time, species information. etc.
		if (objs != null)
			drawObjects(gr, objs, time);
		
		//      Show a 'help' text if  showHelp is true. The text should be the following
		//      in two separated lines:
		//
		// h: toggle help
		// s: show animals of a specific state
		if(showHelp) {
			gr.setPaint(Color.RED);
		    gr.drawString("h: toggle help", 10, 15);
		    gr.drawString("s: show animals of a specific state", 10, 30);
		}

	}

	private boolean visible(AnimalInfo a) {
		//      return true of the animal is visible, i.e., currState is null or its
		//      state is equal to currState.
		return currState == null || a.getState() == currState;
	}

	private void drawObjects(Graphics2D g, Collection<AnimalInfo> animals, Double time) {

		//      Draw a grid of regions.
		Graphics2D grid = g;
		grid.setPaint(Color.LIGHT_GRAY);
		for (int x = 0; x <= width; x += rWidth) {
            grid.drawLine(x, 0, x, height);
        }
		for (int y = 0; y <= height; y += rHeight) {
            grid.drawLine(0, y, width, y);
        }
		
		
		// Draw the animals
		for (AnimalInfo a : animals) {
			// If the animal is not visible, we skip to the next iteration. 
			if (!visible(a))
				continue;

			// Information of the species of 'a'
			String gcode = a.getGeneticCode();
			SpeciesInfo speciesInfo = kindsInfo.get(gcode);

			//      If espInfo is null, add a corresponding entry to the map. For the color
			//      use ViewUtils.getColor(a.getGeneticCode()).
			if(speciesInfo == null)
				kindsInfo.put(gcode, new SpeciesInfo(ViewUtils.getColor(gcode))); //ViewUtils knows if it's wolf or sheep

			//      Increment the counter of the species (i.e., the one inside speciesInfo).
			kindsInfo.get(gcode).count++;

			//      Draw the animal at the corresponding position, using the color 
			//      speciesInfo.color. Its size should be relative to the animal's age, e.g.,
			//      age/2+2. For drawing you can use fillRoundRect, fillRect or fillOval.
			g.setPaint(kindsInfo.get(gcode).color); //set color
			Vector2D pos = a.getPosition();
			double age = a.getAge();
			int size = (int) Math.round((age / 2 + 2)); 
			g.fillRect( (int) Math.round(pos.getX()), (int) Math.round(pos.getY()), size, size); //set rectangle
		}
		
		int space = 10;
		//      Draw the tag of the visible state, using currState.toString(), if it is not null.
		if(currState != null) {
			g.setPaint(Color.PINK);
			drawStringWithRect(g, 60, height - space, "State: " + currState.toString());
			space += 20;
		}
		
		//      Draw the time. To use only 3 decimals you can use String.format("%.3f", time).
		g.setPaint(Color.BLUE);
		drawStringWithRect(g, 60, height - space, "Time: " + String.format("%.3f", time));
		space += 20;
		//      Draw the information of each species. At the end of the iteration, reset the 
		//      species count.
		for (Entry<String, SpeciesInfo> e : kindsInfo.entrySet()) {
			SpeciesInfo info = e.getValue();
			g.setPaint(kindsInfo.get(e.getKey()).color);
			drawStringWithRect(g, 60, height - space, e.getKey() + ": " + info.count);
			space += 20;
			info.count = 0;
		}
	}

	// A method for drawing a text with a rectangular border.
	void drawStringWithRect(Graphics2D g, int x, int y, String s) {
		Rectangle2D rect = g.getFontMetrics().getStringBounds(s, g);
		g.drawString(s, x, y);
		g.drawRect(x - 1, y - (int) rect.getHeight(), (int) rect.getWidth() + 1, (int) rect.getHeight() + 5);
	}

	@Override
	public void update(List<AnimalInfo> objs, Double time) {
		//      Store objs and time in the corresponding fields, and call repaint() to
		//      redraw the component.
		this.objs = objs;
		this.time = time;
		repaint();
	}

	@Override
	public void reset(double time, MapInfo map, List<AnimalInfo> animals) {
		//      Update the fields width, height, cols, rows, etc.
		this.width = map.getWidth();
		this.height = map.getHeight();
		this.cols = map.getCols();
		this.rows = map.getRows();
		this.rWidth = map.getRegionWidth();
		this.rHeight = map.getRegionHeight();
		// This line changes the size of the component, and thus the size of the window
		// because MapWindow calls pack() after calling reset().
		setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));

		// Draw the state.
		update(animals, time);
	}

}