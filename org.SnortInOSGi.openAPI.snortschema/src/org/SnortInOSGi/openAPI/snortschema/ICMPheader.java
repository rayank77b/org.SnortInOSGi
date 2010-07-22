/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * class ICMPheader is a map for table icmphdr in snort main database.
 */
@XmlRootElement(name = "ICMPheader")
public class ICMPheader extends Data{
   
	/**
	 * TYPE, CODE declare how the fields should named in db analyzer database 
	 */
	public static String TYPE = "icmptype";
	public static String CODE = "icmpcode";
	
	/**
	 * CTYPE, CCODE declare the strings for create table sql-statement
	 */
	public static String CTYPE = "icmptype   TINYINT  UNSIGNED NOT NULL";
	public static String CCODE = "icmpcode TINYINT  UNSIGNED NOT NULL";
	
	/**
	 * necessary attribtes of table icmphdr
	 */
	public int type;    // icmp type
	public int code;    // icmp code
	public int csum;    // checksum
	public int id;      // identification number
	public int seq;     // sequence
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(TYPE)) {
			sql1 =  TYPE;
			sql2 = "'" +this.type+"'";
		} else if( value.equalsIgnoreCase(CODE)) {
			sql1 = CODE;
			sql2 = "'" +this.code+"'";
		} else {
			throw new DataException("ERROR on ICMPheader getSQLValues");
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
		if( value.equalsIgnoreCase(TYPE)) {
			sql1 =  CTYPE;
		} else if( value.equalsIgnoreCase(CODE)) {
			sql1 = CCODE;
		} else {
			throw new DataException("ERROR on ICMPheader getSQLCreateValue");
		}
		return sql1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ICMPheader [code=" + code + ", csum=" + csum + ", id=" + id
				+ ", seq=" + seq + ", type=" + type + "]";
	}
}
