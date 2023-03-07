/**
 * 
 */
package org.mfp.softball.usssa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.Team;
import org.mfp.softball.data.TeamInfo;
import org.mfp.softball.usssa.data.USSSATeam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glen
 *
 */
public class USSSASeasonTeams2Reporter { 
	//private static final String [] seasonsToReport = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"};
	//private static final String [] seasonsToReport = {"2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"};
	private static final String [] seasonsToReport = { "2023"};
	//private static final String [] agesToReport = {"8", "9", "10", "11", "12", "14", "15", "16", "18"};
	//private static final String [] agesToReport = { "10", "11", "12", "13", "14" };
	//private static final String [] agesToReport = { "8", "9" };
	private static final String [] agesToReport = { "9", "10", "11", "12", "13", "14", "15", "16", "18" };
	//private static final String [] divisionsToReport = { "", "AS", "C", "B", "A" };
	private static final String [] divisionsToReport = { "A", "B", "C", "O" };
	//private static final String [] statesToReport = { "VA", "MD", "DE", "VA", "PA", "WV", "NJ" };
	private static final String [] statesToReport = { "CT", "MA", "RI", "NY", "NH", "VT", "ME", "MD", "DE", "VA", "PA", "WV", "NJ" };
	//private static final String [] statesToReport = { "MD" };
	private Logger logger;

	private boolean listTeams = true;
	private boolean listSummary = false;
	
	/**
	 * 
	 */
	public USSSASeasonTeams2Reporter() {
		logger = null;
		logger = LogManager.getLogger(USSSASeasonTeams2Reporter.class.getName());
	}

	public boolean processTournaments() {
		boolean retVal = false;
		USSSASeleniumAccessor ua = new USSSASeleniumAccessor();
		ua.setSleep(100);
		ua.login();
		//USSSATeamParser tp = new USSSATeamParser();
		TeamInfo ti = new TeamInfo();
		for( String state : statesToReport) {
			for( String season : seasonsToReport) {
				for(String age : agesToReport) {
					for(String division : divisionsToReport) {
						logger.info("State: " + state + "  season: " + season + "  age: " + age + "  division: " + division);
						Integer ii = Integer.parseInt(age);
						if((ii % 2) == 0 && division.equalsIgnoreCase("")) {
							logger.debug("Skipping " + division + " age " + age);
							continue;
						}
						if((ii % 2) == 1 && (division.equalsIgnoreCase("A") == true ||
								             division.equalsIgnoreCase("B") == true)) {
							logger.debug("Skipping " + division + " age " + age);
							continue;
						}
						ti = ua.getTeams(state, season, age, division, ti, false);
						logger.trace(ti.toString());
					}
				}
			}
		}
		ua.logout();
		ua.close();
		
		if(listTeams) {
			System.out.println(USSSATeam.getCSVHeader());
			for(Team team : ti.getEntries()) {
				System.out.println(team.toCSVString());
			}
		} 
		
		if(listSummary) {
			for( String state : statesToReport) {
				StringBuilder sb = new StringBuilder();
				sb.append("State,Year,");
				for(String age : agesToReport) {
					for(String division : divisionsToReport) {
						sb.append(age + division + ",");
					}
					sb.append(age + "Total,");
				}
				sb.append("Open,ASTotal,CTotal,BTotal,ATotal,YearTotal");
				System.out.println(sb.toString());
				for( String season : seasonsToReport) {
					sb = new StringBuilder();
					sb.append(state + "," + season + ",");
					int yearTotal = 0;
					int ageTotal = 0;
					Map<String, Integer> divisionMap = new HashMap<String, Integer>();
					for(String division : divisionsToReport) {
						divisionMap.put(division, 0);
					}
					for(String age : agesToReport) {
						ageTotal = 0;
						for(String division : divisionsToReport) {
							int count = ti.getTeams(season, state, new Integer(age).intValue(), division);
							yearTotal += count;
							ageTotal += count;
							sb.append(count + ",");
							int val = divisionMap.get(division);
							val += count;
							divisionMap.put(division, val);
						}
						sb.append(ageTotal + ",");
					}
					for(String division : divisionsToReport) {
						sb.append(divisionMap.get(division) + ",");
					}
					sb.append(yearTotal);
					System.out.println(sb.toString());
				}
				System.out.println();
			}
		}

		return retVal;
	}

	public boolean isListTeams() {
		return listTeams;
	}

	public void setListTeams(boolean listTeams) {
		this.listTeams = listTeams;
	}

	public boolean isListSummary() {
		return listSummary;
	}

	public void setListSummary(boolean listSummary) {
		this.listSummary = listSummary;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSASeasonTeams2Reporter ptr = new USSSASeasonTeams2Reporter();
		ptr.processTournaments();
	}

}
