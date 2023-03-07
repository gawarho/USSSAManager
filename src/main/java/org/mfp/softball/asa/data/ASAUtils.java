/**
 * 
 */
package org.mfp.softball.asa.data;

import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Glen
 *
 */
public class ASAUtils {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(ASAUtils.class.getName());
	private static final TreeMap<String, String> areaToCodeMap = prepareMap1();
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
	public static String getAreaToCode(String state) {
		return areaToCodeMap.get(state);
	}
}
