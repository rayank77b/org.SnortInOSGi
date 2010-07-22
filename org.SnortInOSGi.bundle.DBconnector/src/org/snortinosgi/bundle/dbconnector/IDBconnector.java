/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.bundle.dbconnector;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IDBconnector interface provide a interface to access database.
 *
 */
public interface IDBconnector {
	
	/**
	 * Executes the given SQL statement, which returns a single ResultSet object. 
	 * @param sql	an SQL statement to be sent to the database, typically a static SQL SELECT statement 
	 * @return	a ResultSet object that contains the data produced by the given query; never null
	 * @throws SQLException
	 */
	public ResultSet executetSQL(String sql) throws SQLException ;
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
	 * or an SQL statement that returns nothing, such as an SQL DDL statement. 
	 * @param sql	an SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement. 
	 * @return	either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing 
	 * @throws SQLException
	 */
	public int executeCommand(String sql) throws SQLException;

}
