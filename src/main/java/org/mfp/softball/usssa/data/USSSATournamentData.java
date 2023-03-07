/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.mfp.softball.data.BaseTournamentData;

/**
 * @author Glen
 *
 */
public class USSSATournamentData extends BaseTournamentData {

	private String startDate = null;
	private String endDate = null;
	private int teams = 0;
	private String park = null;
	
	/**
	 * 
	 */
	public USSSATournamentData(String id, String name, String ageDivision, String cost, String year, String director, 
			String startDate, String teams, String stature, String park, String city, String state, 
			String games) { 
		super(name, id, stature, games, cost, year);
		this.contact = director;
		this.startDate = startDate;
		if(teams.equals("?")) {
			this.teams = 0;
		} else {
		    this.teams = Integer.parseInt(teams);
		}
		int age = USSSAUtils.getAgeFromTournamentDivision(ageDivision);
		addAge(new Integer(age).toString());
		division = USSSAUtils.getDivisionFromTournamentDivision(ageDivision);
		this.park = park;
		this.city = city;
		this.state = state;
	}

	public String toShortString() {
		return year + " " + state + " " + getFirstAge() + division + " " + id;
	}
	
	@Override
	public String toString() {
		return "USSSATournamentData [division=" + division
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", teams=" + teams + ", park=" + park + ", name=" + name
				+ ", id=" + id + ", ages=" + ages + ", cost=" + cost
				+ ", type=" + type + ", contact=" + contact + ", email="
				+ email + ", phone=" + phone + ", games=" + games + ", city="
				+ city + ", state=" + state + "]";
	}
}
