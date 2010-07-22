/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.dbanalyzer.rebuilddb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.junit.Test;
import org.snortinosgi.dbanalyzer.rebuilddb.RebuildDB;


/**
 *
 */
public class testMetadataDB {
	
	@Test(timeout = 3000)
	public void test_getMetadata() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			String ret = rebuild.getMetadata("testDB");
			assertEquals("error", null, ret);
			
			String should = "data2:eventcid;eventsid;eventtimestamp;signaturename;signatureclassname;" +
					"signaturepriority;iphdrdst;iphdrsrc;iphdrproto|data3:eventcid;eventtimestamp;" +
					"signaturename;signatureclassname;iphdrdst;iphdrsrc|data98:eventcid;eventtimestamp;signaturename";
			ret = rebuild.getMetadata("zzztestSnort1");
			assertEquals("error", should, ret);

			should = "data3:eventcid;eventtimestamp;" +
			"signaturename;signatureclassname;iphdrdst;iphdrsrc";
			ret = rebuild.getMetadata("zzztestSnort1", "data3");
			assertEquals("error", should, ret);
			
			ret = rebuild.getMetadata("zzztestSnort1", "data999999");
			assertEquals("error", null, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_insertMetadata() {
		try {
			RebuildDB rebuild = new RebuildDB();
			assertNotNull(">> Cannot create DBconnector", rebuild);
			
			int ret = rebuild.insertMetadata("testDB", "test:test;test2;test3");
			assertEquals("error", IRebuildDB.OK, ret);
			
			ret = rebuild.insertMetadata("testDB2", "test:test;test2;test3");
			assertEquals("error", IRebuildDB.OK, ret);

			ret = rebuild.insertMetadata("testDB", "test34:test;test2;test3");
			assertEquals("error", IRebuildDB.OK, ret);
			
			ret = rebuild.insertMetadata("testDB", "test34:test;test2;test3");
			assertEquals("error", IRebuildDB.EXISTS, ret);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	

}
