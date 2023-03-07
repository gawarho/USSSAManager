/**
 * 
 */
package org.mfp.softball.usssa.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mfp.softball.data.BaseTournamentListing;

/**
 * @author gawarho
 *
 */
public class USSSATournamentListing extends BaseTournamentListing { 
	@SuppressWarnings("unused")
	private Logger logger = null;
	
	public USSSATournamentListing() {
		super();
		logger = LogManager.getLogger(USSSATournamentListing.class.getName());		
	}	
}
