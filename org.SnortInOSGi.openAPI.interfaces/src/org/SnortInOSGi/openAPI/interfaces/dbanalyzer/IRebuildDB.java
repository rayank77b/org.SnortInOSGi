/**
 * @author Andrej Frank, 2009,2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.dbanalyzer;

/**
 * IRebuildDB provide a methode to store snort main database datas in a remote separated database.
 * Second IRebuildDB reorginize the stored data in an once table.
 */
public interface IRebuildDB {
	
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
	 * Create a database on a separate database, which should run on DBAnalyser
	 * @param name	the name of the database
	 * @return 		should return an int numer OK, ERROR or EXISTS
	 */
	public int createDatabase(String name);
	
	/**
	 * Delete a database on a separate database, which should run on DBAnalyser
	 * @param name	the name of the database
	 * @return 		should return an int numer OK or ERROR
	 */
	public int deleteDatabase(String name);
	
	/**
	 * Set the used database. Analog command on sql: "use dbname"
	 * @param dbname	the name of the database which should be used
	 * @return 		should return an int numer OK or ERROR
	 */
	public int setDatabase(String dbname);
	
	/**
	 * Create a table. The database must be before selected. This should be only used if 
	 * the database is yet selected. Rather use createTable(String dbname, String tablename).
	 * @param sql	sql-statement for create the table. Should be generated from tosql string and a Data object.
	 * @return 		should return an int numer OK, ERROR or EXISTS
	 */
	public int createTable(String tosql);
	
	/**
	 * Create a table on the database with name dbname. 
	 * @param dbname the name of the database
	 * @param sql	sql-statement for create the table. Should be generated from tosql string and a Data object.
	 * @return 		should return an int numer OK, ERROR or EXISTS
	 */
	public int createTable(String dbname, String tosql);
	
	/**
	 * Copy all information stored in Alert Object in separate database
	 * @param cid	the id of the event 
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyAlert(long cid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Alert Object in separate database. After copying delete the event data with cid ID.
	 * @param cid	the id of the event 
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyAlertAndDelete(long cid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Alert Objects in separate database
	 * @param fromcid	begining id of the event
	 * @param tocid	ending id of the event
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyAlerts(long fromcid, long tocid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Alert Objects in separate database. After copying delete the event data with cids ID.
	 * @param fromcid	begining id of the event
	 * @param tocid	ending id of the event
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyAlertsAndDelete(long fromcid, long tocid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Packet Object in separate database.
	 * @param cid	the id of the event 
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyPacket(long cid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Packet Object in separate database. After copying delete the event data with cids ID.
	 * @param cid	the id of the event 
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyPacketAndDelete(long cid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Packet Objects in separate database.
	 * @param fromcid	begining id of the event
	 * @param tocid	ending id of the event
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyPackets(long fromcid, long tocid, int sid, String toSQL, String dbname);
	
	/**
	 * Copy all information stored in Packet Objects in separate database. After copying delete the event data with cids ID.
	 * @param fromcid	begining id of the event
	 * @param tocid	ending id of the event
	 * @param sid	the sensor id
	 * @param toSQL	how to store the data. "data:field;field;field..."
	 * @param dbname the target database name
	 * @return 		should return an int numer OK, ERROR or ERROR_SQL
	 */
	public int copyPacketsAndDelete(long fromcid, long tocid, int sid, String toSQL, String dbname);
	
	/**
	 * test if a table is exists in database.
	 * @param tablename	the name of the table
	 * @return 		return EXISTS if table exists or ERROR if table doesnot exists
	 */
	public int testTableExists(String tablename);
	
	/**
	 * set the metadata about the separate stored database. The datas are stored in a metadata db :
	 * create database metadataDB;
	 * use metadataDB;
	 * CREATE TABLE metadata(
     * 		dbname VARCHAR(100) NOT NULL,
     * 		sqlstring TEXT NOT NULL
	 * );
	 * @param dbname	the new database name
	 * @param sqlstring	how to store the data. "data:field;field;field..."
	 * @return 		return OK or ERROR
	 */
	public int insertMetadata(String dbname, String sqlstring);
	
	/**
	 * get the metadata about the separate stored database with databasename. 
	 * @param dbname	the database name
	 * @return 		String as a  "data:field;field;field|data2:blub;blub..." fild or null on error.
	 */
	public String getMetadata(String dbname);
	
	/**
	 * get the metadata about the separate stored database with databasename and tablename. 
	 * @param dbname	the database name
	 * @return 		String as a  "data:field;field;field..." fild or null on error.
	 */
	public String getMetadata(String dbname, String tablename);
	
	
}
