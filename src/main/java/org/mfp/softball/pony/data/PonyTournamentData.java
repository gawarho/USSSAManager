/**
 * 
 */
package org.mfp.softball.pony.data;

import org.apache.logging.log4j.LogManager;
import org.mfp.softball.data.BaseTournamentData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gawarho
 *
 */
public class PonyTournamentData extends BaseTournamentData { 
	private ArrayList<String> status = new ArrayList<String> ();
	private String location = null;
	private String dates = null;
	private String regStatus = null;
	private String zone = null;
	
	public PonyTournamentData(String name, String id, String type, String games, String cost, String year,
			String ages, String dates, String location, String contact, String regStatus, String zone) {
		super(name, id, type, games, cost, year);
		logger = LogManager.getLogger(PonyTournamentData.class.getName());
		
		parseAges(ages);
		this.location = location;
		parseCityState(location);
		parseContactInfo(contact);
		this.games = games;
		this.regStatus = regStatus;
		this.dates = dates;
		parseDates(dates);
		this.zone = zone;
	}

	private void parseAges(String str) {
		String [] parts = str.split(",");
	    for(String p : parts) {
	    	p = p.trim();
	    	if(p.indexOf("Closed") > -1) {
	    		this.status.add("Closed");
	    	} else {
	    		this.status.add("Open");
	    	}
	    	p = p.replace("*Closed", "");
	    	ages.add(p);
	    }
	}
	private void parseContactInfo(String str) {
		String [] parts = str.split("\\s+");
		int size = parts.length;
		phone = parts[size-1];
		phone = phone.trim();
		email = parts[size-2];
		email = email.trim();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<size-2; i++) {
			sb.append(parts[i] + " ");
		}
		contact = sb.toString();
		contact = contact.trim();
		
		return;
	}
	
	
	private void parseDates(String str) {
		String [] parts = str.split("-");
		
		startDate = parts[0];
		startDate = startDate.trim();
		
		endDate = parts[1];
		endDate = endDate.trim();
		
		return;
	}
	private void parseCityState(String str) {
	    city = "Unknown";
	    state = "Unknown";
	    
	    int index = str.lastIndexOf(",");
	    if(index > -1) {
	    	city = str.substring(0, index);
	    	city = city.trim();
	    	state = str.substring(index + 1);
	    	state = state.trim();
	    }
	    
	    return;
	}
	
	private int getAge(String str) {
		int age = -1;
		
		Pattern p = Pattern.compile("(\\d+) & Under");
		Matcher m = p.matcher(str);
		if(m.find()) {
			String ageStr = m.group(1);
			age = Integer.parseInt(ageStr);
		}
		
		return age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public ArrayList<String> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<String> status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PonyTournamentData [status=" + status + ", location="
				+ location + ", dates=" + dates + ", regStatus=" + regStatus
				+ ", zone=" + zone + "]";
	}

	
}
