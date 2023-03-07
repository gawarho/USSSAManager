/**
 * 
 */
package org.mfp.softball.pony;

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
import org.mfp.softball.pony.data.PonyTournamentData;

/**
 * @author gawarho
 *
 */
public class PonyMainPageProcessor {
	private Logger logger = null;

	public PonyMainPageProcessor() {
		logger = LogManager.getLogger(PonyMainPageProcessor.class.getName());
	}

	public ArrayList<PonyTournamentData> parseTournaments(String html, String year) {
		ArrayList<PonyTournamentData> arr = new ArrayList<PonyTournamentData>();

		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		//logger.debug(es.size());
		if(es.size() > 2) {
			Element e = es.get(2);
			logger.debug(e.toString());
			//System.exit(1);
			Elements rows = e.select("tr");
			//logger.debug(rows.size());
			boolean headerRow = true;
			for(Element r : rows) {
				if(headerRow) {
					headerRow = false;
					continue;
				}

				Elements cells = r.select("td");
				if(cells.size() == 10) {
					String name = cells.get(0).text().trim();
					Elements ems = cells.get(0).select("a");
					String id = null;
					if(ems.size() > 0) {
						id = ems.first().attr("name").trim();
					}
					String ages = cells.get(1).text().trim();
					String type = cells.get(2).text().trim();
					String dates = cells.get(3).text().trim();
					String location = cells.get(4).text().trim();
					String cost = cells.get(5).text().trim();
					String contact = cells.get(6).text().trim();
					String regStatus = cells.get(7).text().trim();
					String games = cells.get(8).text().trim();
					String zone = cells.get(9).text().trim();
					PonyTournamentData pd = new PonyTournamentData(name, id, type, games, year,
							cost, ages, dates, location, contact, regStatus, zone);
					arr.add(pd);
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
		PonyMainPageProcessor ep = new PonyMainPageProcessor();

		try {
			BufferedReader br = new BufferedReader(new FileReader("pony.main.html"));
			String line = null;
			while((line = br.readLine()) != null) {
				List<PonyTournamentData> l = ep.parseTournaments(line, "2015");
				for(PonyTournamentData te : l) {
					System.out.println(te.toString());
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
