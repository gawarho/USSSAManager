/**
 * 
 */
package org.mfp.softball.usssa;

import org.mfp.common.BaseSelenium;
import org.mfp.common.util.Sleep;
import org.mfp.softball.data.Team;
import org.mfp.softball.data.TeamInfo;
import org.mfp.softball.usssa.data.USSSATeam;
import org.mfp.softball.usssa.data.USSSAUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;
/**
 * @author Glen
 *
 */
public class USSSASeleniumAccessor extends BaseSelenium {

	private int sleep = 0;

	private String tournamentsUrl = "http://dc2.usssa.com";

	private boolean loggedIn = false;
	//private String userId = "gawarho2";
	private String userId = "bdowell";
	//private String password = "!Q2w#E4r%T";
	private String password = "Ravens10*";
	private String loginUrl = "https://dc2.usssa.com/#/login";
	private String logoutUrl = "https://dc2.usssa.com/#/login";

	private String mainMenuUrl = "https://dc2.usssa.com/#/login";

	private String rosterManagerUrl = "http://engine.usssa.com/sports/RosterManager.asp?Sport=16";
	private String rosterManagerUrlNoSport = "http://engine.usssa.com/sports/RosterManager.asp";

	private String mainURL = null;
	public USSSASeleniumAccessor() {
		super();
	}

	public boolean login() {
		String retVal = "";

		if(!loggedIn) {
			driver.get(logoutUrl);
			//WebElement user = driver.findElement(By.id("username"));
			WebElement user = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/form/div[1]/input"));
			user.sendKeys(userId);
			WebElement pw = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/form/div[2]/input"));
			pw.sendKeys(password);			
			//WebElement submit = driver.findElement(By.name("Submit"));
			WebElement submit = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/form/button"));
			submit.click();
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			//driver.get(rosterManagerUrlNoSport);
			String mainWindow = driver.getWindowHandle();

			// Click on Team Mgmt
			WebElement we = driver.findElement(By.xpath("/html/body/div/div[1]/div/nav/div/div[2]/ul[1]/li[3]/a"));
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			we.click();

			// Team Manager
			we = driver.findElement(By.xpath("/html/body/div/div[1]/div/nav/div/div[2]/ul[1]/li[3]/ul/li[4]/a"));
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			we.click();

			Set<String> windows = driver.getWindowHandles();
			String subWindow = null;
			for(String s : windows) {
				if(! s.equals(mainWindow)) {
					subWindow = s;
					break;
				}
			}
			driver.switchTo().window(subWindow);

			// click on Fastpitch
			we = driver.findElement(By.xpath("/html/body/a[4]"));
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			we.click();
			mainURL = driver.getCurrentUrl();
			loggedIn = true;
		}

		return loggedIn;		
	}

	/**
	 * 
	 */
	public boolean logout() {
		boolean retVal = true;

		if(loggedIn) {
			driver.get(logoutUrl);
			loggedIn = false;
		}

		return retVal;
	}

	public TeamInfo getTeams(String state, String year, String age, String division) {
		TeamInfo ti = new TeamInfo();
		return getTeams(state, year, age, division, ti);
	}


	public TeamInfo getTeams(String state, String year, String age, String division, TeamInfo ti) {
		return getTeams(state, year, age, division, ti, false);
	}

