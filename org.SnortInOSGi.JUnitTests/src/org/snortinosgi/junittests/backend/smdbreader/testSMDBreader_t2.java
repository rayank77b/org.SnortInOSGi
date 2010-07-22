/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.Reference;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.SnortInOSGi.openAPI.snortschema.UDPheader;
import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;


/**
 *
 */
public class testSMDBreader_t2 {
	
	
	@Test(timeout = 3000)
	public void test_getICMPheader() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getICMPheader(101, 1);
			assertEquals(">> false length", 176, xml.length());
			
			ICMPheader icmp = (ICMPheader) Data.getFromXML(xml, ICMPheader.class);
			assertNotNull(">> Option is null", icmp);
			assertEquals(">> false icmp_type", 3, icmp.type);
			assertEquals(">> false icmp_code", 3, icmp.code);
			assertEquals(">> false icmp_csum", 47100, icmp.csum);
			assertEquals(">> false icmp_id", 0, icmp.id);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getUDPheader() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getUDPheader(25, 1);
			assertEquals(">> false length", 170, xml.length());
			
			UDPheader udp = (UDPheader) Data.getFromXML(xml, UDPheader.class);
			assertNotNull(">> UDPheader is null", udp);
			assertEquals(">> false udp_dst", 56701, udp.dport);
			assertEquals(">> false udp_src", 53, udp.sport);
			assertEquals(">> false udp_len", 115, udp.len);
			assertEquals(">> false udp_csum", 47532, udp.csum);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_getTCPheader() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			String xml = reader.getTCPheader(23, 1);
			assertEquals(">> false length", 294, xml.length());
			
			TCPheader tcp = (TCPheader) Data.getFromXML(xml, TCPheader.class);
			assertNotNull(">> TCPheader is null", tcp);
			assertEquals(">> false tcp_sport", 25992, tcp.sport);
			assertEquals(">> false tcp_dport", 80, tcp.dport);
			assertEquals(">> false tcp_seq", 32324957L, tcp.seq);
			assertEquals(">> false tcp_ack", 3653652081L, tcp.ack);
			assertEquals(">> false tcp_off", 5, tcp.off);
			assertEquals(">> false tcp_res", 0, tcp.res);
			assertEquals(">> false tcp_flags", 24, tcp.flags);
			assertEquals(">> false tcp_win", 65535, tcp.win);
			assertEquals(">> false tcp_csum", 4529, tcp.csum);
			assertEquals(">> false tcp_urp", 0, tcp.urp);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getReference() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			List<String> xmls = reader.getReference(2);
			assertEquals(">> false length", 126, xmls.get(0).length());
			
			Reference r = (Reference) Data.getFromXML(xmls.get(0), Reference.class);
			assertNotNull(">> Reference is null", r);
			
			assertEquals(">> false tcp_sport", "10041", r.tag);
			assertEquals(">> false tcp_sport", "nessus", r.name);
			
			xmls = reader.getReference(1);  // should be null
			if(xmls==null) 
				fail("should be zero");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	
	@Test(timeout = 3000)
	public void test_getSignatureClassName() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String ret = reader.getSignatureClassName(1);
			assertNotNull(">> String is null", ret);
			
			assertEquals(">> false tcp_sport", "attempted-recon", ret);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getSignature() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getSignature(1);
			assertEquals(">> false length", 273, xml.length());
			//System.out.println(xml);
			
			Signature ret = (Signature) Data.getFromXML(xml, Signature.class);
			assertNotNull(">> String is null", ret);
			assertEquals(">> false  sig_name", "WEB-CGI upload.pl access", ret.name);
			assertEquals(">> false sig_class_name", "attempted-recon", ret.class_name);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}

}
