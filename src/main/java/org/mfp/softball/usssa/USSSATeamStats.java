/**
 * 
 */
package org.mfp.softball.usssa;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mfp.softball.data.Team;
import org.mfp.softball.usssa.data.USSSAUtils;
/**
 * @author Glen
 *
 */
public class USSSATeamStats {
	private String getTeamsUrl = "http://www.usssa.com/sports/Team2.asp";
	private String getTeamUrl = "http://www.usssa.com/sports/Team3.asp";

	public USSSATeamStats() {
	}

	@SuppressWarnings("unused")
	public Object [] getTeamList(String state, String classId, String seasonId) {

		URL url = null;
		Document doc = null;
		try {
			url = new URL(getTeamsUrl + "?State=" + state + "&ClassID=" + classId + "&SeasonID=" + seasonId);
			//System.out.println(url);
			doc = Jsoup.parse(url, 3000);
			//System.out.println(doc);
		} catch(Exception e) {
			System.err.println(e);
		}

		Elements tables = doc.select("table");
		//System.out.println(tables);
		Iterator<Element> ite = tables.iterator();
		int index = 1;
		ArrayList<Team> alRet = new ArrayList<Team>();
		while(ite.hasNext()) {
			Element table = ite.next();
			//System.out.println(index++ + "  " + table);
			//System.out.println();
			Elements a2 = table.select("select");
			if(a2 == null) {
				continue;
			}
			Iterator<Element> it9 = a2.iterator();
			boolean found = false;
			while(it9.hasNext()) {
				Element t = it9.next();
				String name = t.attributes().get("name");
				if(name != null) {
					if(name.equalsIgnoreCase("TeamID")) {
						found = true;
					}
				}
			}
			if(found == false) {
				continue;
			}
			System.out.println(table);
			Elements al = table.select("option");
			if(al != null) {
				Iterator<Element> ite2 = al.iterator();
				while(ite2.hasNext()) {
					Element t = ite2.next();
					System.out.println(t);
					//System.out.println(t.attributes().toString() + "  " + t.text());
					String teamId = null;
					//teamId = t.attributes().get("value");
					//System.out.println(teamId);
					String teamCity = null;
					String teamName = null;
					String teamReg = null;
					String dClass = null;
					if(false) {
						try {
							URL url2 = new URL(getTeamUrl + "?TeamID=" + teamId);
							Document doc2 = Jsoup.parse(url2, 3000);
							//System.out.println(doc2);
							Elements tablesT = doc2.select("table");
							//System.out.println(tables);
							Iterator<Element> iteT = tablesT.iterator();
							while(iteT.hasNext()) {
								Element tableT = iteT.next();
								//System.out.println(tableT);
								Elements alT = tableT.select("font");
								Iterator<Element> iteT2 = alT.iterator();
								while(iteT2.hasNext()) {
									Element font = iteT2.next();
									//System.out.println(font);
									String text = font.text().toLowerCase();
									if(iteT2.hasNext()) {
										font = iteT2.next();
										if(text.startsWith("team :")) {
											teamName = font.text();
											//System.out.println(text + "  " + teamName);
										}
										if(text.startsWith("team city :")) {
											teamCity = font.text();
											//System.out.println(text + "  " + teamCity);
										}
										if(text.startsWith("team registration :")) {
											teamReg = font.text();
											//System.out.println(text + "  " + teamReg);
										}
										if(text.startsWith("class :")) {
											dClass = font.text();
											//System.out.println(text + "  " + teamReg);
										}
									}
								}
							}
						} catch(Exception e) {
							System.err.println(e);
						}
					}

					if(false) {
						Team team = makeTeam(t.text());
						if(team != null) {
							team.setName(teamName);
							//team.setTeamReg(teamReg);
							//team.setLocation(teamCity);
							//team.setdClass(dClass);
							alRet.add(team);
						} else {
							//System.err.println("No team in " + t.text());
						}
					}
					index++;
				}
			} else {
				System.out.println("no option field");
			}
		}
		System.out.println("Teams: " + index);

		return alRet.toArray();
	}

	private Team makeTeam(String text) {
		System.out.println("Value 1: " + text);
		//Pattern p = Pattern.compile("([\\w\\s]+)\\s{2,}([\\w\\s]+)\\s{1,}Manager: ([\\w\\s]+)");
		Pattern p = Pattern.compile("Manager: ([\\w+\\s+])+");
		Matcher m = p.matcher(text);
		String coach = null;
		Team team = null;
		if(m.find()) {
			for (int i = 0; i < m.groupCount(); ++i) {
				//System.out.println(i + "  " + m.group(i));
			}
			//System.out.println(m.group(1));
			//System.out.println(m.group(2));
			//name = m.group(1);
			//location = m.group(2);
			coach = m.group(0);
			coach.replaceFirst("Manager :", "");
			//team = new Team(name, coach, location);
		} else {
			System.out.println();
		}

		return team;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSATeamStats ts = new USSSATeamStats();
		String state = "52";
		String classId = "1015";
		String seasonId = "18";
		Object [] teams2 = ts.getTeamList(state, classId, seasonId);
		System.out.println(state + " " + classId + " " + teams2.length);
		System.exit(0);
		ArrayList<String> al2 = new ArrayList<String>();
		al2.add("Year");
		al2.add("State");
		Set<String> s3 = USSSAUtils.getRelevantClasstocodemap().keySet();
		Iterator<String> it3 = s3.iterator();
		while(it3.hasNext()) {
			String dClass = it3.next();
			al2.add(dClass);
		}
		StringBuilder sb3 = new StringBuilder();
		for(int i=0; i<al2.size(); i++) {
			sb3.append(al2.get(i) + ",");
		}
		System.out.println(sb3.toString());
		Set<String> s1 = USSSAUtils.getYeartocodemap().keySet();
		Iterator<String> it1 = s1.iterator();
		while(it1.hasNext()) {
			ArrayList<String> al = new ArrayList<String>();
			String year = it1.next();
			al.add(year);
			al.add("MD");
			seasonId = USSSAUtils.getYearToCode(year).toString();
			Set<String> s = USSSAUtils.getRelevantClasstocodemap().keySet();
			Iterator<String> it = s.iterator();
			while(it.hasNext()) {
				String dClass = it.next();
				//al.add(dClass);
				classId = USSSAUtils.getRelevantClassToCode(dClass).toString();
				Object [] teams = ts.getTeamList(state, classId, seasonId);
				//System.out.println(year + " " + dClass + " " + teams.length);
				al.add(new Integer(teams.length).toString());
				for(int i=0; i<teams.length; i++) {
					//System.out.println(teams[i].toString());
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<al.size(); i++) {
				sb.append(al.get(i) + ",");
			}
			System.out.println(sb.toString());
		}
	}

}

