/**
 * 
 */
package org.mfp.softball.usssa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentData;
import org.mfp.softball.usssa.data.USSSATournamentListing;

/**
 * @author Glen
 *
 */
public class USSSATournamentReporter {
	//private static final String [] agesToReport = {"10", "12", "14", "16", "18"};
	private static final String [] agesToReport = { "14" };
	private static final String [] divisionsToReport = {"AS", "C", "B", "A", "O"};
	//private static final String [] statesToReport = { "MD", "DE", "VA", "PA", "NJ", "NY", "CT", "MA" };
	private static final String [] statesToReport = { "MD" };
	@SuppressWarnings("unused")
	private Logger logger = null;

	/**
	 * 
	 */
	public USSSATournamentReporter() {
		logger = LogManager.getLogger(USSSATournamentReporter.class.getName());
	}
	
	public boolean processTournaments(String year) {
		boolean retVal = false;
		USSSAWebAccessor ua = new USSSAWebAccessor();
		ua.login();
		USSSATournamentListingParser utlp = new USSSATournamentListingParser();
		USSSATournamentListing utl = new USSSATournamentListing();
		for(String state : statesToReport) {
			for(String age : agesToReport) {
				for(String division : divisionsToReport) {
					String html = ua.getTournamentInfo(state, year, age, division);
					utl = utlp.parseTournaments(html, year, utl);
				}
			}
		}
		
		for(BaseTournamentData utd : utl.getEntries()) {
			System.out.println(utd.toShortString());
		}
		
		return retVal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSATournamentReporter ptr = new USSSATournamentReporter();
		ptr.processTournaments("2015");
	}

}
