/**
 * 
 */
package org.mfp.softball.dcusssa.data;


/**
 * @author Glen
 *
 */
public class Team {
	protected long id = 0L;
	protected String name = "";
	protected String coach = "";
	protected String state = "";
	protected long age = 0L;
	protected String classification = "";
	protected String location = "";
	protected long season = 0L;
	protected String telephone = "";
	protected String email = "";
	protected String secPhone = "";

	public Team(String id, String name, long age, String division, long season, String coach) {
		this(Long.parseLong(id), name, age, division, season, coach, null, null);
	}

	public Team(Long id, String name, long age, String division, long season, String coach, String state, String city) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.classification = division;
		this.coach = coach;
		this.state = state;
		this.location = city;
		this.season = season;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getSeason() {
		return season;
	}

	public void setSeason(long season) {
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
		sb.append(classification + ",");
		sb.append(email);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Team{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", coach='" + coach + '\'' +
				", teamState='" + state + '\'' +
				", age='" + age + '\'' +
				", division='" + classification + '\'' +
				", city='" + location + '\'' +
				", season='" + season + '\'' +
				", telephone='" + telephone + '\'' +
				", email='" + email + '\'' +
				", secPhone='" + secPhone + '\'' +
				'}';
	}
}

