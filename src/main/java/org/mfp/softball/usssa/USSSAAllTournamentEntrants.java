/**
 * 
 */
package org.mfp.softball.usssa;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentData;
import org.mfp.softball.data.BaseTournamentEntry;
import org.mfp.softball.usssa.data.USSSATournamentInfo;
import org.mfp.softball.usssa.data.USSSATournamentListing;

/**
 * @author Glen
 *
 */
public class USSSAAllTournamentEntrants {
	private final String [] agesToReport = {"10", "12", "14", "16", "18"};
	//private final String [] agesToReport = { "18" };
	private final String [] divisionsToReport = {"AS", "C", "B", "A", "O"};
	//private final String [] divisionsToReport = {"C"};
	private Map<String, String> divisionsMap = new HashMap<String, String>();
	private final String [] statesToReport = { "WV", "MD", "DE", "VA", "PAE", "PAC", "PAW", "NJ", "NYD", "NYD", "CT", "MA" };
	//private static final String [] statesToReport = { "MD", "PAE" };
	private Logger logger = null;

	/**
	 * 
	 */
	public USSSAAllTournamentEntrants() {
		logger = LogManager.getLogger(USSSAAllTournamentEntrants.class.getName());
		for(String d : divisionsToReport) {
			divisionsMap.put(d, "1");
		}
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
					logger.trace(utl.getEntries().size());
				}
			}
		}

		USSSATournamentEntryParser utep = new USSSATournamentEntryParser();
		USSSATournamentInfo ti = new USSSATournamentInfo();
		for(BaseTournamentData utd : utl.getEntries()) {
			logger.trace(utd.getName());
			if(isToBeProcessed(utd.getDivision())) {
				String html = ua.getTournament(Integer.parseInt(utd.getId()));
				//System.out.println(html);
				ti = utep.parseTournament(html, year, ti);
			}
		}

		for(BaseTournamentEntry t : ti.getEntries()) {
			System.out.println(t.toCSVString());
		}

		ua.logout();

		return retVal;
	}

	private boolean isToBeProcessed(String d) {
		boolean retVal = false;
		if(divisionsMap.containsKey(d)) {
			retVal = true;
		}
		return retVal;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSAAllTournamentEntrants ptr = new USSSAAllTournamentEntrants();
		ptr.processTournaments("2015");
	}

}
