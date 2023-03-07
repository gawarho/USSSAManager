/**
 * 
 */
package org.mfp.softball.asa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mfp.softball.asa.data.ASATournamentData;
/**
 * @author gawarho
 *
 */
public class ASAMainPageProcessor {
	private Logger logger = null;

	public ASAMainPageProcessor() {
		logger = LogManager.getLogger(ASAMainPageProcessor.class.getName());
	}

	public ArrayList<ASATournamentData> parseTournaments(String html, String year) {
		ArrayList<ASATournamentData> arr = new ArrayList<ASATournamentData>();

		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		logger.debug(es.size());
		if(es.size() > 1) {
			Element e = es.get(1);
			//logger.debug(e.toString());
			//System.exit(1);
			Elements rows = e.select("tr");
			//logger.debug(rows.size());
			int headerRows = 0;
			boolean found = false;
			String location = "";
			String location2 = null;
			String name = null;
			String id = null;
			String cost = null;
			String teams = null;
			String games = null;
			String ages = null;
			String dates = null;
			for(Element r : rows) {
				if(headerRows < 2) {
					headerRows++;
					continue;
				}
				logger.trace(r.toString());

				Elements cells = r.select("td");
				if(cells.size() == 1) {
					if(cells.get(0).text().length() == 0) {
						if(found == true) {
							ASATournamentData pd = new ASATournamentData(name, id, games, cost, year, ages, dates, location, teams);
							arr.add(pd);
							logger.debug("Added: " + name);							
						}
						found = false;
						continue;
					} else {
						if(found == true) {
							location2 = cells.get(0).text().trim();
							ASATournamentData pd = new ASATournamentData(name, id, games, cost, year, ages, dates, location2, teams);
							arr.add(pd);
							pd.setPark(location);
							logger.debug("Added: " + name);
						}
						found = false;
					}
					continue;
				}
				if(cells.size() == 14) {
					found = true;
					dates = cells.get(1).text().trim();
					dates = dates.replace("\u00a0",  "");
					logger.debug(dates);
					name = cells.get(3).text().trim();
					name = name.replace("\u00a0",  "");
					logger.debug(name);
					Elements ems = cells.get(3).select("a");
					logger.debug(ems.toString());
					id = null;
					if(ems.size() > 0) {
						id = ems.first().attr("href").trim();
						id = id.replace("/i%21/tournaments/tournamentDetails.php?TID=", "");
					}
					location = cells.get(5).text().trim();
					location = location.replace("\u00a0",  "");
					logger.debug(location);
					ages = cells.get(7).text().trim();
					ages = ages.replace("\u00a0",  "");
					logger.debug(ages);
					games = cells.get(9).text().trim();
					games = games.replace("\u00a0",  "");
					logger.debug(games);
					teams = cells.get(11).text().trim();
					cost = cost.replace("\u00a0",  "");
					logger.debug(teams);
					cost = cells.get(13).text().trim();
					cost = cost.replace("\u00a0",  "");
					logger.debug(cost);
				} else {
					logger.error("Not enough cells in " + "");
				}
			} // end number of cells to determine if header or team entry
		}
		logger.debug("Entries: " + arr.size());

		return arr;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ASAMainPageProcessor ep = new ASAMainPageProcessor();

		try {
			BufferedReader br = new BufferedReader(new FileReader("asa.main.html"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			List<ASATournamentData> l = ep.parseTournaments(sb.toString(), "2015");
			for(ASATournamentData te : l) {
				System.out.println(te.toString());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
