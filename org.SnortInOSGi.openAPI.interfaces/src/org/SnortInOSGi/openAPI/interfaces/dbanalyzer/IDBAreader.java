/**
 * @author Andrej Frank, 2009, 2010, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.dbanalyzer;

import java.util.List;

/**
 * IDBAreader is an interface for read-only access to separete databases (db analyzer). 
 */
public interface IDBAreader {
	
	/**
	 * OK is a status for no errors		
	 */
	public static int OK = 1;
	
	/**
	 * 	Some errors are occured
	 */
	public static int ERROR = 2;
	
	/**
	 * Table, database exists		
	 */
	public static int EXISTS = 3;
	
	/**
	 * Syntax error in SQL-statement		
	 */
	public static int ERROR_SQL = 4;
	
	
	/**
	 * get all information about dbnames and table name of new databases.
	 * @return 		information string stored as "databasename:tablename|dbname2:tablename2|...."
	 */
	public String getAllMetadataInfos();

	/**
	 * Set the used database. Analog command on sql: "use dbname"
	 * @param dbname	the name of the database which should be used
	 * @return 		should return an int numer OK or ERROR
	 */
	public int setDatabase(String dbname);
		
	/**
	 * get stored data for certain cid number
	 * @param cid		cid number of alert/log
	 * @param sqlstring	sql string, how to get the data
	 * @param dbname	database name
	 * @return		return a string of data as "value|value2|value3|..."
	 */
	public String getDataForCid(long cid, String sqlstring, String dbname);
	
	/**
	 * get stored data for certain cids number interval
	 * @param fromcid	start cid of alert/log 
	 * @param tocid		stop cid of alert/log
	 * @param sqlstring	sql string, how to get the data
	 * @param dbname	database name
	 * @return		retturn a list of strings of data as "value|value2|value3|..."
	 */
	public List<String>  getDataForCids(long fromcid, long tocid, String sqlstring, String dbname);
	
	/**
	 * get the metadata about the separate stored database with databasename and tablename. 
	 * @param dbname	the database name
	 * @return 		String as a  "data:field;field;field..." fild or null on error.
	 */
	public String getMetadata(String dbname, String tablename);
	
}
