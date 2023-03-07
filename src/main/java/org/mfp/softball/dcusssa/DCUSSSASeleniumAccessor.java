package org.mfp.softball.dcusssa;

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

/**
 * Created by gawarho on 1/1/17.
 */
public class DCUSSSASeleniumAccessor extends BaseSelenium {
    /**
     *
     */
    private int sleep = 0;

    private String tournamentsUrl = "http://web.usssa.com";

    private boolean loggedIn = false;
    private String userId = "gawarho2";
    private String password = "MD=stars00";
    private String loginUrl = "http://dc.usssa.com/login";
    private String logoutUrl = "http://dc.usssa.com/login/logout.aspx";
    private String wtdInputName = "WTD";
    private String sportInputName = "Sport";
    private String wtdInputValue = "1";
    private String sportInputValue = "16";

    private String teamsUrl = "http://dc.usssa.com/#/team-manage-ng";

    private String mainMenuUrl = "http://web.usssa.com/sports/ISTSMenu.asp";

    private String teamManagerUrl = "http://web.usssa.com/sports/RosterManager.asp?WhatToDo=SelectionC";
    private String rosterManagerUrl = "http://web.usssa.com/sports/RosterManager.asp?WhatToDo=Begin";

    private String teamManagerReferer = "http://web.usssa.com/sports/RosterManager.asp?WhatToDo=Begin";
    private String host = "web.usssa.com";


    public DCUSSSASeleniumAccessor() {
        super();
    }

    public boolean login() {
        String retVal = "";

        if (!loggedIn) {
            driver.get(loginUrl);
            WebElement istsid = driver.findElement(By.name("txtUsername"));
            istsid.sendKeys(userId);
            WebElement pw = driver.findElement(By.name("txtPassword"));
            pw.sendKeys(password);
            Select sp = new Select(driver.findElement(By.name("lstSport")));
            sp.selectByValue("16");
            WebElement submit = driver.findElement(By.name("btnLogin"));
            submit.click();
            //System.out.println(driver.getCurrentUrl());
            loggedIn = true;
        }

        return loggedIn;
    }

    /**
     *
     */
    public boolean logout() {
        boolean retVal = true;

        if (loggedIn) {
            driver.get(logoutUrl);
            loggedIn = false;
        }

        return retVal;
    }

    public void getElements() {
        driver.get("http://dc.usssa.com/#/events?sportID=16");
        List<WebElement> wes = driver.findElements(By.tagName("button"));

        for(WebElement we : wes) {
            System.out.println(we.toString() + we.getText());
        }

        WebElement we = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/div/div[8]/button"));
        System.out.println(we.toString() + we.getText());

    }

    public TeamInfo getTeams(String state, String year, String age, String division) {
        TeamInfo ti = new TeamInfo();
        return getTeams(state, year, age, division, ti);
    }


    public TeamInfo getTeams(String state, String year, String age, String division, TeamInfo ti) {
        return getTeams(state, year, age, division, ti, false);
    }

