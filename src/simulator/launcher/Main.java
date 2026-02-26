package simulator.launcher;

import simulator.misc.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.misc.Utils;
import simulator.model.Animal;
import simulator.model.Region;
import simulator.model.SelectionStrategy;
import simulator.model.Simulator;

public class Main {

	private enum ExecMode {
		BATCH("batch", "Batch mode"), GUI("gui", "Graphical User Interface mode");

		private String tag;
		private String desc;

		private ExecMode(String modeTag, String modeDesc) {
			tag = modeTag;
			desc = modeDesc;
		}

		public String getTag() {
			return tag;
		}

		public String getDesc() {
			return desc;
		}
	}

	// default values for some parameters
	//
	private final static Double DEFAULT_TIME = 10.0; // in seconds
	private final static Double DEFAULT_DTIME = 0.03; // in seconds

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double time = null;
	private static Double dt = null;
	private static String inFile = null;
	private static String outFile = null;
	private static boolean sv = true;
 	private static ExecMode mode = ExecMode.BATCH;
	
	private static Factory <SelectionStrategy> SsFactory;
	private static Factory <Animal> AnFactory;
	private static Factory <Region> RnFactory;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTimeOption(line);
			parseDtOption(line);
			parseSvOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();
		
		//time per step
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg().desc("A double representing actual time, in  \n"
				+ "                             seconds, per simulation step. Default value: 0.03. ").build());
		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("A configuration file.").build());
		
		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("ouput").hasArg().desc("Output File where output is written.").build());
		
		//simple viewer
		cmdLineOptions.addOption(Option.builder("sv").longOpt("simple-viewer").desc("Show the viewer window in console mode.").build());

		// steps
		cmdLineOptions.addOption(Option.builder("t").longOpt("time").hasArg()
				.desc("A real number representing the total simulation time in seconds. Default value: "
						+ DEFAULT_TIME + ".").build());
		
		

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	
	private static void parseOutFileOption(CommandLine line) throws ParseException {
		outFile = line.getOptionValue("o");
		if (mode == ExecMode.BATCH && outFile == null) {
			throw new ParseException("In batch mode an output file is required to run the Controller");
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		inFile = line.getOptionValue("i");
		if (mode == ExecMode.BATCH && inFile == null) {
			throw new ParseException("In batch mode an input configuration file is required to initialize the simulation");
		}
	}

	private static void parseTimeOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", DEFAULT_TIME.toString());
		try {
			time = Double.parseDouble(t);
			assert (time >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time: " + t);
		}
	}
	
	private static void parseDtOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("dt", DEFAULT_DTIME.toString());
		try {
			dt = Double.parseDouble(t);
			assert (dt >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for dt: " + t);
		}
	}
	
	private static void parseSvOption(CommandLine line) throws ParseException {
		String s = line.getOptionValue("sv", "true");
		try {
			sv = Boolean.parseBoolean(s);
		} catch (Exception e) {
			throw new ParseException("Invalid value for sv: " + s);
		}
	}

	private static void initFactories() {
		List <Builder<SelectionStrategy>> bss = new ArrayList<>();
		bss.add(new SelectFirstBuilder(Constants.TYPE_SELECT_FIRST, Constants.DESC_SELECT_FIRST));
		bss.add(new SelectClosestBuilder(Constants.TYPE_SELECT_CLOSEST, Constants.DESC_SELECT_CLOSEST));
		bss.add(new SelectYoungestBuilder(Constants.TYPE_SELECT_YOUNGEST, Constants.DESC_SELECT_YOUNGEST));
		SsFactory = new BuilderBasedFactory<SelectionStrategy>(bss);
		List <Builder<Animal>> ba = new ArrayList<>();
		ba.add(new SheepBuilder(Constants.TYPE_SHEEP_BUILDER, Constants.DESC_SHEEP_BUILDER));
		ba.add(new WolfBuilder(Constants.TYPE_WOLF_BUILDER, Constants.DESC_WOLF_BUILDER));
		AnFactory = new BuilderBasedFactory<Animal>(ba);
		List <Builder<Region>> br = new ArrayList<>();
		br.add(new DefaultRegionBuilder(Constants.TYPE_DEFAULT_REGION_BUILDER, Constants.DESC_DEFAULT_REGION_BUILDER));
		br.add(new DynamicSupplyRegionBuilder(Constants.TYPE_DYNAMIC_REGION_BUILDER, Constants.DESC_DYNAMIC_REGION_BUILDER));
		RnFactory = new BuilderBasedFactory<Region>(br);
	}

	private static JSONObject loadJSONFile(InputStream in) {
		return new JSONObject(new JSONTokener(in));
	}


	private static void startBatchMode() throws Exception {
		InputStream is = new FileInputStream(new File(inFile));
		
		JSONObject jo = loadJSONFile(is);
		int cols = jo.getInt("cols");
		int rows = jo.getInt("rows");
		int width = jo.getInt("width");
		int height = jo.getInt("height");
		
		Simulator sim = new Simulator(cols, rows, width, height, AnFactory, RnFactory);
		Controller controler = new Controller(sim);
		JSONObject data = jo.has("data") ? jo.getJSONObject("data") : new JSONObject();
		controler.loadData(data);
		OutputStream out = new FileOutputStream(outFile);
		PrintStream p = new PrintStream(out);
		controler.run(time, dt, sv, p);
		out.close();
	}

	private static void startGUIMode() throws Exception {
		// implementation for the next assignment
		throw new UnsupportedOperationException("GUI mode is not ready yet ...");
	}

	private static void start(String[] args) throws Exception {
		initFactories();
		parseArgs(args);
		switch (mode) {
		case BATCH:
			startBatchMode();
			break;
		case GUI:
			startGUIMode();
			break;
		}
	}

	public static void main(String[] args) {
		Utils.RAND.setSeed(2147483647l);
		try {
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
