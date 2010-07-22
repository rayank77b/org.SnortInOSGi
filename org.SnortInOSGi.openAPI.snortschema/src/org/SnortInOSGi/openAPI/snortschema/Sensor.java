/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Sensor is a map for tables sensor, detail and encoding in snort main database.
 */
@XmlRootElement(name = "Sensor")
public class Sensor extends Data {

	/**
	 * HOSTNAME,DEVINTERFACE,FILTER,ENCODING,DETAIL declare how the fields should named in db analyzer database 
	 */
	public static String HOSTNAME = "sensorhostname";
	public static String DEVINTERFACE = "sensordevinterface";
	public static String FILTER = "sensorfilter";
	public static String ENCODING = "sensorencoding";
	public static String DETAIL = "sensordetail";
	
	/**
	 * CHOSTNAME,CDEVINTERFACE,CFILTER,CENCODING,CDETAIL declare the strings for create table sql-statement
	 */
	public static String CHOSTNAME = "sensorhostname TEXT";
	public static String CDEVINTERFACE = "sensordevinterface TEXT";
	public static String CFILTER = "sensorfilter TEXT";
	public static String CENCODING = "sensorencoding TEXT";
	public static String CDETAIL = "sensordetail TEXT";
	
	/**
	 * necessary attribtes of table sensor
	 */
	public int sid;					// sensor id
	public String hostname;			// hostname of sensor
	public String dev_interface;	// device interface
	public String filter; 
	public long last_cid;			// last stored cied
	public String encoding_text;	// how the data are stored in table data. (hex, base64 or ascii)
	public String detail_text;

	public Sensor() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sensor [detail_text=" + detail_text + ", dev_interface="
				+ dev_interface + ", encoding_text=" + encoding_text
				+ ", filter=" + filter + ", hostname=" + hostname
				+ ", last_cid=" + last_cid + ", sid=" + sid + "]";
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(HOSTNAME)) {
			sql1 =  HOSTNAME;
			sql2 = "'" +this.hostname+"'";
		} else if( value.equalsIgnoreCase(DEVINTERFACE)) {
			sql1 =  DEVINTERFACE;
			sql2 = "'" +this.dev_interface+"'";
		} else if( value.equalsIgnoreCase(FILTER)) {
			sql1 =  FILTER;
			sql2 = "'" +this.filter+"'";
		} else if( value.equalsIgnoreCase(ENCODING)) {
			sql1 =  ENCODING;
			sql2 = "'" +this.encoding_text+"'";
		} else if( value.equalsIgnoreCase(DETAIL)) {
			sql1 = DETAIL;
			sql2 = "'" +this.detail_text+"'";
		} else {
			throw new DataException("ERROR on Sensor getSQLValues");
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
		if( value.equalsIgnoreCase(HOSTNAME)) {
			sql1 =  CHOSTNAME;
		} else if( value.equalsIgnoreCase(DEVINTERFACE)) {
			sql1 =  CDEVINTERFACE;
		} else if( value.equalsIgnoreCase(FILTER)) {
			sql1 =  CFILTER;
		} else if( value.equalsIgnoreCase(ENCODING)) {
			sql1 =  CENCODING;
		} else if( value.equalsIgnoreCase(DETAIL)) {
			sql1 = CDETAIL;
		} else {
			throw new DataException("ERROR on Sensor getSQLCreateValue");
		}
		return sql1;
	}
}
