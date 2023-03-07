/**
 * 
 */
package org.mfp.softball.pony.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentInfo;

/**
 * @author gawarho
 *
 */
public class PonyTournamentInfo extends BaseTournamentInfo{ 
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public PonyTournamentInfo() {
		super();
		logger = LogManager.getLogger(PonyTournamentInfo.class.getName());		
	}	
}
