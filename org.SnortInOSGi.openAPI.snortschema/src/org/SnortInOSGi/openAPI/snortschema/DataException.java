/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

/**
 * DataException are exception happen on errors due to getSQLValues() and getSQLCreateValue().
 * DataException should be throws if there a request for a field which doesn't exists in this object.
 */
public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DataException() {
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DataException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DataException(Throwable cause) {
		super(cause);
	}

	
}
