/**
 * 
 */
package org.mfp.softball.usssa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.mfp.softball.usssa.data.USSSAUtils;
import org.apache.logging.log4j.Logger;


/**
 * @author gawarho
 *
 */
public class USSSAWebAccessor {

	private boolean loggedIn = false;
	private String userId = "gawarho2";
	private String password = "mdstars";
	private String loginUrl = "http://web.usssa.com/sports/ISTSMenu.asp";
	private String loginInputName = "ISTSID";
	private String passwordInputName = "Password";
	private String submitInputName = "B1";
	private String submitInputValue = " ENTER ";
	private String logoutUrl = "http://web.usssa.com/sports/ISTSLogin.asp";
	private String wtdInputName = "WTD";
	private String sportInputName = "Sport";
	private String wtdInputValue = "1";
	private String sportInputValue = "16";

	private String protocol = "http";
	private String baseUrl = "web.usssa.com";
	private String teamsUrl = "/sports/Team2.asp";

	private String teamManagerFilterUrl = "http://web.usssa.com/sports/RosterManager.asp?WhatToDo=SelectionC";
	private String teamManagerMainUrl = "/sports/RosterManager.asp";
	
	private String tournamentListingUrl = "/sports/FindTourn2.asp";

	private String mainMenuUrl = "http://web.usssa.com/sports/ISTSMenu.asp";

	private String tournamentEntryUrl = "http://web.usssa.com/sports/ListingOfTeamsInTourn.asp";
	private String seasonIdInputName = "SeasonID";
	private String seasonIdInputValue = "19";
	private String a2InputName = "A2";
	private String a2InputValue = "1";
	private String monthIdInputName = "MonthID";
	private String monthIdInputValue = "0";
	private String lastClassInputName = "LastClass";
	private String lastClassInputValue = "0";
	private String statureIdInputName = "StatureID";
	private String statureIdInputValue = "0";
	private String stateIdInputName = "StateID";
	private String stateIdInputValue = "0";
	private String directorTournamentsInputName = "DirectorTournaments";

	//private String [] cookies;
	private Map<String, String> cookies = new HashMap<String, String>();
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36";

	private Logger logger = null;


	public USSSAWebAccessor() {
		logger = LogManager.getLogger(USSSAWebAccessor.class.getName());
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		//cookies = new String[0];
	}

