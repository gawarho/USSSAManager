/**
 * 
 */
package org.mfp.softball.usssa;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.TeamInfo;

/**
 * @author Glen
 *
 */
public class USSSASeasonTeamsReporter {
	//private static final String [] seasonsToReport = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015"};
	private static final String [] seasonsToReport = { "2022"};
	//private static final String [] agesToReport = {"10", "12", "14", "16", "18"};
	private static final String [] agesToReport = {"10"};
	//private static final String [] divisionsToReport = { "AS", "C", "B", "A" };
	private static final String [] divisionsToReport = { "C" };
	//private static final String [] statesToReport = { "MD", "DE", "VA", "PAW", "PAC", "PAE", "NJ", "NYU", "NYD", "CT", "MA" };
	private static final String [] statesToReport = { "MD" };
	private Logger logger = null;

	/**
	 * 
	 */
	public USSSASeasonTeamsReporter() {
		logger = LogManager.getLogger(USSSASeasonTeamsReporter.class.getName());
	}

	public boolean processTournaments() {
		boolean retVal = false;
		System.out.println("Start");
		USSSAWebAccessor ua = new USSSAWebAccessor();
		System.out.println("login");
		ua.login();
		USSSATeamParser tp = new USSSATeamParser();
		TeamInfo ti = new TeamInfo();
		for( String state : statesToReport) {
			for( String season : seasonsToReport) {
				for(String age : agesToReport) {
					for(String division : divisionsToReport) {
						String html = ua.getTeams(state, season, age, division);
						ti = tp.parseTeams(html, season, state, age, division, ti);
						logger.trace(ti.toString());
					}
				}
			}
		}
		ua.logout();
		for( String state : statesToReport) {
			StringBuilder sb = new StringBuilder();
			sb.append("State,Year,");
			for(String age : agesToReport) {
				for(String division : divisionsToReport) {
					sb.append(age + division + ",");
				}
				sb.append(age + "Total,");
			}
			sb.append("ASTotal,CTotal,BTotal,ATotal,YearTotal");
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
		
		return retVal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSASeasonTeamsReporter ptr = new USSSASeasonTeamsReporter();
		ptr.processTournaments();
	}

}
