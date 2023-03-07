/**
 * 
 */
package org.mfp.softball.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author gawarho
 *
 */
public class BaseTournamentEntry { 
	protected String name = null;
	protected String id = null;
	protected int eAge = 0;
	protected int tAge = 0;
	protected String city = null;
	protected String state = null;
	protected String year = null;
	
	protected Logger logger = null;
	
	public BaseTournamentEntry(String name) {
		this(name, 0, 0, null, null, null, null);
	}
	
	public BaseTournamentEntry(String name, String year) {
		this(name, 0, 0, null, null, null, year);
	}
	
	public BaseTournamentEntry(String name, int eAge, int tAge, String city, String state, String id, String year) {
		logger = LogManager.getLogger(BaseTournamentEntry.class.getName());
		this.name = name;
		this.eAge = eAge;
		this.tAge = tAge;
		this.city = city;
		this.state = state;
		this.id = id;
		this.year = year;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int geteAge() {
		return eAge;
	}

	public void seteAge(int eAge) {
		this.eAge = eAge;
	}

	public int gettAge() {
		return tAge;
	}

	public void settAge(int tAge) {
		this.tAge = tAge;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "," + id + "," + state);
		return sb.toString();
	}


	@Override
	public String toString() {
		return "BaseTournamentEntry [name=" + name + ", eAge=" + eAge
				+ ", tAge=" + tAge + ", city=" + city + ", state=" + state
				+ "]";
	}
	
}
