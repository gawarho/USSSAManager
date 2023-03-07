/**
 * 
 */
package org.mfp.softball.pony;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

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
public class PonyWebAccessor {

	private String tournamentsUrl = "http://www.youthleaguesusa.com/pony/2015/cgi-bin/webtournament.cgi";
	private String tournamentUrl = "http://www.youthleaguesusa.com/pony/2015/cgi-bin/tournamentprofile.cgi";
	private String [] cookies;
	private HttpClient client = HttpClientBuilder.create().build();
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36";

	private Logger logger = null;

	
	/**
	 * 
	 */
	public PonyWebAccessor() {
		logger = LogManager.getLogger(USSSAWebAccessor.class.getName());
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
        cookies = new String[0];
	}
	
	public String getMainPage() {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SPORTTYPE", "Fast Pitch Softball"));
		paramList.add(new BasicNameValuePair("ZONETYPE", "East"));
		String html = sendPost(tournamentsUrl, paramList, false);
		
		return html;
	}
	
	public String getTournamentById(String id) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("PSELECT", id));
		String html = sendPost(tournamentUrl, paramList, false);
		
		return html;
	}
	private String sendPost(String url, List<NameValuePair> postParams, boolean loginAttempt) {
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", "www.pony.org");
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
		PonyWebAccessor ua = new PonyWebAccessor();
		String tHtml = ua.getMainPage();
		//String tHtml = ua.getTournamentById("50002");
		System.out.println(tHtml);
	}

}
