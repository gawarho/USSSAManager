/**
 * 
 */
package org.mfp.softball.data;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author gawarho
 *
 */
public class BaseTournamentInfo { 
	private ArrayList<BaseTournamentEntry> entries = new ArrayList<BaseTournamentEntry> ();
	
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public BaseTournamentInfo() {
		logger = LogManager.getLogger(BaseTournamentInfo.class.getName());		
	}
	
	public void setEntries(ArrayList<BaseTournamentEntry> entries) {
		this.entries = entries;
	}
	
	public ArrayList<BaseTournamentEntry> getEntries() {
		return entries;
	}
	
	public void addEntry(BaseTournamentEntry entry) {
		this.entries.add(entry);
	}
	
	public int getEntryByAge(int age) {
		int count = 0;
		for(BaseTournamentEntry pte : entries) {
			if(age == pte.geteAge()) {
				count++;
			}
		}
		
		return count;
	}
	
	public int getEntryByState(String state) {
		int count = 0;
		for(BaseTournamentEntry pte : entries) {
			if(state.equalsIgnoreCase(pte.getState())) {
				count++;
			}
		}
		return count;
	}
}
