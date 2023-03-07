/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Glen
 *
 */
public class USSSAUtils {
	private static Logger logger = LogManager.getLogger(USSSAUtils.class.getName());

	private static final TreeMap<String, Integer> yearToCodeMap = prepareMap1();
	private static final TreeMap<String, Integer> stateToCodeMap = prepareMap2();
	private static final TreeMap<String, Integer> classToCodeMap = prepareMap3();
	private static final TreeMap<String, Integer> relevantClassToCodeMap = prepareMap4();

	private static final Pattern ageDivisionPat = Pattern.compile("(\\d+)&*U(.*)");

	private static TreeMap<String, Integer> prepareMap1() {
		TreeMap<String, Integer> aMap = new TreeMap<String, Integer>();

		aMap.put("2023", 27);
		aMap.put("2022", 26);
		aMap.put("2021", 25);
		aMap.put("2020", 24);
		aMap.put("2019", 23);
		aMap.put("2018", 22);
		aMap.put("2017", 21);
		aMap.put("2016", 20);
		aMap.put("2015", 19);
		aMap.put("2014", 18);
		aMap.put("2013", 17);
		aMap.put("2012", 16);
		aMap.put("2011", 15);
		aMap.put("2010", 14);
		aMap.put("2009", 13);
		aMap.put("2008", 12);
		aMap.put("2007", 11);
		aMap.put("2006", 10);
		aMap.put("2005", 9);
		aMap.put("2004", 8);
		aMap.put("2003", 7);
		aMap.put("2002", 6);
		aMap.put("2001", 5);
		aMap.put("2000", 4);

		return aMap;
	}

	public static Map<String, String> yearToConstant;

	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("2011", "15");
		aMap.put("2010", "14");
		aMap.put("2009", "13");
		aMap.put("2008", "12");
		aMap.put("2007", "11");
		aMap.put("2006", "10");
		aMap.put("2005", "9");
		aMap.put("2004", "8");
		aMap.put("2003", "7");
		aMap.put("2002", "6");
		aMap.put("2001", "5");
		aMap.put("2000", "4");
		aMap.put("2012", "16");
		aMap.put("2013", "17");
		aMap.put("2014", "18");
		aMap.put("2015", "19");
		aMap.put("2016", "20");
		aMap.put("2017", "21");
		aMap.put("2018", "22");
		aMap.put("2019", "23");
		aMap.put("2020", "24");
		aMap.put("2021", "25");
		aMap.put("2022", "26");
		aMap.put("2023", "27");

