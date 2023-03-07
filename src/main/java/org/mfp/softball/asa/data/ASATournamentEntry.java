/**
 * 
 */
package org.mfp.softball.asa.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.mfp.softball.data.BaseTournamentEntry;

/**
 * @author gawarho
 *
 */
public class ASATournamentEntry extends BaseTournamentEntry { 
	private String eventAge = null;
	private String teamAge = null;
	private String cityState = null;
	
	public ASATournamentEntry(String name, String eventAge, String teamAge, String cityState) {
		super(name);
		logger = LogManager.getLogger(ASATournamentEntry.class.getName());
		this.eventAge = eventAge;
		eAge = getAge(eventAge);
		this.teamAge = teamAge;
		tAge = getAge(teamAge);
		this.cityState = cityState;
		parseCityState(cityState);
	}

	
	private void parseCityState(String str) {
	    city = "Unknown";
	    state = "Unknown";
	    
	    int index = str.lastIndexOf(",");
	    if(index > -1) {
	    	city = str.substring(0, index);
	    	city = city.replaceAll(",", "");
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
	
	public String getEventAge() {
		return eventAge;
	}

	public void setEventAge(String eventAge) {
		this.eventAge = eventAge;
	}

	public String getTeamAge() {
		return teamAge;
	}

	public void setTeamAge(String teamAge) {
		this.teamAge = teamAge;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	@Override
	public String toString() {
		return "ASATournamentEntry [eventAge=" + eventAge + ", teamAge="
				+ teamAge + ", cityState=" + cityState + "]";
	}
	
}
