/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.dbanalyzer.rebuilddb;

import static org.junit.Assert.assertNotNull;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.junit.Test;
import org.snortinosgi.dbanalyzer.rebuilddb.Activator;


/**
 *
 */
public class testWriter {
	
	@Test(timeout = 3000)
	public void test_deleteCID() {
		ISMDBwriter writer = Activator.getDefault().getWriter();
		assertNotNull(">> Cannot create DBconnector", writer);
	}
}
