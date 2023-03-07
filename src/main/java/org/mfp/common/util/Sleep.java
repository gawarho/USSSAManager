/**
 * 
 */
package org.mfp.common.util;

import java.util.Random;

/**
 * @author Glen
 *
 */
public class Sleep {
  static private Random rn = new Random();
  static public void sleep(int milliseconds) {
	  try {
		  Thread.sleep(milliseconds);
	  } catch(Exception e) {
		  
	  }
  }
  
  static public int random(int range) {
	  return rn.nextInt(range);
  }
}
