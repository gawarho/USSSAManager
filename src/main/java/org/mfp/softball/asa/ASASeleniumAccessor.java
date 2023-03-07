/**
 * 
 */
package org.mfp.softball.asa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.mfp.common.BaseSelenium;
import org.mfp.softball.asa.data.ASAUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Glen
 *
 */
public class ASASeleniumAccessor extends BaseSelenium {

	private String tournamentsUrl = "http://gfp.tournamentasa.com";
		
	private String tournamentUrl = "http://gfp.tournamentasa.com/i!/tournaments/tournamentDetails.php?TID=";
	private String [] cookies;
	//private HttpClient client = HttpClientBuilder.create().build();
	//private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36";

	//private WebDriver driver = null;
	
	//private Logger logger = null;
	
	/**
	 * 
	 */
	public ASASeleniumAccessor() {
		super();
	}
	
	public String getMainPage(String area) {
		String url = tournamentsUrl + "/" + ASAUtils.getAreaToCode(area.toUpperCase()) + "/";
		logger.debug(url);
		driver.get(url);
		goToLink("/i!/tournaments/tournamentsHome.php");
		WebElement h = driver.findElement(By.tagName("html"));
		String html = driver.getPageSource();
		logger.debug(html);

		return html;
	}
	
	public String getTournamentById(String id) {
		String url = tournamentUrl + id;
        driver.get(url);		
		String html = driver.getPageSource();
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
		ASASeleniumAccessor ua = new ASASeleniumAccessor();
		//String tHtml = ua.getMainPage("MD");
		String tHtml = ua.getTournamentById("4600");
		System.out.println(tHtml);
	}
}
