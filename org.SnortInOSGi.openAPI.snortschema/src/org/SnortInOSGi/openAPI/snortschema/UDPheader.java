/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class UDPheader is a map for table udphdr in snort main database.
 */
@XmlRootElement(name = "UDPheader")
public class UDPheader extends Data{
	/**
	 * SRCPORT,DSTPORT declare how the fields should named in db analyzer database 
	 */
	public static String SRCPORT = "udpsrcport";
	public static String DSTPORT = "udpdstport";
	
	/**
	 * CSRCPORT,CDSTPORT declare the strings for create table sql-statement
	 */
	public static String CSRCPORT = "udpsrcport  SMALLINT UNSIGNED NOT NULL";
	public static String CDSTPORT = "udpdstport  SMALLINT UNSIGNED NOT NULL";
	
	/**
	 * necessary attribtes of table udphdr
	 */
	public int sport;		// source port
	public int dport; 		// destination port
	public int len;			// length of the data
	public int csum;		// checksum
	
	public UDPheader() {	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UDPheader [csum=" + csum + ", dport=" + dport + ", len=" + len
				+ ", sport=" + sport + "]";
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 =  SRCPORT;
			sql2 = "'" +this.sport+"'";
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = DSTPORT;
			sql2 = "'" +this.dport+"'";
		} else {
			throw new DataException("ERROR on UDPheader getSQLValues");
		}
		String []tmp = {sql1,sql2};
		return tmp;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLCreateValue(java.lang.String)
	 */
	@Override
	public String getSQLCreateValue(String value) throws DataException {
		String sql1;
		// get all event field
		if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 =  CSRCPORT;
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = CDSTPORT;
		} else {
			throw new DataException("ERROR on UDPheader getSQLValues");
		}
		return sql1;
	}
}
