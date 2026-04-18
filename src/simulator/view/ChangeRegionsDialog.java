package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.launcher.Main;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class ChangeRegionsDialog extends JDialog implements EcoSysObserver {

	private DefaultComboBoxModel<String> _regionsModel;
	private DefaultComboBoxModel<String> _fromRowModel;
	private DefaultComboBoxModel<String> _toRowModel;
	private DefaultComboBoxModel<String> _fromColModel;
	private DefaultComboBoxModel<String> _toColModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _regionsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

	private int _status = 0;
	private static final String MESSAGE = "Select a region type, the rows/cols interval, "
			+ "and provide values for the parameters in the Value column"
			+ " (default values are used for parameters with no value).";

	ChangeRegionsDialog(Controller ctrl) {
		super((Frame) null, true);
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Change Regions");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(700, 40));
		textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(textPanel);

		JPanel tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension(700, 180));
		mainPanel.add(tablePanel);

		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.setPreferredSize(new Dimension(700, 50));
		comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));
		mainPanel.add(comboBoxPanel, Component.LEFT_ALIGNMENT);

		JPanel buttonsPanel = new JPanel();
		mainPanel.add(buttonsPanel);

		// Creo el mensaje del top
		JTextArea msg = new JTextArea(MESSAGE);
		msg.setAlignmentX(CENTER_ALIGNMENT);
		msg.setEditable(false);
		msg.setLineWrap(true);
		msg.setWrapStyleWord(true);
		msg.setPreferredSize(new Dimension(680, 40));
		textPanel.add(msg);

		_regionsInfo = Main.RnFactory.getInfo();
		
		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1; // Columna value.
			}
		};
		_dataTableModel.setColumnIdentifiers(_headers);
		JTable table = new JTable(_dataTableModel);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(685, 180));
		tablePanel.add(scrollPane);

		JLabel regionsLabel = new JLabel("Region type: ");
		regionsLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		comboBoxPanel.add(regionsLabel);
		comboBoxPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		_regionsModel = new DefaultComboBoxModel<>();
		for (JSONObject reg : _regionsInfo) {
			_regionsModel.addElement(reg.getString("type"));
		}
		JComboBox<String> regionsComboBox = createComboBox(_regionsModel, 90, 25);
		regionsComboBox.setSelectedItem(0);
		updateRegionInfoTable(0);
		regionsComboBox.addActionListener((e) -> {
			int index = regionsComboBox.getSelectedIndex();
			updateRegionInfoTable(index);

		});
		comboBoxPanel.add(regionsComboBox);

		JLabel rowFromTo = new JLabel("Rows from/to: ");
		rowFromTo.setAlignmentY(Component.TOP_ALIGNMENT);
		JLabel colFromTo = new JLabel("Cols from/to: ");
		colFromTo.setAlignmentY(Component.TOP_ALIGNMENT);

		_fromRowModel = new DefaultComboBoxModel<>();
		_toRowModel = new DefaultComboBoxModel<>();
		_fromColModel = new DefaultComboBoxModel<>();
		_toColModel = new DefaultComboBoxModel<>();

		JComboBox<String> _fromRowComboBox = createComboBox(_fromRowModel, 70, 25);
		JComboBox<String> _toRowComboBox = createComboBox(_toRowModel, 70, 25);
		JComboBox<String> _fromColComboBox = createComboBox(_fromColModel, 70, 25);
		JComboBox<String> _toColComboBox = createComboBox(_toColModel, 70, 25);

		comboBoxPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		comboBoxPanel.add(rowFromTo);

		comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxPanel.add(_fromRowComboBox);
		comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxPanel.add(_toRowComboBox);

		comboBoxPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		comboBoxPanel.add(colFromTo);

		comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxPanel.add(_fromColComboBox);
		comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		comboBoxPanel.add(_toColComboBox);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			JSONObject regions = null;
			try {
				regions = create_rjson();
				_ctrl.setRegions(regions);
				this._status = 1;
				setVisible(false);

			} catch (Exception ex) {
				ex.printStackTrace();
				ViewUtils.showErrorMsg(ex.getMessage());
			}

		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			this._status = 0;
			setVisible(false);
		});

		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400)); // puedes usar otro tamaño
		pack();
		setResizable(true);
		setVisible(false);
	}

	private void updateRegionInfoTable(int index) {
		JSONObject info = _regionsInfo.get(index);
		JSONObject data = info.getJSONObject("data");
		_dataTableModel.setRowCount(0); // Vacio la tabla
		for (String str : data.keySet()) {
			Object rowData[] = { str, "", data.getString(str) };
			_dataTableModel.addRow(rowData);
		}
	}

	public void open(Frame parent) {
		setLocation(//
				parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
				parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
	}

	private String getJSON() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String k = _dataTableModel.getValueAt(i, 0).toString();
			String v = _dataTableModel.getValueAt(i, 1).toString();
			if (!v.isEmpty()) {
				s.append('"');
				s.append(k);
				s.append('"');
				s.append(':');
				s.append(v);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		return s.toString();
	}

	private JSONObject create_rjson() throws NumberFormatException {
		JSONObject region_data = new JSONObject(this.getJSON());

		String region_type = _regionsModel.getSelectedItem().toString();
		JSONObject spec = new JSONObject();
		spec.put("data", region_data);
		spec.put("type", region_type);

		int row_from = Integer.valueOf(_fromRowModel.getSelectedItem().toString());
		int row_to = Integer.valueOf(_toRowModel.getSelectedItem().toString());
		int col_from = Integer.valueOf(_fromColModel.getSelectedItem().toString());
		int col_to = Integer.valueOf(_toColModel.getSelectedItem().toString());
		JSONArray rowRange = new JSONArray();
		rowRange.put(row_from);
		rowRange.put(row_to);

		JSONArray colRange = new JSONArray();
		colRange.put(col_from);
		colRange.put(col_to);

		JSONObject singleRegion = new JSONObject();
		singleRegion.put("row", rowRange);
		singleRegion.put("col", colRange);
		singleRegion.put("spec", spec);

		JSONArray regions = new JSONArray();
		regions.put(singleRegion);

		JSONObject rjson = new JSONObject();
		rjson.put("regions", regions);

		return rjson;
	}

	private JComboBox<String> createComboBox(DefaultComboBoxModel<String> model, int x, int y) {
		JComboBox<String> comboBox = new JComboBox<String>(model);
		comboBox.setAlignmentY(Component.TOP_ALIGNMENT);
		comboBox.setPreferredSize(new Dimension(x, y));
		comboBox.setMaximumSize(new Dimension(x, y));
		comboBox.setMinimumSize(new Dimension(x, y));
		return comboBox;
	}

	private void updateValues(DefaultComboBoxModel<String> model, int range) {
		for (int i = 0; i < range; ++i) {
			model.addElement(String.valueOf(i));
		}
	}

	private void updateComboBoxs(MapInfo map) {
		_fromRowModel.removeAllElements();
		_toRowModel.removeAllElements();
		_fromColModel.removeAllElements();
		_toColModel.removeAllElements();

		updateValues(_fromRowModel, map.getRows());
		updateValues(_toRowModel, map.getRows());
		updateValues(_fromColModel, map.getCols());
		updateValues(_toColModel, map.getCols());

	}

	private int getStatus() {
		return _status;
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		updateComboBoxs(map);
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		updateComboBoxs(map);
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {

	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {

	}

	@Override
	public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {

	}
}
