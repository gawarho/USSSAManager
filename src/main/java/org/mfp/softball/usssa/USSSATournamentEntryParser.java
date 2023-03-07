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
import org.mfp.softball.data.BaseTournamentEntry;
import org.mfp.softball.data.BaseTournamentInfo;
import org.mfp.softball.usssa.data.USSSATournamentEntry;
import org.mfp.softball.usssa.data.USSSATournamentInfo;

/**
 * @author gawarho
 *
 */
public class USSSATournamentEntryParser {
	private Logger logger = null;

	public USSSATournamentEntryParser() {
		logger = LogManager.getLogger(USSSATournamentEntryParser.class.getName());
	}

	public USSSATournamentInfo parseTournament(String html, String year) {
		USSSATournamentInfo uti = new USSSATournamentInfo();
		return parseTournament(html, year, uti);
	}
	@SuppressWarnings("unused")
	public USSSATournamentInfo parseTournament(String html, String year, USSSATournamentInfo uti) {
		Document doc = Jsoup.parse(html); 
		Elements es = doc.select("table");
		if(es.size() > 10) {
			Element e = es.get(10);
			Elements rows = e.select("tr");
			boolean headerRow = true;
			boolean paid = false;
			boolean cc = false;
			boolean free = false;
			boolean waitlisted = false;
			int newCount = 0;
			for(Element r : rows) {
				if(headerRow) {
					headerRow = false;
					continue;
				}

				Elements cells = r.select("td");
				if(cells.size() == 1) {
					if(cells.get(0).text().equalsIgnoreCase(
							"Participant would like to send check in advance (Entry Pending).")) {
						logger.trace("Not paid");
						paid = false;
						cc = false;
						free = false;
						waitlisted = false;
					} else if(cells.get(0).text().equalsIgnoreCase(
							"Tournament Fee Received. (Entry Reserved).")) {
						logger.trace("Paid");
						paid = true;
						cc = false;
						free = false;
						waitlisted = false;
					} else if(cells.get(0).text().equalsIgnoreCase(
							"Online Credit Card fee received (Entry Reserved)")) {
						logger.trace("CC");
						paid = true;
						cc = true;
						free = false;
						waitlisted = false;
					} else if(cells.get(0).text().equalsIgnoreCase(
							"Director has placed your team on a waiting list(Entry Pending).")) {
						logger.trace("Waitlisted");
						paid = false;
						cc = false;
						free = false;
						waitlisted = true;
					}
				} else {
					if(cells.size() == 14) {
						String reg = cells.get(1).text();
						boolean insurance = false;
						if(reg.lastIndexOf("Ins Cert") > 0) {
							String [] parts = reg.split("\\s+");
							reg = parts[0];
							insurance = true;
							logger.trace("Insurance");
						}
						String state = cells.get(2).text();
						String city = cells.get(3).text();
						String team = cells.get(4).text();
						Elements ems2 = cells.get(4).select("a");
						if(ems2.size() > 0) {
							reg = ems2.first().attr("href");
							reg = reg.replace("TeamSchedule.asp?teamid=", "");
							logger.trace(reg);
						}
						String rStatus = cells.get(5).text();
						String rApproved = cells.get(6).text();
						String addApproved = cells.get(7).text();
						String addFrozen = cells.get(8).text();
						String players = cells.get(9).text();
						String classification = cells.get(10).text();
						String manager = cells.get(11).text();
						String email = null;
						Elements ems = cells.get(11).select("a");
						if(ems.size() > 0) {
							email = ems.first().attr("href");
							email = email.replace("mailto:", "");
							logger.trace(email);
						}
						String phone = cells.get(12).text();
						String [] arr2 = phone.split("\\s+");
						String cellPhone = arr2[0];
						String altPhone = arr2[1];
						logger.trace("Cell: " + cellPhone + "  Alt: " + altPhone);
						String roster = cells.get(13).text();
						USSSATournamentEntry te = new USSSATournamentEntry(team, reg, classification, manager, year);
						te.setPaid(paid);
						te.setCc(cc);
						te.setWaitlist(waitlisted);
						te.setFree(free);
						te.setCity(city);
						te.setState(state);
						te.setAltPhone(altPhone);
						te.setCellPhone(cellPhone);
						te.setEmailAddr(email);
						te.setInsurance(insurance);
						te.setRoster(Integer.parseInt(players));
						logger.debug("added " + team);
						uti.addEntry(te);
						newCount++;
					} else {
						logger.error("Not enough cells in " + "");
					}
				} // end number of cells to determine if header or team entry
			}
			logger.debug("New Count: " + newCount + "  Entries: " + uti.getEntries().size());
		} else {
			logger.debug("No entries");
		}

		return uti;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSATournamentEntryParser ep = new USSSATournamentEntryParser();

		try {
			BufferedReader br = new BufferedReader(new FileReader("test.html"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			BaseTournamentInfo uti = ep.parseTournament(sb.toString(), "2015");
			for(BaseTournamentEntry te : uti.getEntries()) {
				System.out.println(te.toString());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
