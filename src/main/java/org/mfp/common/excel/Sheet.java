/**
 * 
 */
package mfp.common.excel;

import java.util.ArrayList;

/**
 * @author Glen
 *
 */
public class Sheet {

	private String name;
	private ArrayList<Row> rows = new ArrayList<Row>();
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	/**
	 * 
	 */
	public Sheet(String name) {
      this.name = name;
	}

	public void addRow(Row r) {
		rows.add(r);
	}
	
	public void addColumn(Column c) {
		columns.add(c);
	}
}