		yearToConstant = Collections.unmodifiableMap(aMap);
	}

	private static TreeMap<String, Integer> prepareMap4() {
		TreeMap<String, Integer> aMap = new TreeMap<String, Integer>();

		aMap.put("18O", 1081);
		aMap.put("18A", 1015);
		aMap.put("18B", 1016);
		aMap.put("18C", 1051);
		aMap.put("18AS", 1058);
		aMap.put("16O", 1083);
		aMap.put("16A", 1020);
		aMap.put("16B", 1021);
		aMap.put("16C", 1052);
		aMap.put("16AS", 1059);
		aMap.put("15O", 1084);
		aMap.put("15C", 1085);
		aMap.put("15AS", 1060);
		aMap.put("14O", 1093);
		aMap.put("14A", 1025);
		aMap.put("14B", 1026);
		aMap.put("14C", 1054);
		aMap.put("14AS", 1061);
		aMap.put("13O", 1086);
		aMap.put("13C", 1087);
		aMap.put("13AS", 1062);
		aMap.put("12O", 1094);
		aMap.put("12A", 1030);
		aMap.put("12B", 1031);
		aMap.put("12C", 1056);
		aMap.put("12AS", 1063);
		aMap.put("11O", 1088);
		aMap.put("11C", 1092);
		aMap.put("11AS", 1064);
		aMap.put("10O", 1082);
		aMap.put("10A", 1089);
		aMap.put("10B", 1096);
		aMap.put("10C", 1095);
		aMap.put("10AS", 1065);
		aMap.put("9O", 1090);
		aMap.put("9C", 1071);
		aMap.put("9AS", 1066);
		aMap.put("9CP", 1072);
		aMap.put("8O", 1090);
		aMap.put("8C", 1071);
		aMap.put("8AS", 1066);
		aMap.put("8CP", 1072);

		return aMap;
	}

	private static TreeMap<String, Integer> prepareMap2() {
		TreeMap<String, Integer> aMap2 = new TreeMap<String, Integer>();

		aMap2.put("Alabama", 63);
		aMap2.put("Alaska", 92);
		aMap2.put("Arizona", 86);
		aMap2.put("Arkansas", 71);
		aMap2.put("British Columbia", 17);
		aMap2.put("California - Northern", 94);
		aMap2.put("California - Southern", 33);
		aMap2.put("Caribbean", 7);
		aMap2.put("Colorado", 84);
		aMap2.put("Connecticut", 6);
		aMap2.put("CT", 6);
		aMap2.put("Delaware", 51);
		aMap2.put("DE", 51);
		aMap2.put("District of Columbia", 9);
		aMap2.put("Florida - North", 67);
		aMap2.put("Florida - South", 68);
		aMap2.put("Florida-Pan", 69);
		aMap2.put("Georgia", 58);
		aMap2.put("Hawaii", 99);
		aMap2.put("Idaho", 82);
		aMap2.put("Illinois", 36);
		aMap2.put("Indiana", 35);
		aMap2.put("Iowa", 42);
		aMap2.put("Kansas", 48);
		aMap2.put("Kentucky-North", 53);
		aMap2.put("Kentucky-South", 57);
		aMap2.put("Louisiana", 72);
		aMap2.put("Maine", 1);
		aMap2.put("ME", 1);
		aMap2.put("Manitoba", 16);
		aMap2.put("Maryland", 52);
		aMap2.put("MD", 52);
		aMap2.put("Massachusetts", 4);
		aMap2.put("MA", 4);
		aMap2.put("Michigan", 38);
		aMap2.put("Minnesota", 41);
		aMap2.put("Mississippi", 64);
		aMap2.put("Missouri", 43);
		aMap2.put("Montana", 81);
		aMap2.put("Nebraska", 47);
		aMap2.put("Nevada", 88);
		aMap2.put("New Hampshire", 2);
		aMap2.put("NH", 2);
		aMap2.put("New Jersey", 22);
		aMap2.put("NJ", 22);
		aMap2.put("New Mexico", 85);
		aMap2.put("New York - Downstate", 11);
		aMap2.put("NYD", 11);
		aMap2.put("New York - Upstate", 21);
		aMap2.put("NYU", 21);
		aMap2.put("New York", 28);
		aMap2.put("NY", 28);
		aMap2.put("North Carolina", 56);
		aMap2.put("North Dakota", 45);
		aMap2.put("Ohio - North", 32);
		aMap2.put("Ohio - South", 37);
		aMap2.put("Oklahoma", 73);
		aMap2.put("Ontario", 12);
		aMap2.put("Oregon", 93);
		aMap2.put("Other", 10);
		aMap2.put("Pennsylvania", 25);
		aMap2.put("PA", 25);
		aMap2.put("Pennsylvania - Central", 24);
		aMap2.put("PAC", 24);
		aMap2.put("Pennsylvania - East", 25);
		aMap2.put("PAE", 25);
		aMap2.put("Pennsylvania - West", 26);
		aMap2.put("PAW", 26);
		aMap2.put("Quebec", 13);
		aMap2.put("Rhode Island", 5);
		aMap2.put("RI", 5);
		aMap2.put("South Carolina", 50);
		aMap2.put("South Dakota", 46);
		aMap2.put("Tennessee - East", 66);
		aMap2.put("Tennessee - West", 65);
		aMap2.put("Tennessee-Central", 70);
		aMap2.put("Texas - North", 77);
		aMap2.put("Texas - South", 74);
		aMap2.put("Texas - West", 76);
		aMap2.put("Utah", 87);
		aMap2.put("Vermont", 3);
		aMap2.put("VT", 3);
		aMap2.put("Virginia", 54);
		aMap2.put("VA", 54);
		aMap2.put("Washington", 91);
		aMap2.put("West Virginia", 55);
		aMap2.put("WV", 55);
		aMap2.put("Wisconsin", 39);
		aMap2.put("Wyoming", 83);

		return aMap2;
	}

	private static TreeMap<String, Integer> prepareMap3() {
		TreeMap<String, Integer> aMap = new TreeMap<String, Integer>();

		aMap.put("Fast-Pitch Girls 18 & Under A", 1015);
		aMap.put("Fast-Pitch Girls 18 & Under B", 1016);
		aMap.put("Fast-Pitch Girls 18 & Under C", 1051);
		aMap.put("Fast-Pitch Girls 18 & Under Rec / All-Stars", 1058);
		aMap.put("Fast-Pitch Girls 16 & Under A", 1020);
		aMap.put("Fast-Pitch Girls 16 & Under B", 1021);
		aMap.put("Fast-Pitch Girls 16 & Under C", 1052);
		aMap.put("Fast-Pitch Girls 16 & Under Rec / All-Stars", 1059);
		aMap.put("Fast-Pitch Girls 15 & Under", 1084);
		aMap.put("Fast-Pitch Girls 15 & Under C", 1085);
		aMap.put("Fast-Pitch Girls 15 & Under Rec / All-Stars", 1060);
		aMap.put("Fast-Pitch Girls 14 & Under A", 1025);
		aMap.put("Fast-Pitch Girls 14 & Under B", 1026);
		aMap.put("Fast-Pitch Girls 14 & Under C", 1054);
		aMap.put("Fast-Pitch Girls 14 & Under Rec / All-Stars", 1061);
		aMap.put("Fast-Pitch Girls 13 & Under", 1086);
		aMap.put("Fast-Pitch Girls 13 & Under C", 1087);
		aMap.put("Fast-Pitch Girls 13 & Under Rec / All-Stars", 1062);
		aMap.put("Fast-Pitch Girls 12 & Under A", 1030);
		aMap.put("Fast-Pitch Girls 12 & Under B", 1031);
		aMap.put("Fast-Pitch Girls 12 & Under C", 1056);
		aMap.put("Fast-Pitch Girls 12 & Under Rec / All-Stars", 1063);
		aMap.put("Fast-Pitch Girls 11 & Under", 1088);
		aMap.put("Fast-Pitch Girls 11 & Under C", 1092);
		aMap.put("Fast-Pitch Girls 11 & Under Rec / All-Stars", 1064);
		aMap.put("Fast-Pitch Girls 10 & Under A", 1089);
		aMap.put("Fast-Pitch Girls 10 & Under B", 1096);
		aMap.put("Fast-Pitch Girls 10 & Under C", 1095);
		aMap.put("Fast-Pitch Girls 10 & Under Rec / All-Stars", 1065);
		aMap.put("Fast-Pitch Girls 10 & U Coach/Machine Pitch", 1070);
		aMap.put("Fast-Pitch Girls 9 & Under", 1090);
		aMap.put("Fast-Pitch Girls 9 & Under Rec / All-Stars", 1066);
		aMap.put("Fast-Pitch Girls 9 & U Coach/Machine Pitch", 1072);
		aMap.put("Fast-Pitch Girls 8 & Under A", 1091);
		aMap.put("Fast-Pitch Girls 8 & Under B", 1098);
		aMap.put("Fast-Pitch Girls 8 & Under Rec / All-Stars", 1067);
		aMap.put("Fast-Pitch Girls 8 & Under Coach Pitch Open", 1074);
		aMap.put("Fast-Pitch Girls 8 & Under Coach Pitch B", 1101);
		aMap.put("Fast-Pitch Girls 8 & Under Machine Pitch Open", 1102);
		aMap.put("Fast-Pitch Girls 8 & Under Machine Pitch B", 1103);
		aMap.put("Fast-Pitch Girls 7 & Under Coach Pitch", 1042);
		aMap.put("Fast-Pitch Girls 7 & Under Machine Pitch", 1076);
		aMap.put("Fast-Pitch Girls 7 & Under Rec / All-Stars", 1068);
		aMap.put("Fast-Pitch Girls 6 & Under", 1043);
		aMap.put("Fast-Pitch Girls 6 & Under Rec / All-Stars", 1069);
		aMap.put("Fast-Pitch Girls 6 & U Coach/Machine/T-Ball", 1078);
		aMap.put("Fast-Pitch Girls 5 & U Coach/Machine/T-Ball", 1080);
		aMap.put("Fast-Pitch Girls 18 & U Arena 6vs6", 1100);
		aMap.put("Fast-Pitch Girls 16 & U Arena 6vs6", 1104);
		aMap.put("Fast-Pitch Girls 14 & U Arena 6vs6", 1108);
		aMap.put("Fast-Pitch Girls 12 & U Arena 6vs6", 1112);
		aMap.put("Fast-Pitch Girls 10 & U Arena 6vs6", 1116);
		aMap.put("Fast-Pitch Girls 8 & U Arena 6vs6", 1120);
		aMap.put("Fast-Pitch Girls 6 & U Arena 6vs6", 1124);

		return aMap;
	}

	public String getYearConstant(String year) {
		String yConstant = yearToConstant.get(year);
		if(yConstant == null) {
			int yInt = Integer.parseInt(year);
			if(yInt < 2000 || yInt >= 3000) {
				logger.error("Year invalid");
				yInt = 4; // set to 2000
			} else {
				yInt = yInt - 2020 + 24;
			}
			yConstant = new Integer(yInt).toString();
		}

		return yConstant;
	}

	public static int getAgeFromClassification(String str) {
		Map<String, String> m = getAgeAndDivisionFromClassification(str);
		return Integer.parseInt(m.get("Age"));
	}

	public static String getDivisionFromClassification(String str) {
		Map<String, String> m = getAgeAndDivisionFromClassification(str);
		return m.get("Division");
	}

	public static Map<String, String> getAgeAndDivisionFromClassification(String str) {
		String strSave = str;
		Map<String, String> retVal = new HashMap<String, String>();
		String ageStr = "";
		String divisionStr = "";
		int index = str.indexOf("FPGirls");
		//System.out.println("Index: " + index);
		if(index > -1) {
			str = str.replace("FPGirls", "");
			index = str.indexOf("&");
			if(index > -1) {
				ageStr = str.substring(0, index);
				//System.out.println("age: " + ageStr);
				str = str.substring(index+1);
				if(str.startsWith("U")) {
					str = str.substring(1);
					//System.out.println("division " + str);
					if(str.startsWith("AllSt")) {
						str = "AS";
					} else {
						str = str.substring(1);
					}
				}
				divisionStr = str;
			} else {
				index = str.indexOf("U");
				if(index > -1) {
					ageStr = str.substring(0, index);
					str = str.substring(index+1);
					if(str.startsWith("AllSt")) {
						str = "AS";
					}
				}
				divisionStr = str; 
			}
		}

		retVal.put("Age",  ageStr);
		retVal.put("Division",  divisionStr);
		logger.trace("Classification: " + strSave + "  Age: " + ageStr + "  Division: " + divisionStr);

		return retVal;
	}

	public static String getYearToCode(String year) {
		Integer retVal = yearToCodeMap.get(year);
		if(retVal == null) {
			logger.error("Year: " + year + " does not map to valid code");
			return null;			
		}
		return retVal.toString();
	}

	public static String getStateToCode(String state) {
		Integer retVal = stateToCodeMap.get(state);
		if(retVal == null) {
			logger.error("State: " + state + " does not map to valid code");
			return null;			
		}
		return retVal.toString();
	}

	public static Integer getClassToCode(String dClass) {
		return classToCodeMap.get(dClass);
	}

	public static String getAgeDivisionCode(String age, String division) {
		String dClass = age + division;
		Integer retVal = relevantClassToCodeMap.get(dClass);
		//System.out.println("dClass: " + dClass + "  Code: " + retVal);
		if(retVal == null) {
			logger.error("Age: " + age + "  Division: " + division + " does not map to valid code");
			return null;
		}
		return retVal.toString();
	}
	public static Integer getRelevantClassToCode(String dClass) {
		return relevantClassToCodeMap.get(dClass);
	}

	public static Map<String, Integer> getYeartocodemap() {
		return yearToCodeMap;
	}

	public static Map<String, Integer> getStatetocodemap() {
		return stateToCodeMap;
	}

	public static Map<String, Integer> getClasstocodemap() {
		return classToCodeMap;
	}

	public static Map<String, Integer> getRelevantClasstocodemap() {
		return relevantClassToCodeMap;
	}

	public static int getAgeFromTournamentDivision(String ad) {
		int retVal = 0;
		Map<String, String> m = parseTournamentDivision(ad);
		if(m.containsKey("Age")) {
			retVal = Integer.parseInt(m.get("Age"));
		}
		return retVal;		
	}

	public static String getDivisionFromTournamentDivision(String ad) {
		String retVal = null;
		Map<String, String> m = parseTournamentDivision(ad);
		retVal = m.get("Division");
		return retVal;
	}

	public static void parseAndListTournamentDivision(String ad) {
		Map<String, String> m = parseTournamentDivision(ad);
		System.out.println(m);
		return;
	}
	public static Map<String, String> parseTournamentDivision(String ad) {
		Map<String, String> m = new HashMap<String, String>();
		ad = ad.toUpperCase();
		//System.out.println("Parse: " + ad);
		Matcher m2 = ageDivisionPat.matcher(ad);
		String type = "FP";
		String gender = "Girls";
		String age = "";
		String division = "";
		if(m2.find()) {
			age = m2.group(1);
			division = m2.group(2);
		}

		//System.out.println(gender + " " + type + " " + age + " " + division);
		m.put("Type", type);
		m.put("Gender", gender);
		m.put("Age", age);
		m.put("Division", division);

		return m;
	}

	public static Map<String, String> parseTournamentDivision2(String ad) {
		Map<String, String> m = new HashMap<String, String>();
		ad = ad.toUpperCase();
		String type = ad.substring(0, 2);
		//System.out.println("Type: " + type);
		String gender = ad.substring(2, 7);
		//System.out.println("Gender: " + gender);
		ad = ad.substring(7);
		//System.out.println("Ad: " + ad);
		int indx = 0;
		String age = "0";
		if(( indx = ad.indexOf("&")) > -1) {
			age = ad.substring(0, indx);
			ad = ad.substring(indx+2);
		} else {
			indx = ad.indexOf("U");
			age = ad.substring(0,  indx);
			ad = ad.substring(indx+1);
		}
		//System.out.println("Ad: " + ad);
		String division = null;
		if(ad.length() > 0) {
			if(ad.toLowerCase().startsWith("allst")) {
				division = "AS";
			} else {
				division = ad.substring(0,1);
			}
		} else {
			division = "";
		}

		m.put("Type", type);
		m.put("Gender", gender);
		m.put("Age", age);
		m.put("Division", division);

		return m;
	}


	public static void main(String[] args) {
		String [] divisions = { "FPGirls13U", "FPGirls14UOpen", "FPGirls14&UB", "FPGirls18&UC", 
				"FPGirls10UAllStar", "FPGirls10UC", "FPGirls8uAllStar" };
		for(String str : divisions) {
			USSSAUtils.parseAndListTournamentDivision(str);
		}
	}
}
