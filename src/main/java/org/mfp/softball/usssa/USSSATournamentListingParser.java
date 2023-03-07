/**
 * 
 */
package org.mfp.softball.usssa;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mfp.softball.data.BaseTournamentData;
import org.mfp.softball.usssa.data.USSSATournamentData;
import org.mfp.softball.usssa.data.USSSATournamentListing;
/**
 * @author gawarho
 *
 */
public class USSSATournamentListingParser {
	private Logger logger = null;

	public USSSATournamentListingParser() {
		logger = LogManager.getLogger(USSSATournamentListingParser.class.getName());
	}
	
	public USSSATournamentListing parseTournaments(String html, String year) {
        USSSATournamentListing utl = new USSSATournamentListing();
        return utl;
	}


	public USSSATournamentListing parseTournaments(String html, String year, USSSATournamentListing utl) {
        
		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		logger.trace(es.size());
		int newCount = 0;
		if(es.size() > 11) {
			Element e = es.get(11);
			//logger.debug(e.toString());
			//System.exit(1);
			Elements rows = e.select("tr");
			//logger.debug(rows.size());
			boolean header = true;
			String name = null;
			String id = null;
			String cost1 = null;
			String cost2 = null;
			String director = null;
			String teams = null;
			String games = null;
			String startDate = null;
			String division = null;
			String city = null;
			String state = null;
			String park = null;
			String type = null;
			for(Element r : rows) {
				if(header) {
					header = false;
					continue;
				}

				Elements cells = r.select("td");
				if(cells.size() == 1) {
					continue;
				}
				if(cells.size() == 12 || cells.size() == 13) {
					cost1 = cells.get(0).text().trim();
					cost2 = cells.get(1).text().trim();
					director = cells.get(2).text().trim();
					startDate = cells.get(3).text().trim();
					division = cells.get(5).text().trim();
					name = cells.get(6).text().trim();
					teams = cells.get(7).text().trim();
					type = cells.get(8).text().trim();
					park = cells.get(9).text().trim();
					city = cells.get(10).text().trim();
					state = cells.get(11).text().trim();
					if(cells.size() == 13) {
						games = cells.get(12).text().trim();
					}
					Elements ems = cells.get(4).select("a");
					id = null;
					if(ems.size() > 0) {
						id = ems.first().attr("href").trim();
						id = id.replace("TournamentMoreInfo.asp?TournID=", "");
					}

					String cost = cost1;
					if(cost2 != null) {
						cost = cost2;
					}
					USSSATournamentData pd = new USSSATournamentData(id, name, division, cost, year,
							director, startDate, teams, type, park, city, state, games);
					utl.addEntry(pd);
					newCount++;
					logger.trace("Added: " + name);							
				} else {
					logger.error("Not enough cells in " + "");
				}
			} // end number of cells to determine if header or team entry
		}
		logger.debug("New Count: " + newCount + "  Entries: " + utl.getEntries().size());

		return utl;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSATournamentListingParser ep = new USSSATournamentListingParser();

		try {
			BufferedReader br = new BufferedReader(new FileReader("usssa.tourn.html"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			USSSATournamentListing uti = ep.parseTournaments(sb.toString(), "2015");
			for(BaseTournamentData te : uti.getEntries()) {
				System.out.println(te.toString());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
