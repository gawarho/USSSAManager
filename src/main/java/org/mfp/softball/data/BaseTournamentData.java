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
public class BaseTournamentData { 
	protected String name = null;
	protected String id = null;
	protected ArrayList<String> ages = new ArrayList<String> ();
	protected String cost = null;
	protected String type = null;
	protected String contact = null;
	protected String email = null;
	protected String phone = null;
	protected String games = null;
	protected String city = null;
	protected String state = null;
	protected String startDate = null;
	protected String endDate = null;
	protected String year = null;
	protected String division = null;
	
	protected Logger logger = null;
	
	public BaseTournamentData(String name, String id, String type, String games, String cost, String year) {
		logger = LogManager.getLogger(BaseTournamentData.class.getName());
		this.name = name;
		this.id = id;
		this.games = games;
		this.type = type;
		this.cost = cost;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getAges() {
		return ages;
	}
	
	public int getFirstAge() {
		int retVal = 0;
		
		if(ages.size() > 0) {
			retVal = Integer.parseInt(ages.get(0));
		}
		
		return retVal;
	}

	public void setAges(ArrayList<String> ages) {
		this.ages = ages;
	}
	
	public void addAge(String age) {
		this.ages.add(age);
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String toShortString() {
		return "";
	}
	@Override
	public String toString() {
		return "BaseTournamentData [name=" + name + ", id=" + id + ", ages="
				+ ages + ", cost=" + cost + ", year=" + year + ", contact=" + contact + ", email="
				+ email + ", phone=" + phone + ", games=" + games + ", city="
				+ city + ", state=" + state + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", type=" + type + "]";
	}

	
}