	public TeamInfo getTeams(String state, String year, String age, String division, TeamInfo ti, boolean details) {
		//TeamInfo ti2 = new TeamInfo();

		String stateCode = USSSAUtils.getStateToCode(state);
		String classCode = USSSAUtils.getAgeDivisionCode(age, division);
		String seasonCode = USSSAUtils.getYearToCode(year);
		driver.navigate().to(mainURL);
		try { Thread.sleep(3000); } catch(Exception e) {};

		//System.out.println("StateCode: " + stateCode + "  ClassCode: " + classCode + " seasonCode: " + seasonCode);
        System.out.println("Year " + year + " State " + state + " Age " + age + " Division " + division);
		WebElement seasonId = driver.findElement(By.name("SeasonId"));
		List<WebElement> opts = seasonId.findElements(By.tagName("option"));
		for(WebElement opt : opts) {
			if(opt.getAttribute("value").equalsIgnoreCase(seasonCode)) {
				opt.click();
				break;
			}
		}
		//try { Thread.sleep(1000); } catch(Exception e) {};

		WebElement stateId2 = driver.findElement(By.name("StateID2"));
		opts = stateId2.findElements(By.tagName("option"));
		for(WebElement opt : opts) {
			if(opt.getAttribute("value").equalsIgnoreCase(stateCode)) {
				opt.click();
				break;
			}
		}
		//try { Thread.sleep(1000); } catch(Exception e) {};
		WebElement classId2 = driver.findElement(By.name("ClassID2"));
		opts = classId2.findElements(By.tagName("option"));
		for(WebElement opt : opts) {
			if(opt.getAttribute("value").equalsIgnoreCase(classCode)) {
				opt.click();
				break;
			}
		}
		//try { Thread.sleep(1000); } catch(Exception e) {};

		// Click Filter button
		WebElement filterElement = driver.findElement(By.xpath("/html/body/form/div/div/table/tbody/tr[1]/td/table/tbody/tr/td[6]/input"));
		filterElement.submit();
		try { Thread.sleep(5000); } catch(Exception e) {};

		int count = 0;
		WebElement teamId = driver.findElement(By.name("TeamID"));
		List<WebElement> teams = teamId.findElements(By.tagName("option"));
		for(int i=0; i<teams.size(); i++) {
		//for(WebElement team : teams) {
			WebElement team = teams.get(i);
			String id = team.getAttribute("value");
			String text = team.getText();
			//System.out.println(text);
			if(text.toLowerCase().startsWith("the search criteria options")) {
				break;
			}
			String name = text.substring(0, 33).replaceAll("^\\s+", "").trim();
			//System.out.println("012345678901234567890123456789012345678901234567890");
			//System.out.println(text);
			text = text.substring(35).trim();
			//System.out.println(text);
			String [] parts = text.split("\\s+", 6);
			String tournaments = parts[0];
			String stateA = parts[1].trim();
			String teamRegId = parts[2].trim();
			if(teamRegId.equals("N.")) {
				continue;
			}
			String teamClass = parts[4].trim();
			//System.out.println(teamClass);
			text = parts[5];
			if(text.length() < 27) {
				continue;
			}
			String coach = text.substring(0, 14).trim();
			String telephone = text.substring(15,27).trim();

			//System.out.println("Reg: " + teamRegId + " name: " + name + " Class: " + teamClass + " Year: " + year + " Coach: " + coach + " State: " + stateA);
			USSSATeam t = new USSSATeam(teamRegId, name, teamClass, year, coach, null, stateA);
			team.click();
			// Edit Manager
			WebElement we2 = driver.findElement(By.xpath("/html/body/form/div/div/table/tbody/tr[3]/td/p/input[1]"));
			we2.click();

			//
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[1]/td[2]/input"));
			String firstName = we2.getAttribute("value");
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[2]/td[2]/input"));
			String lastName = we2.getAttribute("value");
			t.setCoach(firstName + " " + lastName);
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[7]/td[2]/input"));
			String cellPhone = we2.getAttribute("value");
			t.setTelephone(cellPhone);
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[8]/td[2]/input"));
			String otherPhone = we2.getAttribute("value");
			t.setSecPhone(otherPhone);
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[10]/td[2]/input"));
			String email = we2.getAttribute("value");
			t.setEmail(email);
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[3]/td[2]/input"));
			String address = we2.getAttribute("value");
			t.setAddress(address);
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[4]/td[2]/input"));
			String city = we2.getAttribute("value");
			t.setCity(city);
     		we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[5]/td[2]/select"));
			Select select = new Select(we2);
			WebElement fSopt = select.getFirstSelectedOption();
			t.setCoachState(fSopt.getText());
			we2 = driver.findElement(By.xpath("/html/body/form[1]/table/tbody/tr[6]/td[2]/input"));
			String zip = we2.getAttribute("value");
			t.setZip(zip);

			try {
				Thread.sleep(2000);
			} catch(Exception e) {}
			driver.navigate().back();
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}

			ti.addEntry(t);
			teamId = driver.findElement(By.name("TeamID"));
			teams = teamId.findElements(By.tagName("option"));

			count++;
			//if(count >= 2) { break; }
		}
		System.out.println("State: " + state + " season: " + year + " Count: " + count);

		return ti;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSASeleniumAccessor ua = new USSSASeleniumAccessor();
		ua.login();
		TeamInfo ti = ua.getTeams("MD", "2015", "15", "");
		for(Team t : ti.getEntries()) {
			System.out.println(t.toCSVString());
		}
		ua.close();
	}
}
