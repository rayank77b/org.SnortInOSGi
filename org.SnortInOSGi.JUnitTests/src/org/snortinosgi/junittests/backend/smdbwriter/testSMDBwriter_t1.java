/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbwriter;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;
import org.snortinosgi.backend.smdbwriter.SMDBwriter;


/**
 *
 */
public class testSMDBwriter_t1 {
	
	@Test(timeout = 3000)
	public void test_WriterConstruct() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}

	@Test(timeout = 3000)
	public void test_deleteICMPheader() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteICMPheader(2377, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_deleteTCPheader() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteTCPheader(3, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_deleteUDPheader() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteUDPheader(32, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_deleteIPheader() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteIPheader(10, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_deleteEvent() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteEvent(10, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_deleteCID() {
		try {
			SMDBwriter writer = new SMDBwriter();
			assertNotNull(">> Cannot create DBconnector", writer);
			
			writer.deleteCID(11, 1);
			//
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	/*
	 * after deleting datas, you should for testing reinsert the data without error
	 * 
INSERT INTO icmphdr VALUES(1, 2377, 3, 3, 47100, NULL, NULL);
INSERT INTO tcphdr VALUES(1, 3, 38146, 80, 4158790916, 3556573833, 8, 0, 24, 90, 9405, 0);
INSERT INTO udphdr VALUES(1, 32, 53, 33518, 117, 31818);
INSERT INTO iphdr VALUES(1,10,2255247270, 2255234159, 4, 5, 0,752,11021,0,0,62,6,34317);
INSERT INTO event VALUES(1,10,6,'2008-05-19 16:58:53');

INSERT INTO event VALUES(1,11,5,'2008-05-19 16:59:01');
INSERT INTO iphdr VALUES(1,11, 1480619512, 2255233539, 4,5, 16,1186, 0,0,0, 240,6,0);
INSERT INTO tcphdr VALUES(1,11,61127, 80, 3608307764, 1441758057, 5,0,24,10720,0,0);

	*/
	
}