    public TeamInfo getTeams(String state, String year, String age, String division, TeamInfo ti, boolean details) {
        TeamInfo ti2 = new TeamInfo();
        driver.get(teamsUrl);
        Sleep.sleep(5000);

        String stateCode = USSSAUtils.getStateToCode(state);
        String classCode = USSSAUtils.getAgeDivisionCode(age, division);
        String seasonCode = USSSAUtils.getYearToCode(year);

        logger.error("StateCode: " + stateCode + "  ClassCode: " + classCode + " seasonCode: " + seasonCode);

        try {
            WebElement season = driver.findElement(By.name("season-select"));
            Select seasonS = new Select(season);
            seasonS.selectByVisibleText(year);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        WebElement we = driver.findElement(By.name("state-select"));
        Select sts = new Select(we);
        sts.selectByVisibleText(state);

        Select cs = new Select(driver.findElement(By.id("classes")));
        cs.selectByValue(classCode);

        /*
         * click on View Teams
         */
        WebElement btn = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/div/div[5]/button"));
        btn.click();

        Sleep.sleep(5000);
        /*
         * click on 500 teams to get them all
         */
        //WebElement cBtn = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/div[2]/div/div/div/button[4]"));
        //System.out.println(cBtn.toString());
        //cBtn.click();
        //Sleep.sleep(5000);

        WebElement tblWe = driver.findElement(By.id("ng-table-teams"));
        WebElement tBody = tblWe.findElement(By.tagName("tbody"));
        List<WebElement> teams = tBody.findElements(By.tagName("tr"));
        int tdCount = teams.get(0).findElements(By.tagName("td")).size();
        for (WebElement tr : teams) {
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            if(tds.size() >= 20) {
                String id = tds.get(6).getText();
                String teamRegId = tds.get(17).getText();
                String tmpStr = tds.get(18).getText();
                String [] parts = tmpStr.split("\n");
                String coach = parts[0];
                String email = "";
                String telephone = "";
                if(parts.length >= 2) {
                    if(parts[1].contains("@")) {
                        email = parts[1];
                    } else {
                        telephone = parts[1];
                    }
                    if(parts.length > 2) {
                        telephone = parts[2];
                    }
                }

                String tournaments = tds.get(9).getText();
                String name = tds.get(8).getText();
                System.out.println(name);
                String teamClass = tds.get(7).getText();
                String stateA = tds.get(10).getText();
                USSSATeam t = new USSSATeam(teamRegId, name, teamClass, year, coach, null, stateA);
                t.setTelephone(telephone);
                t.setEmail(email);
                t.setIstsId(id);
                WebElement we3 = tds.get(20);
                WebElement we4 = we3.findElement(By.id("button-template-url"));
                we4.click();
                //*[@id="ng-table-teams"]/tbody/tr[1]/td[21]/div/ul/li[8]
                WebElement we5 = we3.findElement(By.xpath("./div/ul/li[8]"));
                we5.click();
                Sleep.sleep(1000);
                List<WebElement> wes = driver.findElements(By.tagName("a"));
                for(WebElement we9 : wes) {
                    String ng_click = we9.getAttribute("ng-click");
                    if(ng_click != null && ng_click.equalsIgnoreCase("cancel()")) {
                        System.out.println("Found: ");
                        we9.click();
                        break;
                    } else {
                        System.out.println(ng_click);
                    }
                }
                ti2.addEntry(t);
            }
        }


/*
        if (details) {
            for (Team t : ti2.getEntries()) {
                if (sleep > 0) {
                    Sleep.sleep(sleep);
                }
                USSSATeam t2 = (USSSATeam) t;
                String istsId = t2.getIstsId();
                driver.get(rosterManagerUrl);
                stateId2 = driver.findElement(By.name("StateID2"));
                opts = stateId2.findElements(By.tagName("option"));
                for (WebElement opt : opts) {
                    if (opt.getAttribute("value").equalsIgnoreCase(stateCode)) {
                        opt.click();
                        break;
                    }
                }
                classId2 = driver.findElement(By.name("ClassID2"));
                opts = classId2.findElements(By.tagName("option"));
                for (WebElement opt : opts) {
                    if (opt.getAttribute("value").equalsIgnoreCase(classCode)) {
                        opt.click();
                        break;
                    }
                }
                seasonId = driver.findElement(By.name("SeasonId"));
                opts = seasonId.findElements(By.tagName("option"));
                for (WebElement opt : opts) {
                    if (opt.getAttribute("value").equalsIgnoreCase(seasonCode)) {
                        opt.click();
                        break;
                    }
                }

                tmpElement = driver.findElement(By.name("Form1"));
                tmpElement.submit();

                teamId = driver.findElement(By.name("TeamID"));
                teams = teamId.findElements(By.tagName("option"));
                boolean found = false;
                for (WebElement team : teams) {
                    String id = team.getAttribute("value");
                    if (id.equalsIgnoreCase(istsId)) {
                        team.click();
                        WebElement we = driver.findElement(By.name("button1"));
                        we.click();
                        WebElement tmp = driver.findElement(By.name("Address"));
                        String address = tmp.getAttribute("value");
                        t2.setAddress(address);
                        tmp = driver.findElement(By.name("FirstName"));
                        String first = tmp.getAttribute("value");
                        tmp = driver.findElement(By.name("LastName"));
                        String last = tmp.getAttribute("value");
                        t2.setCoach(first + " " + last);
                        tmp = driver.findElement(By.name("Zip"));
                        String zip = tmp.getAttribute("value");
                        t2.setZip(zip);
                        tmp = driver.findElement(By.name("City"));
                        String city = tmp.getAttribute("value");
                        t2.setCity(city);

                        WebElement stateId = driver.findElement(By.name("State"));
                        Select select = new Select(stateId);
                        WebElement fSopt = select.getFirstSelectedOption();
                        t2.setCoachState(fSopt.getText());

                        tmp = driver.findElement(By.name("WorkPhone"));
                        String workPhone = tmp.getAttribute("value");
                        t2.setSecPhone(workPhone);
                        tmp = driver.findElement(By.name("Email1"));
                        String email1 = tmp.getAttribute("value");
                        t2.setEmail(email1);
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    logger.warn(t2.getName() + " not found");
                }

            }

        }
        */
        ti.addEntries(ti2);

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
        org.mfp.softball.dcusssa.DCUSSSASeleniumAccessor ua = new org.mfp.softball.dcusssa.DCUSSSASeleniumAccessor();
        ua.login();
        TeamInfo ti = ua.getTeams("Maryland", "2011", "16", "A", new TeamInfo());
        for(Team t : ti.getEntries()) {
            System.out.println(t.toCSVString());
        }

        //ua.close();
    }
}
