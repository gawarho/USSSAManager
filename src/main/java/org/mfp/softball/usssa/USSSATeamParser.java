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
import org.mfp.softball.data.Team;
import org.mfp.softball.data.TeamInfo;

/**
 * @author Glen
 *
 */
public class USSSATeamParser {
	private Logger logger = null;

	/**
	 * 
	 */
	public USSSATeamParser() {
		logger = LogManager.getLogger(USSSATeamParser.class.getName());
	}
	public TeamInfo parseTeams(String html, String season, String state, String age, String division) {
		TeamInfo ti = new TeamInfo();
		return ti = parseTeams(html, season, state, age, division, ti);
	}

	public TeamInfo parseTeams(String html, String season, String state, String age, String division, TeamInfo ti) {
		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		logger.debug("\n\n\n" + es.size());
		//logger.debug(es.toString());
		int entries = 0;
		if(es.size() > 12) {
			Element e = es.get(12);
			Elements rows = e.select("tr");
			//logger.trace(rows.size());
			Element r =rows.get(0);
			Elements options = r.getElementsByTag("option");
			for(Element o : options) {
				String text = o.text();

				String team = text.substring(0, 20).trim();
				team = team.replace("\u00a0", "");
				if(team.startsWith("No Teams have been")) {
					continue;
				}				String city = text.substring(21,  36).trim();
				city = city.replace("\u00a0", "");
				String manager = text.substring(37).trim();
				manager = manager.replace("Manager: ", "");
				manager = manager.replace("\u00a0", "");
				String id = o.attr("value").trim();
				id = id.replace("\u00a0", "");
				Team t = new Team(id, team, age, division, season, manager, city, state);
				logger.debug("added " + team + "  " + city + "  " + manager + "  " + id);
				ti.addEntry(t);
				entries++;
			} // end number of cells to determine if header or team entry
		}
		logger.debug("Season: " + season + "  State: " + state + "  Age: " + age + "  Division: " + division + "  Entries: " + entries);

		return ti;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSATeamParser tp = new USSSATeamParser();
		try {
			BufferedReader br = new BufferedReader(new FileReader("usssa.teams.html"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			TeamInfo ti = tp.parseTeams(sb.toString(), "2015", "18", "A", "MD");
			System.out.println(ti.toString());
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
