/**
 * 
 */
package mfp.common.excel;

import java.util.ArrayList;

/**
 * @author Glen
 *
 */
public class Spreadsheet {
    private String name;
	private ArrayList<Sheet> sheets = new ArrayList<Sheet>();
	
	/**
	 * 
	 */
	public Spreadsheet(String name) {
		this.name = name;
	}
	
	public void addSheet(Sheet s) {
		sheets.add(s);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
