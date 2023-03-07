/**
 * 
 */
package org.mfp.softball.pony;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.pony.data.PonyTournamentData;
import org.mfp.softball.pony.data.PonyTournamentInfo;

import java.util.ArrayList;

/**
 * @author Glen
 *
 */
public class PonyTournamentReporter {
	private static final String [] agesToReport = {"10", "12", "14", "16", "18"};
	private static final String [] statesToReport = { "MD", "DE", "VA", "PA", "NJ", "NY", "CT", "MA" };
	private Logger logger = null;
	private String year = "";

	/**
	 * 
	 */
	public PonyTournamentReporter(String year) {
		logger = LogManager.getLogger(PonyTournamentReporter.class.getName());
		this.year = year;
	}
	
	public boolean processPonyTournaments() {
		boolean retVal = false;
		PonyWebAccessor pwa = new PonyWebAccessor();
		String mainHtml = pwa.getMainPage();
		PonyMainPageProcessor pmpp = new PonyMainPageProcessor();
		ArrayList<PonyTournamentData> arrPtd = pmpp.parseTournaments(mainHtml, year);
		PonyEntryParser pep = new PonyEntryParser();
		String header = "Name,City,State,Start,End,";
		StringBuilder sb2 = new StringBuilder(header);
		for(String a : agesToReport) {
			sb2.append(a + ",");
		}
		for(String s : statesToReport) {
			sb2.append(s + ",");
		}
		logger.info(sb2.toString());
		for(PonyTournamentData p : arrPtd) {
			String html = pwa.getTournamentById(p.getId());
			PonyTournamentInfo pti = pep.parseTournament(html);
			StringBuilder sb = new StringBuilder();
			sb.append(p.getName() + ",");
			sb.append(p.getCity() + ",");
			sb.append(p.getState() + ",");
			sb.append(p.getStartDate() + ",");
			sb.append(p.getEndDate() + ",");
			for(String a : agesToReport) {
				int count = pti.getEntryByAge(Integer.parseInt(a));
				sb.append(new Integer(count).toString() + ",");
			}
			for(String s : statesToReport) {
				int count = pti.getEntryByState(s);
				sb.append(new Integer(count).toString() + ",");
			}
			logger.info(sb.toString());
		}
		
		return retVal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PonyTournamentReporter ptr = new PonyTournamentReporter("2015");
		ptr.processPonyTournaments();
	}

}
