/**
 *
 */
package org.mfp.softball.softballtournaments;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.mfp.common.BaseSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Glen
 */
public class SoftballTournamentsSeleniumAccessor extends BaseSelenium {

    private int sleep = 0;
    private String whoAmI = "";

    private String tournamentsUrl = "http://web.usssa.com";

    private boolean loggedIn = false;

    private String userId = "glen.warholic@gmail.com";
    private String password = "md_stars";

    private String usssaEastUserId = "usssa.east@usssa.com";
    private String usssaEastPassword = "ravens";

    private String loginUrl = "http://softballtournaments.com/index?id=cpanel";
    private String logoutUrl = "http://softballtournaments.com/index?id=logout";
    private String addUrl = "http://softballtournaments.com/index?id=cpanel&com=add";
    private String host = "www.softballtournaments.com";

    private String baseComments = "This tournament is for age groups %s and has %s divisions of play.  " +
            "The tournament features %s game format.  " +
            "The cost of the tournament is %s.  " +
            "Features to note about the tournament include: " +
            "We have a professional and experienced full time staff available year round (24/7/365) to assist the needs of our teams. We provide a full range of tournament services.  " +
            "Our national website, www.usssa.com is the most state of the art, comprehensive and transparent of its kind of any association and any sport. " +
            "Our local website, www.usssa1.com has been totally revamped using today's latest technology.  " +
            "Our tournament schedule goes far beyond the normal weekend offerings. " +
            "We spend the time and effort necessary to provide a balanced and rewarding tournament experience for all teams.  " +
            "%s See this link for more information: http://www.usssa1.com/softball/team-info/why-play-usssa  -- " +
            "Please direct questions about this tournament to %s at %s." +
            "The tournament web site at www.usssa.com is %s.  " +
            "Lodging information to assist teams is HBC Event Services, " +
            "Ashlie Spock, " +
            "505-346-0522, " +
            "ashlies@hbceventservices.com, " +
            "http://usssa1hotels.com/2018-events";

    public SoftballTournamentsSeleniumAccessor() {
        super();
    }

