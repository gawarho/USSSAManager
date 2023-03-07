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
public class BaseTournamentListing { 
	private ArrayList<BaseTournamentData> entries = new ArrayList<BaseTournamentData> ();
	
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public BaseTournamentListing() {
		logger = LogManager.getLogger(BaseTournamentListing.class.getName());		
	}
	
	public void setEntries(ArrayList<BaseTournamentData> entries) {
		this.entries = entries;
	}
	
	public ArrayList<BaseTournamentData> getEntries() {
		return entries;
	}
	
	public void addEntry(BaseTournamentData entry) {
		this.entries.add(entry);
	}
	
}
