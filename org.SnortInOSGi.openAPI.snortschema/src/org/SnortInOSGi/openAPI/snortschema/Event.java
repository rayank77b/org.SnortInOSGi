/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * class Event is a map for table event in snort main database.
 */
@XmlRootElement(name = "Event")
public class Event extends Data{
	/**
	 * SID,CID,SIGNATUREID,TIMESTAMP declare how the fields should named in db analyzer database 
	 */
	public static String SID 			= "eventsid";
	public static String CID 			= "eventcid";
	public static String SIGNATUREID 	= "eventsignature";
	public static String TIMESTAMP 		= "eventtimestamp";
	
	/**
	 * CSID,CCID,CSIGNATUREID,CTIMESTAMP declare the strings for create table sql-statement
	 */
	public static String CSID 			= "eventsid 	  	INT 	   UNSIGNED NOT NULL";
	public static String CCID 			= "eventcid 	  	INT 	   UNSIGNED NOT NULL";
	public static String CSIGNATUREID 	= "eventsignature 	INT       UNSIGNED NOT NULL";
	public static String CTIMESTAMP 	= "eventtimestamp 	DATETIME  NOT NULL";
	
	/**
	 * necessary attribtes of table event
	 */
	public long cid;		// store unique alert/log id
	public int 	sid;		// store sensor id
	public int 	signature;	// store signature id
	public long timestamp;	// store the time of alert appearance
	
	public Event() {
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value) throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(SID)) {
			sql1 =  SID;
			sql2 = "'" +this.sid+"'";
		} else if( value.equalsIgnoreCase(CID)) {
			sql1 = CID;
			sql2 = "'" +this.cid+"'";
		} else if( value.equalsIgnoreCase(TIMESTAMP)) {
			sql1 = TIMESTAMP;
			Timestamp t = new Timestamp(this.timestamp);
			sql2 = "'" +t.toString()+"'";
		} else if( value.equalsIgnoreCase(SIGNATUREID)) {
			sql1 = SIGNATUREID;
			sql2 = "'" +this.signature+"'";
		} else {
			throw new DataException("ERROR on Event getSQLValues");
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
		if( value.equalsIgnoreCase(SID)) {
			sql1 =  CSID;
		} else if( value.equalsIgnoreCase(CID)) {
			sql1 = CCID;
		} else if( value.equalsIgnoreCase(TIMESTAMP)) {
			sql1 = CTIMESTAMP;
		} else if( value.equalsIgnoreCase(SIGNATUREID)) {
			sql1 = CSIGNATUREID;
		} else {
			throw new DataException("ERROR on Event getSQLCreateValue");
		}
		
		return sql1;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [cid=" + cid + ", sid=" + sid + ", signature="
				+ signature + ", timestamp=" + timestamp + "]";
	}
}