    public boolean login(String email) {
        String retVal = "";

        if (email.equalsIgnoreCase(whoAmI)) {
            //System.out.println("Logging out " + whoAmI + " Logging in " + email);
            logout();
        }
        whoAmI = email;
        if (!loggedIn) {
            driver.get(loginUrl);
            WebElement selectlogin = driver.findElement(By.name("selectlogin"));
            selectlogin.sendKeys("trndir");
            WebElement login = driver.findElement(By.name("tournlogin"));
            if (whoAmI.equalsIgnoreCase("glen.warholic@usssa.com")) {
                login.sendKeys(userId);
            } else {
                login.sendKeys(usssaEastUserId);
            }

            WebElement pw = driver.findElement(By.name("adminpasswd"));
            if (whoAmI.equalsIgnoreCase("glen.warholic@usssa.com")) {
                pw.sendKeys(password);
            } else {
                pw.sendKeys(usssaEastPassword);
            }
            WebElement submit = driver.findElement(By.name("login"));
            submit.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    public boolean addTournament(TournamentConfig tc) {
        boolean retVal = false;

        //System.out.println(tc.toString());

        retVal = addTournament(tc.getName(), tc.getComplex(), tc.getCity(), tc.getState(),
                tc.getStartMonth(), tc.getStartDay(), tc.getEndMonth(), tc.getEndDay(), tc.getYear(),
                tc.getClassification(), tc.getAssociation(), tc.getComments(), tc.getPocEmail());

        return retVal;
    }

    public boolean addTournament(String name, String complex, String city, String state,
                                 int startMonth, int startDay, int endMonth, int endDay, int year,
                                 String classification, String association, String comment, String who) {
        boolean retVal = false;

        login(who);

        driver.get(addUrl);

        WebElement fName = driver.findElement(By.name("tname"));
        fName.sendKeys(name);

        WebElement fComplex = driver.findElement(By.name("complex"));
        fComplex.sendKeys(complex);

        WebElement fCity = driver.findElement(By.name("city"));
        fCity.sendKeys(city);
        Select sState = new Select(driver.findElement(By.name("state")));
        sState.selectByVisibleText(state);
        Select sCountry = new Select(driver.findElement(By.name("country")));
        sCountry.selectByVisibleText("United States");

        Select sStartMonth = new Select(driver.findElement(By.name("sdmo")));
        sStartMonth.selectByVisibleText(String.format("%02d", startMonth));
        Select sStartDay = new Select(driver.findElement(By.name("sdday")));
        sStartDay.selectByVisibleText(String.format("%02d", startDay));
        Select sEndMonth = new Select(driver.findElement(By.name("edmo")));
        sEndMonth.selectByVisibleText(String.format("%02d", endMonth));
        Select sEndDay = new Select(driver.findElement(By.name("edday")));
        sEndDay.selectByVisibleText(String.format("%02d", endDay));

        Select sYear = new Select(driver.findElement(By.name("year")));
        sYear.selectByVisibleText(String.valueOf(year));

        List<WebElement> fFastSlow = driver.findElements(By.name("slowfast"));
        fFastSlow.get(0).click();

        List<WebElement> fTypes = driver.findElements(By.name("wmcs[]"));
        for (WebElement we : fTypes) {
            String sValue = we.getAttribute("value");
            if (sValue.equalsIgnoreCase("Y")) {
                we.click();
            }
        }

        WebElement fClassification = driver.findElement(By.name("class"));
        fClassification.sendKeys(classification);

        WebElement fAssociation = driver.findElement(By.name("assoc"));
        fAssociation.sendKeys(association);

        WebElement fMoreInfo = driver.findElement(By.name("moreinfo"));
        fMoreInfo.sendKeys(comment);


        WebElement fSubmit = driver.findElement(By.name("Submit"));
        fSubmit.click();

        retVal = true;

        return retVal;
    }

    public void processFile(String filename) {
        File csvFile = new File(filename);
        List<TournamentConfig> ll = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(csvFile, Charset.defaultCharset(), CSVFormat.EXCEL);
            int lineNo = 0;
            for (CSVRecord csvRecord : parser) {
                lineNo++;
                String name = csvRecord.get(3).trim();
                String startDate = csvRecord.get(1).trim();
                Pattern p = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{2})");
                Matcher m = p.matcher(startDate);
                String startMonth = "";
                String startDay = "";
                String startYear = "";
                if (m.find()) {
                    startMonth = m.group(1);
                    startDay = m.group(2);
                    startYear = m.group(3);
                } else {
                    System.out.println("Bad Start Date " + startDate + " for Line " + lineNo);
                    continue;
                }
                String endDate = csvRecord.get(2).trim();
                m = p.matcher(endDate);
                String endMonth = "";
                String endDay = "";
                String endYear = "";
                if (m.find()) {
                    endMonth = m.group(1);
                    endDay = m.group(2);
                    endYear = m.group(3);
                } else {
                    System.out.println("Bad End Date " + endDate + " for Line " + lineNo);
                    continue;
                }
                String complex = csvRecord.get(6).trim();
                String city = csvRecord.get(7).trim();
                String state = csvRecord.get(8).trim();
                if (state.equalsIgnoreCase("MD")) {
                    state = "Maryland";
                } else if (state.equalsIgnoreCase("DE")) {
                    state = "Delaware";
                } else if (state.equalsIgnoreCase("VA")) {
                    state = "Virginia";
                }

                String games = csvRecord.get(9).trim();
                String cost = csvRecord.get(10).trim();
                String classifications = csvRecord.get(5).trim();
                String ages = csvRecord.get(4).trim();
                String comments = csvRecord.get(13).trim();
                String poc = csvRecord.get(11).trim();
                String pocEmail = csvRecord.get(12).trim();
                String webSite = csvRecord.get(14).trim();

                comments = String.format(baseComments, ages, classifications, games, cost, comments, poc, pocEmail, webSite);

                if (false) {
                    System.out.println("Name: " + name);
                    System.out.println("Complex: " + complex);
                    System.out.println("City: " + city);
                    System.out.println("State: " + state);
                    System.out.println("Start Month: " + startMonth);
                    System.out.println("Start Day: " + startDay);
                    System.out.println("End Month: " + endMonth);
                    System.out.println("End Day: " + endDay);
                    System.out.println("Classifications: " + classifications);
                    System.out.println("Comments: " + comments);
                }

                TournamentConfig tc = new TournamentConfig(name, complex, city, state,
                        Integer.parseInt(startMonth), Integer.parseInt(startDay), Integer.parseInt(endMonth), Integer.parseInt(endDay), 2018,
                        classifications, "USSSA", comments, pocEmail);
                ll.add(tc);
            }
            System.out.println("Tournaments loaded: " + ll.size());
            List<String> relevantUsers = new ArrayList<>();
            //relevantUsers.add("glen.warholic@usssa.com");
            relevantUsers.add("usssa.east@usssa.com");
            for(String u : relevantUsers) {
                for (TournamentConfig tc : ll) {
                    if (tc.getPocEmail().equalsIgnoreCase(u)) {
                        boolean status = addTournament(tc);
                        if (status != true) {
                            System.out.println("Tournament not posted: " + tc.getName() + " " + tc.getPocEmail() + " " + tc.getClassification());
                        } else {
                            System.out.println("Tournament posted: " + tc.getName() + " " + tc.getPocEmail() + " " + tc.getClassification());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: SoftballTournamentsSeleniumAccessor csvFile");
            System.exit(1);
        }
        SoftballTournamentsSeleniumAccessor ua = new SoftballTournamentsSeleniumAccessor();

        //ua.addTournament("Tournament 1", "Central Maryland Parks", "Central Maryland", "Delaware",
        //		4, 1, 4, 5, 2019,
        //		"Open,B,C", "USSSA", "Some Comments");
        //try {
        //	Thread.sleep(20000);
        //} catch (InterruptedException e) {
        //	e.printStackTrace();
        //}

        ua.processFile(args[0]);

        ua.close();
    }
}
