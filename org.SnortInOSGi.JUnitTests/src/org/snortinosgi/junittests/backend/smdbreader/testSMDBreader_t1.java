/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;

/**
 *
 */
public class testSMDBreader_t1 {

	@Test(timeout = 3000)
	public void testCreation() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_getCount() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			
			
			Timestamp from=new Timestamp(1000*60*60*24);
			Timestamp to=new Timestamp(new Date().getTime());
			
			int nr = reader.getCountTime(from.getTime(), to.getTime(), 1);
			assertEquals(">> false numbers ", 100, nr);
			nr = reader.getCountCID(10, 50, 1);
			assertEquals(">> false numbers ", 41, nr);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
		
	}
	
	@Test(timeout = 3000)
	public void test_getCountProtocol() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);

			
			
			int nr = reader.getCountCID(1, 100, 1, IPheader.TCP);
			assertEquals(">> false numbers ", 90, nr);
			nr = reader.getCountCID(1, 100, 1, IPheader.UDP);
			assertEquals(">> false numbers ", 7, nr);
			nr = reader.getCountCID(1, 100, 1, IPheader.ICMP);
			assertEquals(">> false numbers ", 0, nr);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
		
	}
	
	
	@Test(timeout = 3000)
	public void test_getCountProtocolTime() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);

			Timestamp from=new Timestamp(1000*60*60*24);
			Timestamp to=new Timestamp(new Date().getTime());
			
			
			int nr = reader.getCountTime(from.getTime(), to.getTime(), 1, IPheader.TCP);
			assertEquals(">> false numbers ", 90, nr);
			nr = reader.getCountTime(from.getTime(), to.getTime(), 1, IPheader.UDP);
			assertEquals(">> false numbers ", 7, nr);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
		
	}
	
	
	@Test(timeout = 3000)
	public void test_getEvent() { 
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
						
			String tmp = reader.getEvent(95, 1);
			assertEquals(">> fail on event", 178, tmp.length());
			//System.out.println(tmp);
			
			Event ev = (Event) Data.getFromXML(tmp, Event.class);
			
			assertNotNull(">> Event is null", ev);
			assertEquals(">> false signature", 5, ev.signature);
			assertNotNull(">> Event.timestamp is null", ev.timestamp);
			
			tmp = reader.getEvent(195, 1);
			assertNull(">> String is not null", tmp);
			
			ev = (Event) Data.getFromXML(tmp, Event.class);
			assertNull(">> Event is not null", ev);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getData() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			String data = reader.getPayload(95, 1);
			assertNotNull(">> Data is null", data);
			//System.out.println(data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getIPheader() { 
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
				
			String xml = reader.getIPheader(95, 1);
			assertEquals(">> fail on event", 317, xml.length());
			//System.out.println(xml);
			IPheader ip = (IPheader) Data.getFromXML(xml, IPheader.class);
			
			assertNotNull(">> IP is null", ip);
			assertEquals(">> false src", 2255247294L, ip.src);
			assertEquals(">> false dst", 2255233539L, ip.dst);
			assertEquals(">> false ", 4, ip.ver);
			assertEquals(">> false ", 240, ip.ttl);
			assertEquals(">> false ", 0, ip.csum);
			assertEquals(">> false ", 0, ip.flags);
			//assertNotNull(">> Event.timestamp is null", ev.timestamp);
			
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getCountSignaturePriority() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);

			
			
			int nr = reader.getSignaturePriorityCount(Signature.PRIO_BLUE, 1, 100, 1);
			assertEquals(">> false numbers ", 14, nr);
			nr = reader.getSignaturePriorityCount(Signature.PRIO_YELLOW, 1, 100, 1);
			assertEquals(">> false numbers ", 44, nr);
			nr = reader.getSignaturePriorityCount(Signature.PRIO_RED, 1, 100, 1);
			assertEquals(">> false numbers ", 42, nr);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
		
	}
	
}
