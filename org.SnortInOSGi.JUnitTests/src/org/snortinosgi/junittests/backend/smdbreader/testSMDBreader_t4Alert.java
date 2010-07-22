/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;

/**
 *
 */
public class testSMDBreader_t4Alert {

	@Test(timeout = 3000)
	public void test_getAlert() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getAlert(2, 1);
			assertEquals(">> false length", 1295, xml.length());
			//System.out.println(xml);
			Alert alert = (Alert) Data.getFromXML(xml, Alert.class);
			assertNotNull(">> Alert is null", alert);
			
			Event ev = alert.event;
			assertNotNull(">> Event is null", ev);
			
			IPheader ip = alert.iphdr;
			assertNotNull(">> Event is null", ip);
			
			Signature sign = alert.signature;
			assertNotNull(">> Signature is null", sign);
			assertEquals(">> false signaturename ", "WEB-CGI cgiwrap access", sign.name);
			
			xml = reader.getAlert(300,1);
			assertNull(">> Alert is not null", xml);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		} 
	}
	
	
	@Test(timeout = 3000)
	public void test_getAlertsCid() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			List<String> xmls = reader.getAlerts(2,5, 1);
			assertEquals(">> false size of list ", 4, xmls.size());
			
			Alert alert = (Alert) Data.getFromXML(xmls.get(0), Alert.class);
			assertNotNull(">> Alert is null", alert);
			
			Event ev = alert.event;
			assertNotNull(">> Event is null", ev);
			
			IPheader ip = alert.iphdr;
			assertNotNull(">> Event is null", ip);
			
			Signature sign = alert.signature;
			assertNotNull(">> Signature is null", sign);
			
			/*
			xmls = reader.getAlerts(50, 150, 1);
			assertEquals(">> false size of list ", 51,xmls.size());
			*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		} 
	}
	
	
	@Test(timeout = 3000)
	public void test_getAlertsTimestamp() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
			Date dt = df.parse( "2008-05-19 16:58:26.0" );
			Timestamp from = new Timestamp(dt.getTime());
			
			dt = df.parse( "2008-05-19 16:59:17.0" );
			Timestamp to = new Timestamp(dt.getTime());
			
			List<String> xmls = reader.getAlertsTime(from.getTime(), to.getTime(), 1);
			assertEquals(">> false size of list ", 13, xmls.size());
			
			Alert alert = (Alert) Data.getFromXML(xmls.get(0), Alert.class);
			assertNotNull(">> Alert is null", alert);
			
			Event ev = alert.event;
			assertNotNull(">> Event is null", ev);
			
			IPheader ip = alert.iphdr;
			assertNotNull(">> Event is null", ip);
			
			Signature sign = alert.signature;
			assertNotNull(">> Signature is null", sign);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		} catch (ParseException e) {
			e.printStackTrace();
			fail(">> Time ParseException");
		} 
	}
	
	@Test(timeout = 3000)
	public void test_getLatestAlerts() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			List<String> xmls = reader.getLatestAlerts(10, 1);
			
			assertEquals(">> false size of list ", 10, xmls.size());
			
			Alert alert = (Alert) Data.getFromXML(xmls.get(0), Alert.class);
			assertNotNull(">> Alert is null", alert);
			
			Event ev = alert.event;
			assertNotNull(">> Event is null", ev);
			
			IPheader ip = alert.iphdr;
			assertNotNull(">> Event is null", ip);
			
			Signature sign = alert.signature;
			assertNotNull(">> Signature is null", sign);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		} 
	}
	
	
}
