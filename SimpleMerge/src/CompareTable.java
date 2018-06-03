import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class CompareTable extends JTable {
	DefaultTableModel model;

	CompareTable() {

	}

	CompareTable(ArrayList<String> fileContentList, Color panelColor) {
		super();
		// set header
		Vector<String> head = new Vector<String>();
		head.addElement("line");
		head.addElement("Content");
		model = new DefaultTableModel(head, 0);

		// set contents
		for (int i = 0; i < fileContentList.size(); i++) {
			Vector<String> contents = new Vector<String>();
			contents.addElement(String.valueOf(i + 1));
			contents.addElement(fileContentList.get(i));
			model.addRow(contents);
			System.out.println(fileContentList.get(i));
		}

		this.setModel(model);

		// Set Color or Grid and Header
		this.setShowHorizontalLines(false);
		this.setGridColor(Color.LIGHT_GRAY);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Color.WHITE);

		// TODO column width
		// Set column size
		TableColumnModel col = this.getColumnModel();
		col.getColumn(0).setPreferredWidth(30);
		col.getColumn(1).setPreferredWidth(550);

		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Color textTable
		ArrayList<Integer> arri = new ArrayList<Integer>();
		TableColumnColor renderer = new TableColumnColor(arri, panelColor);
		try {
			this.setDefaultRenderer(Class.forName("java.lang.Object"), renderer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	CompareTable(DefaultTableModel model) {
		super(model);

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
