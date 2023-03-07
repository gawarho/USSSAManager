/**
 * 
 */
package org.mfp.softball.asa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.usssa.USSSAWebAccessor;

/**
 * @author Glen
 *
 */
public class ASAWebAccessor {

	private static final TreeMap<String, String> areaToCodeMap = prepareMap1();
	private String tournamentsUrl = "http://gfp.tournamentasa.com";
		
	private String tournamentUrl = "http://gfp.tournamentasa.com/i!/tournaments/tournamentDetails.php";
	private String [] cookies;
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36";

	private Logger logger = null;
	private static TreeMap<String, String> prepareMap1() {
		TreeMap<String, String> aMap = new TreeMap<String, String>();
		
		aMap.put("NJ", "8");
		aMap.put("NY", "9");
		aMap.put("DE", "13");
		aMap.put("MD", "15");
		aMap.put("PA", "16");
		aMap.put("VA", "17");
		aMap.put("WV", "52");

		return aMap;
	}

	
	/**
	 * 
	 */
	public ASAWebAccessor() {
		logger = LogManager.getLogger(USSSAWebAccessor.class.getName());
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
        cookies = new String[0];
	}
	
	public String getMainPage(String area) {
		String url = tournamentsUrl + "/" + areaToCodeMap.get(area.toUpperCase()) + "/";
		logger.debug(url);
		String html = sendPost(url, null, true);
		url = tournamentsUrl + "/i!/tournaments/tournamentsHome.php";
		logger.debug(url);
		sendPost(url, null, true);
		
		return html;
	}
	
	public String getTournamentById(String id) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("TID", id));
		String html = sendPost(tournamentUrl, paramList, false);
		
		return html;
	}
	private String sendPost(String url, List<NameValuePair> postParams, boolean loginAttempt) {
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", "gfp.tournamentasa.com");
		post.setHeader("Referer", "http://gfp.tournamentasa.com/15/");
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", 
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		for(String c : cookies) {
		  post.addHeader("Cookie", c);
		}
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		if(logger.isTraceEnabled()) {
			logger.trace("\nPost headers: ");
			Header [] headers = post.getAllHeaders();
			for(Header h : headers) {
				logger.trace(h.getName() + " " + h.getValue());
			}
		}
		StringBuffer result = new StringBuffer();
		try {
			if(postParams != null) {
			  post.setEntity(new UrlEncodedFormEntity(postParams));
			}
			
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
					logger.trace(h.getName() + " " + h.getValue());
				}
			}
			if(loginAttempt) {
				Header [] headers = response.getHeaders("Set-Cookie");
				cookies = new String[headers.length];
				int i = 0;
				for(Header h : headers) {
					logger.debug(h.getValue());
					cookies[i++] = h.getValue();
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
		ASAWebAccessor ua = new ASAWebAccessor();
		//String tHtml = ua.getMainPage("MD");
		String tHtml = ua.getTournamentById("4600");
		System.out.println(tHtml);
	}
}
