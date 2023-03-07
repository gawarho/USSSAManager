/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentInfo;

/**
 * @author gawarho
 *
 */
public class USSSATournamentInfo extends BaseTournamentInfo{ 
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public USSSATournamentInfo() {
		super();
		logger = LogManager.getLogger(USSSATournamentInfo.class.getName());		
	}	
}
