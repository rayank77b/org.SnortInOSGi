/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Sensor;
import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;



public class testSMDBreader_t3 {
	
	@Test(timeout = 3000)
	public void test_getLastCid() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			long ret = reader.getLastCid(1);
			//assertNotNull(">> String is null", ret);
			
			assertEquals(">> false last cid", 100, ret);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getSensor() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getSensor(1);
			assertEquals(">> false length", 274, xml.length());
			
			Sensor ret = (Sensor) Data.getFromXML(xml, Sensor.class);
			
			assertNotNull(">> String is null", ret);
			assertEquals(">> false last_cid", 100, ret.last_cid);
			assertEquals(">> false hostname", "unknown:eth1", ret.hostname);
			assertEquals(">> false interface", "eth1", ret.dev_interface);
			assertEquals(">> false detail_text", "full", ret.detail_text);
			assertEquals(">> false encoding_text", "hex", ret.encoding_text);
			//System.out.println(":"+ret.filter+":");
			//assertEquals(">> false ", , ret.);
			ret = (Sensor) Data.getFromXML(null, Sensor.class);
			assertNull(">> String is not null", ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getSensorList() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			List<String> xmls = reader.getSensorList();
			assertEquals(">> false length", 274, xmls.get(0).length());
			
			Sensor ret = (Sensor) Data.getFromXML(xmls.get(0), Sensor.class);
			assertNotNull(">> String is null", ret);
			
			
			assertEquals(">> false last_cid", 100, ret.last_cid);
			assertEquals(">> false hostname", "unknown:eth1", ret.hostname);
			assertEquals(">> false interface", "eth1", ret.dev_interface);
			assertEquals(">> false detail_text", "full", ret.detail_text);
			assertEquals(">> false encoding_text", "hex", ret.encoding_text);
			
			//System.out.println(":"+ret.filter+":");
			//assertEquals(">> false ", , ret.);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
}