/**
 * 
 */
package org.mfp.common;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author Glen
 *
 */
public class BaseSelenium {
	private Map<String, String> cookies = new HashMap<String, String>();
	protected HttpClient client = HttpClientBuilder.create().build();
	protected final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36";

	protected WebDriver driver = null;

	protected Logger logger = null;

	/**
	 * 
	 */
	public BaseSelenium() {
		logger = LogManager.getLogger(BaseSelenium.class.getName());
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
		System.setProperty("webdriver.chrome.driver", "/home/gawarho/bin/chromedriver");
        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1400,1200));
	}
	
	public void close() {
		driver.close();
	}
	
	
	protected boolean goToLink(String href) {
		boolean retVal = false;
		
		List<WebElement> anchors = driver.findElements(By.tagName("a"));
		Iterator<WebElement> i = anchors.iterator();
		while(i.hasNext()) {
			WebElement anchor = i.next();
			if(anchor.getAttribute("href").contains(href)) {
				logger.debug("Clicking on " + href);
				anchor.click();
				retVal = true;
				break;
			}
			
		}		

		return retVal;
	}
	
	protected String sendPost(String url, String host, String referer, List<NameValuePair> postParams, boolean loginAttempt) {
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Host", host);
		post.setHeader("Referer", referer);
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", 
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		Iterator<Map.Entry<String, String>> it2 = cookies.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry pair = it2.next();
			String c = (String)pair.getKey();
			logger.trace("Cookie: " + c);
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

	public WebDriver getDriver() {
		return driver;
	}
}
