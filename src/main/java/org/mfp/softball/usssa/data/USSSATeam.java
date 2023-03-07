/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.mfp.softball.data.Team;

/**
 * @author Glen
 *
 */
public class USSSATeam extends Team {

	private String istsId = null;
	
	/**
	 * @param id
	 * @param name
	 * @param age
	 * @param division
	 * @param season
	 * @param coach
	 * @param city
	 * @param state
	 */
	public USSSATeam(String id, String name, String age, String division,
			String season, String coach, String city, String state) {
		super(id, name, age, division, season, coach, city, state);
		// TODO Auto-generated constructor stub
	}

	public USSSATeam(String id, String name, String ageDivision, String season, String coach, String city, String state) {
		super(id, name, null, null, season, coach, city, state);
		//System.out.println(name);
		int age2 = USSSAUtils.getAgeFromTournamentDivision(ageDivision);
		age = new Integer(age2).toString();
		//division = USSSAUtils.getDivisionFromTournamentDivision(ageDivision);
        division = USSSAUtils.getDivisionFromTournamentDivision(ageDivision);
		//System.out.println(name + " " + ageDivision + " " + age + " " + division);
	}

	public String getIstsId() {
		return istsId;
	}

	public void setIstsId(String istsId) {
		this.istsId = istsId;
	}

	public static String getCSVHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("Team Name,");
		sb.append("Team State,");
		sb.append("Age,");
		sb.append("Division,");
		sb.append("Email,");
		sb.append("Coach,");
		sb.append("Address,");
		sb.append("City,");
		sb.append("Coach State,");
		sb.append("Zip,");
		sb.append("Cell,");
		sb.append("Other phone");

		return sb.toString();
	}

	@Override
	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ",");
		sb.append(teamState + ",");
		sb.append(age + ",");
		sb.append(division + ",");
		sb.append(email + ",");
		sb.append(coach + ",");
		sb.append(address + ",");
		sb.append(city + ",");
		sb.append(coachState + ",");
		sb.append(zip + ",");
		sb.append(telephone + ",");
		sb.append(secPhone + ",");

		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "USSSATeam [istsId=" + istsId + ", id=" + id + ", name=" + name
				+ ", coach=" + coach + ", teamState=" + teamState + ", age="
				+ age + ", division=" + division + ", city=" + city
				+ ", coachState=" + coachState + ", season=" + season
				+ ", telephone=" + telephone + ", email=" + email
				+ ", address=" + address + ", zip=" + zip + ", secPhone="
				+ secPhone + "]";
	}
}
