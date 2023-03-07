/**
 * 
 */
package org.mfp.softball.asa;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mfp.softball.data.BaseTournamentEntry;
import org.mfp.softball.data.BaseTournamentInfo;
import org.mfp.softball.asa.data.ASATournamentEntry;

/**
 * @author gawarho
 *
 */
public class ASAEntryParser {
	private Logger logger = null;

	public ASAEntryParser() {
		logger = LogManager.getLogger(ASAEntryParser.class.getName());
	}

	public BaseTournamentInfo parseTournament(String html) {
        BaseTournamentInfo pti = new BaseTournamentInfo();
        
		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		//logger.debug(es.size());
		int entries = 0;
		if(es.size() > 6) {
			Element e = es.get(6);
			//logger.trace(e.toString());
			Elements rows = e.select("tr");
			//logger.trace(rows.size());
			boolean headerRow = true;
			for(Element r : rows) {
				if(headerRow) {
					headerRow = false;
					continue;
				}

				Elements cells = r.select("td");
				if(cells.size() == 4) {
					String name = cells.get(0).text();
					String eventAge = cells.get(1).text();
					String teamAge = cells.get(2).text();
					String cityState = cells.get(3).text();
					ASATournamentEntry pe = new ASATournamentEntry(name, eventAge, teamAge, cityState);
					logger.debug("added " + name);
					pti.addEntry(pe);
					entries++;
				} else {
					logger.error("Not enough cells in " + "");
				}
			} // end number of cells to determine if header or team entry
		}
		logger.debug("Entries: " + entries);

		return pti;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ASAEntryParser ep = new ASAEntryParser();

		try {
			BufferedReader br = new BufferedReader(new FileReader("asa.html"));
			String line = null;
			while((line = br.readLine()) != null) {
				BaseTournamentInfo pti = ep.parseTournament(line);
				for(BaseTournamentEntry te : pti.getEntries()) {
					System.out.println(te.toString());
				}
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
