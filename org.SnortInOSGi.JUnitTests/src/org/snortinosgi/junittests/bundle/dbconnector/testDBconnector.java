/**
 * 
 */
package org.snortinosgi.junittests.bundle.dbconnector;


import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 *
 */
public class testDBconnector {
	
	@Test(timeout = 3000)
	public void testConnection() {
		DBconnector dbc = new DBconnector(false, "localhost");
		assertNotNull(">> Cannot create DBconnector", dbc);
		try {
			dbc.connect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void testExecuteSQL() {
		DBconnector dbc = new DBconnector(false, "localhost");
		assertNotNull(">> Cannot create DBconnector", dbc);
		try {
			dbc.connect();
			ResultSet rs = dbc.executetSQL("SELECT COUNT(*) FROM event");
			assertNotNull(">> get a ResultSet null back", rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void testExecuteUpdate() {
		DBconnector dbc = new DBconnector(false, "localhost");
		assertNotNull(">> Cannot create DBconnector", dbc);
		try {
			dbc.connect();
			int ret = dbc.executeCommand("SHOW TABLES");
			assertEquals(">> get an error back", ret, 16);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
		
	}
}
