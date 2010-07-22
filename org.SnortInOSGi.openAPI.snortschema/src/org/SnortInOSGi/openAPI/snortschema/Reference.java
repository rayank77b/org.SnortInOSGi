/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Reference is a map for tables reference and reference_system in snort main database.
 */
@XmlRootElement(name = "Reference")
public class Reference extends Data{
	
	/**
	 * NAME,TAG declare how the fields should named in db analyzer database 
	 */
	public static String NAME = "refname";
	public static String TAG = "reftag";
	
	/**
	 * NAME,TAG declare the strings for create table sql-statement
	 */
	public static String CNAME = "refname  VARCHAR(20)";
	public static String CTAG = "reftag  TEXT NOT NULL";
	
	/**
	 * necessary attribtes of table reference and reference_system
	 */
	public String name;		// store the typ of reference tag
	public String tag;		// store the number or url
	
	public Reference() {
	}



	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(NAME)) {
			sql1 =  NAME;
			sql2 = "'" +this.name+"'";
		} else if( value.equalsIgnoreCase(TAG)) {
			sql1 =  TAG;
			sql2 = "'" +this.tag+"'";
		} else {
			throw new DataException("ERROR on Reference getSQLValues");
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
		if( value.equalsIgnoreCase(NAME)) {
			sql1 =  CNAME;
		} else if( value.equalsIgnoreCase(TAG)) {
			sql1 =  CTAG;
		} else {
			throw new DataException("ERROR on Reference getSQLCreateValue");
		}
		return sql1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reference [name=" +  name + ", tag="
				+ tag + "]";
	}
}
