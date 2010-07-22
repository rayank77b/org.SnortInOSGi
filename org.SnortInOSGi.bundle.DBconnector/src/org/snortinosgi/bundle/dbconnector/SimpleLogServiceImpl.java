/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.bundle.dbconnector;

/**
 * 
 */
public class SimpleLogServiceImpl implements SimpleLogService {

	/* (non-Javadoc)
	 * @see org.snortinosgi.bundle.dbconnector.SimpleLogService#log(java.lang.String)
	 */
	@Override
	public void log(String message) {
		System.out.println(message);
	}

	
}
