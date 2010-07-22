/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.dbanalyzer.dbareader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.snortinosgi.dbanalyzer.dbareader.DBAreader;


/**
 *
 */
public class testMetadata {
	String tosql = "data9:eventcid;eventsid;eventtimestamp;signaturename;signatureclassname;signaturepriority;iphdrdst;iphdrsrc;iphdrproto";

	@Test(timeout = 3000)
	public void test_getMetadata() {
		try {
			DBAreader reader = new DBAreader();
			assertNotNull(">> Cannot create DBAreader", reader);
			String should, ret;
			
			should = "eventcid;eventsid;eventtimestamp;signaturename;signatureclassname;signaturepriority;iphdrdst;iphdrsrc;iphdrproto";
			ret = reader.getMetadata("zzztestSnort1", "data9");
			assertEquals("error", should, ret);
			
			should = "zzztestSnort1:data2|zzztestSnort1:data3|zzztestSnort1:data98|testDB:test34|testDB2:test|testDB:test|zzztestSnort1:data9";
			ret = reader.getAllMetadataInfos();
			assertEquals("error", should, ret);
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_generateSelectString() {
		try {
			DBAreader reader = new DBAreader();
			assertNotNull(">> Cannot create DBAreader", reader);
			
			String should = "SELECT eventcid, eventsid, eventtimestamp, signaturename, " +
					"signatureclassname, signaturepriority, iphdrdst, iphdrsrc, iphdrproto FROM data9";
			
			String ret = reader.generateSelectString(tosql);
			assertEquals("error", should, ret);
			
			int cnt = reader.getFieldsCount(tosql);
			assertEquals("error", 9, cnt);
			
			ret = reader.generateSelectString("data9");
			assertEquals("error", null, ret);
			
			cnt = reader.getFieldsCount("data9");
			assertEquals("error", 0, cnt);
			
			should = "SELECT eventcid FROM data9";
			ret = reader.generateSelectString("data9:eventcid");
			assertEquals("error", should, ret);
			
			cnt = reader.getFieldsCount("data9:eventcid");
			assertEquals("error", 1, cnt);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getDataForCid() {
		try {
			DBAreader reader = new DBAreader();
			assertNotNull(">> Cannot create DBAreader", reader);
			
			String ret = reader.getDataForCid(300, tosql, "zzztestSnort1");
			assertEquals("error", "300|1|17:15:08|WEB-CGI htsearch access|trojan-activity|2|2255234159|1304300351|6", ret);
			
			ret = reader.getDataForCid(3030, tosql, "zzztestSnort1");
			assertEquals("error", null, ret);
			
			List<String> list = reader.getDataForCids(10, 30, tosql, "zzztestSnort1");
			assertEquals("error", "11|1|16:59:01|WEB-IIS header field buffer overflow attempt|attempted-admin|1|2255233539|1480619512|6", list.get(1));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
}
