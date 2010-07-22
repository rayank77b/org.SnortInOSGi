/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Signature is a map for table signature, sig_class and sig_reference in snort main database.
 * maybe we should implement the references tables here too?
 */
@XmlRootElement(name = "Signature")
public class Signature extends Data{
	/**
	 * NAME,CLASSNAME,PRIORITY declare how the fields should named in db analyzer database 
	 */
	public static String NAME = "signaturename";
	public static String CLASSNAME = "signatureclassname";
	public static String PRIORITY = "signaturepriority";
	
	/**
	 * CNAME,CCLASSNAME,CPRIORITY declare the strings for create table sql-statement
	 */
	public static String CNAME = "signaturename VARCHAR(255) NOT NULL";
	public static String CCLASSNAME = "signatureclassname VARCHAR(60) NOT NULL";
	public static String CPRIORITY = "signaturepriority INT UNSIGNED";
	
	/**
	 * priority constant. There exists 3 prioritys: 1,2,3  or high,middle,low
	 */
	public static int PRIO_RED 		= 1;
	public static int PRIO_YELLOW 	= 2;
	public static int PRIO_BLUE 	= 3;
	public static int PRIO_HIGH 	= 1;
	public static int PRIO_MIDDLE 	= 2;
	public static int PRIO_LOW 		= 3;
	
	/**
	 * necessary attribtes of table signature, sig_class and sig_reference
	 * id: signature id
	 * name: name of the signature
	 * class_name: art/class of attack
	 */
	public int id;			// signature id
	public String name;		// description about the alert and his name
	public int class_id;	// class id (depricated: doubled)  FIXME
	public int priority;	// signature priority (high, middle or low)
	public int rev;      	// signature revision, a versionnumber of the signature rule
	public int sid;      	// Snort ID for a signature: sid -> signature id
	public String class_name;	

	public Signature() {
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
		} else if( value.equalsIgnoreCase(CLASSNAME)) {
			sql1 = CLASSNAME;
			sql2 = "'" +this.class_name+"'";
		} else if( value.equalsIgnoreCase(PRIORITY)) {
			sql1 = PRIORITY;
			sql2 = "'" +this.priority+"'";
		} else {
			throw new DataException("ERROR on Signature getSQLValues");
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
		} else if( value.equalsIgnoreCase(CLASSNAME)) {
			sql1 = CCLASSNAME;
		} else if( value.equalsIgnoreCase(PRIORITY)) {
			sql1 = CPRIORITY;
		} else {
			throw new DataException("ERROR on Signature getSQLCreateValue");
		}
		return sql1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Signature [class_id=" + class_id + ", class_name=" + class_name
				+ ", id=" + id + ", name=" + name + ", priority=" + priority
				+ ", rev=" + rev + ", sid=" + sid + "]";
	}
}