	public boolean login() {
		String retVal = "";

		if(!loggedIn) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair(loginInputName, userId));
			paramList.add(new BasicNameValuePair(passwordInputName, password));
			paramList.add(new BasicNameValuePair(submitInputName, submitInputValue));
			retVal = sendPost(loginUrl, paramList, true);
			if(retVal.length() > 0) {
				logger.info("Logged in");
				loggedIn = true;
			}
		}

		return loggedIn;		
	}

	/**
	 * 
	 */
	public boolean logout() {
		boolean retVal = true;

		if(loggedIn) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair(wtdInputName, wtdInputValue));
			paramList.add(new BasicNameValuePair(sportInputName, sportInputValue));
			String htmlContents = sendPost(logoutUrl, paramList);
		}

		return retVal;
	}


	public String getTournament(int id) {
		String str = null;

		if(loggedIn) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair(a2InputName, a2InputValue));
			paramList.add(new BasicNameValuePair(seasonIdInputName, seasonIdInputValue));
			paramList.add(new BasicNameValuePair(monthIdInputName, monthIdInputValue));
			paramList.add(new BasicNameValuePair(lastClassInputName, lastClassInputValue));
			paramList.add(new BasicNameValuePair(statureIdInputName, statureIdInputValue));
			paramList.add(new BasicNameValuePair(stateIdInputName, stateIdInputValue));
			paramList.add(new BasicNameValuePair(directorTournamentsInputName, new Integer(id).toString()));
			str = sendPost(tournamentEntryUrl, paramList);
		} else {
			logger.warn("You are not logged in!");
		}

		return str;
	}

	public String getTeams(String state, String year, String age, String division) {
		String html = null;

		if(loggedIn) {
			String stateCode = USSSAUtils.getStateToCode(state);
			String classCode = USSSAUtils.getAgeDivisionCode(age, division);
			String seasonCode = USSSAUtils.getYearToCode(year);

			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("State", stateCode));
			paramList.add(new BasicNameValuePair("ClassID", classCode));
			paramList.add(new BasicNameValuePair("SeasonId", seasonCode));
			//paramList.add(new BasicNameValuePair(sportInputName, sportInputValue));
			html = sendGet(teamsUrl, paramList, false);
		} else {
			logger.warn("You are not logged in!");
		}

		return html;
	}


	public String getTournamentInfo(String state, String year, String age, String division) {
		String html = null;

		if(loggedIn) {
			String stateCode = USSSAUtils.getStateToCode(state);
			String classCode = USSSAUtils.getAgeDivisionCode(age, division);
			String seasonCode = USSSAUtils.getYearToCode(year);
			String lYear = new Integer(Integer.parseInt(year) - 1).toString();

			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("optSelection", "1"));
			paramList.add(new BasicNameValuePair("WTD", "Tournament"));
			paramList.add(new BasicNameValuePair("ParkID", "0"));
			paramList.add(new BasicNameValuePair("CID", classCode));
			paramList.add(new BasicNameValuePair("State", stateCode));
			paramList.add(new BasicNameValuePair("StatureID", "0"));
			paramList.add(new BasicNameValuePair("TypeID", "0"));
			paramList.add(new BasicNameValuePair("SeasonID", seasonCode));
			paramList.add(new BasicNameValuePair("BeginDate", "8/1/" + lYear));
			paramList.add(new BasicNameValuePair("EndDate", ""));
			//paramList.add(new BasicNameValuePair(sportInputName, sportInputValue));
			html = sendGet(tournamentListingUrl, paramList, false);
		} else {
			logger.warn("You are not logged in!");
		}

		return html;
	}


	public String getTeamsFromTeamManager(String state, String year, String age, String division) {
		String html = null;

		if(loggedIn) {
			String stateCode = USSSAUtils.getStateToCode(state);
			String classCode = USSSAUtils.getAgeDivisionCode(age, division);
			String seasonCode = USSSAUtils.getYearToCode(year);

			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("WhatToDo", "Begin"));
			html = sendGet(teamManagerMainUrl, paramList, true);
			paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("SeasonId", seasonCode));
			paramList.add(new BasicNameValuePair("StateID2", stateCode));
			paramList.add(new BasicNameValuePair("ClassID2", classCode));
			// canned parameters from here down
			paramList.add(new BasicNameValuePair("FirstCharacter", "1"));
			paramList.add(new BasicNameValuePair("DisplayType", "1"));
			paramList.add(new BasicNameValuePair("StateForGlobalReg", "55"));
			paramList.add(new BasicNameValuePair("StateForReg", "55"));
			paramList.add(new BasicNameValuePair("Registration", ""));
			paramList.add(new BasicNameValuePair("FreezeDate", ""));
			paramList.add(new BasicNameValuePair("ClassID", "1000"));
			paramList.add(new BasicNameValuePair("ReclassDate", ""));
			paramList.add(new BasicNameValuePair("EndYearClassID", "1000"));
			paramList.add(new BasicNameValuePair("ChangeTeamName", ""));
			paramList.add(new BasicNameValuePair(sportInputName, sportInputValue));
			html = sendPost(teamManagerFilterUrl, paramList, true);
		} else {
			logger.warn("You are not logged in!");
		}

		return html;
	}


	private String sendPost(String url, List<NameValuePair> postParams) {
		return sendPost(url, postParams, false);
	}

	private String sendGet(String pageUrl, List<NameValuePair> getParams, boolean loginAttempt) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme(protocol).setHost(baseUrl).setPath(pageUrl);
		Iterator<NameValuePair> it = getParams.iterator();
		while(it.hasNext()) {
			NameValuePair nvp = it.next();
			String name = nvp.getName();
			String value = nvp.getValue();
			builder.addParameter(name, value);
		}
		URI url = null;
		try {
			url = builder.build();
		} catch (Exception e){
			logger.error(e);
			return null;
		}
		HttpGet get = new HttpGet(url);
		// add header
		get.setHeader("Host", "web.usssa.com");
		get.setHeader("User-Agent", USER_AGENT);
		get.setHeader("Accept", 
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-US,en;q=0.8");
		Iterator<Map.Entry<String, String>> it2 = cookies.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry pair = it2.next();
			String c = (String)pair.getKey();
			logger.trace("Cookie: " + c);
			get.addHeader("Cookie", c);
		}
		get.setHeader("Connection", "keep-alive");
		get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		get.setHeader("Referer", loginUrl);
		if(logger.isTraceEnabled()) {
			logger.trace("\nGet headers: ");
			Header [] headers = get.getAllHeaders();
			for(Header h : headers) {
				logger.trace(h.getName() + " " + h.getValue());
			}
		}
		StringBuffer result = new StringBuffer();
		try {
			HttpResponse response = client.execute(get);

			int responseCode = response.getStatusLine().getStatusCode();

			logger.trace("Sending 'Get' request to URL : " + url);
			logger.trace("Get parameters : " + getParams);
			logger.trace("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			if(logger.isTraceEnabled()) {
				Header [] headers = response.getAllHeaders();
				logger.trace("\nResponse headers:");
				for(Header h : headers) {
					logger.trace(h.getName() + " " + h.getValue());
				}
			}
			if(loginAttempt) {
				Header [] headers = response.getHeaders("Set-Cookie");
				for(Header h : headers) {
					if(cookies.containsKey(h.getValue()) == false) {
						logger.trace("New Cookie: " + h.getValue());
					}
					cookies.put(h.getValue(), "1");
				}
			}
		} catch(Exception e) {
			logger.error("Exception when sending get command " + url + " " + e);
			e.printStackTrace();
			result = new StringBuffer();
		}

		return result.toString();
	}

	private String sendPost(String url, List<NameValuePair> postParams, boolean loginAttempt) {
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", "web.usssa.com");
		post.setHeader("Origin", "http://web.usssa.com");
		post.setHeader("Referer", "http://web.usssa.com/sports/RosterManager.asp?WhatToDo=Begin");
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", 
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Cache-Control", "max-age=0");
		Iterator<Map.Entry<String, String>> it2 = cookies.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry pair = it2.next();
			String c = (String)pair.getKey();
			logger.trace("Cookie: " + c);
			post.addHeader("Cookie", c);
		}
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		//post.setHeader("Referer", loginUrl);
		if(logger.isTraceEnabled()) {
			logger.trace("\nPost headers: ");
			Header [] headers = post.getAllHeaders();
			for(Header h : headers) {
				logger.trace(h.getName() + ": " + h.getValue());
			}
		}
		StringBuffer result = new StringBuffer();
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));

			HttpResponse response = client.execute(post);

			int responseCode = response.getStatusLine().getStatusCode();

			logger.trace("Sending 'POST' request to URL : " + url);
			logger.trace("Post parameters : " + postParams);
			logger.trace("Response Code : " + responseCode);

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			if(logger.isTraceEnabled()) {
				Header [] headers = response.getAllHeaders();
				logger.trace("\nResponse headers:");
				for(Header h : headers) {
					logger.trace(h.getName() + ": " + h.getValue());
				}
			}
			if(loginAttempt) {
				Header [] headers = response.getHeaders("Set-Cookie");
				for(Header h : headers) {
					if(cookies.containsKey(h.getValue()) == false) {
						logger.trace("New Cookie: " + h.getValue());
					}
					cookies.put(h.getValue(), "1");
				}
			}
		} catch(Exception e) {
			logger.error("Exception when sending post command " + url + " " + e);
			e.printStackTrace();
			result = new StringBuffer();
		}

		return result.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USSSAWebAccessor ua = new USSSAWebAccessor();
		ua.login();
		//String tHtml = ua.getTournament(1173891);
		String tHtml = ua.getTournamentInfo("MD", "2015", "18", "O");
		System.out.println(tHtml);
		ua.logout();
	}

}
