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
public class TeamInfo { 
	private ArrayList<Team> entries = new ArrayList<Team> ();
	
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public TeamInfo() {
		logger = LogManager.getLogger(TeamInfo.class.getName());		
	}
	
	public void setEntries(ArrayList<Team> entries) {
		this.entries = entries;
	}
	
	public ArrayList<Team> getEntries() {
		return entries;
	}
	
	public void addEntry(Team entry) {
		this.entries.add(entry);
	}
	
	public void addEntries(TeamInfo ti) {
		for(Team t : ti.getEntries()) {
			addEntry(t);
		}
	}
	
	public int getTeams(String season, String state, int age, String division) {
		int count = 0;
		for(Team te : entries) {
			if(age == new Integer(te.getAge()).intValue() &&
					season.equalsIgnoreCase(te.getSeason()) && 
					state.equalsIgnoreCase(te.getTeamState()) && 
					division.equalsIgnoreCase(te.getDivision())) {
				count++;
			}
		}
		return count;
	}
	
	public int getEntryByAge(int age) {
		int count = 0;
		for(Team pte : entries) {
			if(age == new Integer(pte.getAge()).intValue()) {
				count++;
			}
		}
		
		return count;
	}
	
	public int getEntryByState(String state) {
		int count = 0;
		for(Team pte : entries) {
			if(state.equalsIgnoreCase(pte.getTeamState())) {
				count++;
			}
		}
		return count;
	}

	@Override
	public String toString() {
		return "TeamInfo [entries=" + entries + "]";
	}
}
