/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Alert is a map for talbes event, iphdr, signatures, sig_class and references of snort database schema.
 * Alert represent the event log, ip header information and signatures information. The upper layer information are not stored.
 */
@XmlRootElement(name = "Alert")
public class Alert extends Data{
	
	/**
	 * necessary to stored objects in Alert
	 */
	public Event event;
	public IPheader iphdr;
	public Signature signature;
	
	public Alert() {
		event = null;
		iphdr = null;
		signature = null;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value) throws DataException{
		String []tmp;
		
		// test for event
		try {
			tmp = event.getSQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for iphdr
		try {
			tmp = iphdr.getSQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for signature
		try {
			tmp = signature.getSQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}

		throw new DataException("ERROR on Alert getSQLValues");
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLCreateValue(java.lang.String)
	 */
	@Override
	public String getSQLCreateValue(String value) throws DataException {
		String tmp;
		
		// test for event
		try {
			tmp = event.getSQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for iphdr
		try {
			tmp = iphdr.getSQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for signature
		try {
			tmp = signature.getSQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}

		throw new DataException("ERROR on Alert getSQLValues");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alert [event=" + event + ", iphdr=" + iphdr + ", signature="
				+ signature + "]";
	}
}
