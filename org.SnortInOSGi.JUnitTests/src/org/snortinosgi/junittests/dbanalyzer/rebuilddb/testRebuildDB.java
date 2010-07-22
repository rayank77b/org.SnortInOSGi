/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.dbanalyzer.rebuilddb;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.DataException;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.SnortInOSGi.openAPI.snortschema.UDPheader;
import org.junit.Test;
import org.snortinosgi.dbanalyzer.rebuilddb.RebuildDB;



/**
 *
 */
public class testRebuildDB {
	private String databasename = "zzztestSnort1";
	private String toSQL = "dataAlert:"+Event.CID+";"+Event.SID+";"
	+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"
	+IPheader.PROTO+";"+IPheader.TTL+";"+Signature.NAME+";"+Signature.CLASSNAME+";"+Signature.PRIORITY;
	
	private String toSQL2 = "dataPacket:"+Event.CID+";"+Event.SID+";"
	+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"
	+IPheader.PROTO+";"+IPheader.TTL+";"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT;
	
	private String toSQL3 = "dataPacket2:"+Event.CID+";"+Event.SID+";"
	+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"
	+IPheader.PROTO+";"+IPheader.TTL+";"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT+";"
	+TCPheader.ACK+";"+TCPheader.SEQ+";"+ICMPheader.CODE+";"+ICMPheader.TYPE+";"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
	
	@Test(timeout = 3000)
	public void test_createDatabase() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.createDatabase(databasename);
			assertEquals("error", IRebuildDB.OK, ret);
			
			// attempt to repeat should fail
			ret = rebuild.createDatabase(databasename);
			assertEquals("error", IRebuildDB.ERROR, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_setDatabase() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.setDatabase(databasename);
			assertEquals("error", IRebuildDB.OK, ret);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_createTableAlert() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			Alert alert = new Alert();
			alert.event= new Event();
			alert.iphdr= new IPheader();
			alert.signature = new Signature();
			
			String sql;
			try {
				sql = alert.getSQLCreate(toSQL);
				int ret = rebuild.createTable(databasename, sql);
				assertEquals(">> error on create Table ", IRebuildDB.OK, ret);

				// test for same creation of table
				ret = rebuild.createTable(databasename, sql);
				assertEquals(">> error, table method should return error! ", IRebuildDB.ERROR, ret);
				
				
				ret = rebuild.testTableExists("dataAlertNew");
				assertEquals(">> error, table method should return error! ", IRebuildDB.ERROR, ret);
				
				ret = rebuild.testTableExists("dataAlert");
				assertEquals(">> error, table method should return exists! ", IRebuildDB.EXISTS, ret);
				
			} catch (DataException e) {
				e.printStackTrace();
				fail("DataException on test_createTable2");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_createTablePacket() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			Packet packet = new Packet();
			packet.event= new Event();
			packet.iphdr= new IPheader();
			packet.icmphdr = new ICMPheader();
			packet.tcphdr = new TCPheader();
			packet.udphdr = new UDPheader();
			
			String sql;
			try {
				sql = packet.getSQLCreate(toSQL2);
				int ret = rebuild.createTable(databasename, sql);
				assertEquals(">> error on create Table ", IRebuildDB.OK, ret);
				
				sql = packet.getSQLCreate(toSQL3);
				
				//System.out.println(sql);
				ret = rebuild.createTable(databasename, sql);
				assertEquals(">> error on create Table ", IRebuildDB.OK, ret);
				
			} catch (DataException e) {
				e.printStackTrace();
				fail("DataException on test_createTablePacket");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_copyAlert() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyAlert(1,1,toSQL, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_copyAlerts() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyAlerts(3, 7,1,toSQL, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 4000)
	public void test_copyAlertsMany() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret;
			for(int i=10;i<110;i=i+10) {
				ret = rebuild.copyAlerts(i, i+9,1,toSQL, databasename);
				
				assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	
	@Test(timeout = 3000)
	public void test_copyPacket() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyPacket(33,1,toSQL2, databasename);
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			ret = rebuild.copyPacket(33,1,toSQL2, databasename);
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);  // add the same secondary
			
			//System.out.println(toSQL3);
			ret = rebuild.copyPacket(34,1,toSQL3, databasename);
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			ret = rebuild.copyPacket(33,1,toSQL3, databasename);
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_copyPackets() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
						
			int ret = rebuild.copyPackets(51, 61, 1,toSQL3, databasename);
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	
	@Test(timeout = 3000)
	public void test_copyAlertAndDelete() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyAlertAndDelete(11,1,toSQL, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_copyAlertsAndDelete() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyAlertsAndDelete(1,3,1,toSQL, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_copyPacketAndDelete() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyPacketAndDelete(14,1,toSQL3, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_copyPacketsAndDelete() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.copyPacketsAndDelete(15,17,1,toSQL3, databasename);
			
			assertEquals(">> error on copy ", IRebuildDB.OK, ret);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	/*
	@Test(timeout = 3000)
	public void test_deleteDatabase() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			// atempt to delete an unexisted database should fail
			int ret = rebuild.deleteDatabase(databasename+"3");
			assertEquals("error", IRebuildDB.ERROR, ret);
			
			ret = rebuild.deleteDatabase(databasename);
			assertEquals("error", IRebuildDB.OK, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}*/
}
