/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;


/**
 *
 */
public class testSMDBreader_t6Statistics {

	@Test(timeout = 3000)
	public void test_getPacket() {
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
}
