/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentEntry;

/**
 * @author gawarho
 *
 */
public class USSSATournamentEntry extends BaseTournamentEntry { 
	private String division = null;
	private String classification = null;
	private String coach = null;
	private String emailAddr = null;
	private String cellPhone = null;
	private String altPhone = null;
	private boolean insurance = false;
	private int roster = 0;
	private boolean paid = false;
	private boolean cc = false;
	private boolean free = false;
	private boolean waitlist = false;
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public USSSATournamentEntry(String name, String id, String classification, String coach, String year) {
		super(name, year);
		logger = LogManager.getLogger(TournamentEntry.class.getName());
		this.classification = classification;
		this.tAge = USSSAUtils.getAgeFromClassification(classification);
		this.division = USSSAUtils.getDivisionFromClassification(classification);
		this.coach = coach;
		this.id = id;
	}

	
	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}


	public String getCoach() {
		return coach;
	}


	public void setCoach(String coach) {
		this.coach = coach;
	}


	public String getEmailAddr() {
		return emailAddr;
	}


	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}


	public String getCellPhone() {
		return cellPhone;
	}


	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	public String getAltPhone() {
		return altPhone;
	}


	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}


	public boolean isInsurance() {
		return insurance;
	}


	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}


	public int getRoster() {
		return roster;
	}


	public void setRoster(int roster) {
		this.roster = roster;
	}


	public boolean isPaid() {
		return paid;
	}


	public void setPaid(boolean paid) {
		this.paid = paid;
	}


	public boolean isCc() {
		return cc;
	}


	public void setCc(boolean cc) {
		this.cc = cc;
	}


	public boolean isFree() {
		return free;
	}


	public void setFree(boolean free) {
		this.free = free;
	}


	public boolean isWaitlist() {
		return waitlist;
	}


	public void setWaitlist(boolean waitlist) {
		this.waitlist = waitlist;
	}

	@Override
	public String toCSVString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "," + id + "," + state + "," + emailAddr + "," + tAge + "," + division);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "USSSATournamentEntry [division=" + division
				+ ", classification=" + classification + ", coach=" + coach
				+ ", emailAddr=" + emailAddr + ", cellPhone=" + cellPhone
				+ ", altPhone=" + altPhone + ", insurance=" + insurance
				+ ", roster=" + roster + ", paid=" + paid + ", cc=" + cc
				+ ", free=" + free + ", waitlist=" + waitlist + ", name=" + name 
				+ ", id=" + id + ", eAge=" + eAge
				+ ", tAge=" + tAge + ", city=" + city + ", state=" + state
				+ ", year=" + year + "]";
	}	
}
