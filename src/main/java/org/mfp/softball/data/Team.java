/**
 * 
 */
package org.mfp.softball.data;


/**
 * @author Glen
 *
 */
public class Team {
	protected String id;
	protected String name;
	protected String coach;
	protected String teamState;
	protected String age;
	protected String division;
	protected String city;
	protected String coachState;
	protected String season;
	protected String telephone;
	protected String email;
	protected String address;
	protected String zip;
	protected String secPhone;

	public Team(String id, String name, String age, String division, String season, String coach, String city) {
		this(id, name, age, division, season, coach, city, null);
	}

	public Team(String id, String name, String age, String division, String season, String coach, String city, String state) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.division = division;
		this.coach = coach;
		this.city = city;
		this.teamState = state;
		this.season = season;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSecPhone() {
		return secPhone;
	}

	public void setSecPhone(String secPhone) {
		this.secPhone = secPhone;
	}

	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ",");
		sb.append(coach + ",");
		sb.append(age + ",");
		sb.append(division + ",");
		sb.append(email);
		return sb.toString();
	}
	
	public String getTeamState() {
		return teamState;
	}

	public void setTeamState(String teamState) {
		this.teamState = teamState;
	}

	public String getCoachState() {
		return coachState;
	}

	public void setCoachState(String coachState) {
		this.coachState = coachState;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", coach=" + coach
				+ ", teamState=" + teamState + ", age=" + age + ", division="
				+ division + ", city=" + city + ", coachState=" + coachState
				+ ", season=" + season + ", telephone=" + telephone
				+ ", email=" + email + ", address=" + address + ", zip=" + zip
				+ ", secPhone=" + secPhone + "]";
	}

}

