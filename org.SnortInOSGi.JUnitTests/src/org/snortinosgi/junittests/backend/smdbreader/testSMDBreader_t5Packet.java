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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.junit.Test;
import org.snortinosgi.backend.smdbreader.SMDBreader;


/**
 *
 */
public class testSMDBreader_t5Packet {

	@Test(timeout = 3000)
	public void test_getPacket() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			String xml = reader.getPacket(2,1);
			assertNotNull(">> Packet is null", xml);
			
			Packet p = (Packet) Data.getFromXML(xml, Packet.class); 
			Event ev = p.event;
			assertNotNull(">> Event is null", ev);
			assertEquals(">> false signature", 2, ev.signature);
			assertEquals(">> false signature", "2008-05-19 16:58:08.0", new Timestamp(ev.timestamp).toString());
			
			IPheader ip = p.iphdr;
			assertNotNull(">> IP header is null", ip);
			assertEquals(">> false src ip", 3257391626L, ip.src);
			assertEquals(">> false ip protocol", 6, ip.proto);
			
			if(ip.proto==IPheader.TCP) {
				TCPheader tcp = p.tcphdr;
				assertNotNull(">> TCP header is null", tcp);
				assertEquals(">> false tcp_sport", 42498, tcp.sport);
				assertEquals(">> false tcp_dport", 80, tcp.dport);
				assertEquals(">> false tcp_seq", 1966360972L, tcp.seq);
				assertEquals(">> false tcp_ack", 3536945545L, tcp.ack);
				assertEquals(">> false tcp_off", 8, tcp.off);
				assertEquals(">> false tcp_res", 0, tcp.res);
				assertEquals(">> false tcp_flags", 24, tcp.flags);
				assertEquals(">> false tcp_win", 17520, tcp.win);
				assertEquals(">> false tcp_csum", 37997, tcp.csum);
				assertEquals(">> false tcp_urp", 0, tcp.urp);
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
	public void test_getPacketsCids() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			List<String> xmls = reader.getPackets(2,6,1);
			assertEquals(">> false size", 5, xmls.size());
			
			Packet p = (Packet) Data.getFromXML(xmls.get(0), Packet.class); 
			assertNotNull(">> Packet is null", p);
			
			Event ev = p.event;
			assertNotNull(">> Event is null", ev);
			assertEquals(">> false signature", 2, ev.signature);
			assertEquals(">> false signature", "2008-05-19 16:58:08.0", new Timestamp(ev.timestamp).toString());
			
			IPheader ip = p.iphdr;
			assertNotNull(">> IP header is null", ip);
			assertEquals(">> false src ip", 3257391626L, ip.src);
			assertEquals(">> false ip protocol", 6, ip.proto);
			
			if(ip.proto==IPheader.TCP) {
				TCPheader tcp = p.tcphdr;
				assertNotNull(">> TCP header is null", tcp);
				assertEquals(">> false tcp_sport", 42498, tcp.sport);
				assertEquals(">> false tcp_dport", 80, tcp.dport);
				assertEquals(">> false tcp_seq", 1966360972L, tcp.seq);
				assertEquals(">> false tcp_ack", 3536945545L, tcp.ack);
				assertEquals(">> false tcp_off", 8, tcp.off);
				assertEquals(">> false tcp_res", 0, tcp.res);
				assertEquals(">> false tcp_flags", 24, tcp.flags);
				assertEquals(">> false tcp_win", 17520, tcp.win);
				assertEquals(">> false tcp_csum", 37997, tcp.csum);
				assertEquals(">> false tcp_urp", 0, tcp.urp);
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
	public void test_getFirstTimeOfCid() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			long tmp = reader.getFirstTimeOfCid(2, 1);
			Timestamp time = new Timestamp(tmp);
			assertNotNull(">> Timestamp is null", time);
			assertEquals(">> false time", "2008-05-19 16:58:08.0", time.toString());
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(">> ClassNotFoundException");
		} catch (SQLException e) {
			e.printStackTrace();
			fail(">> SQLException");
		}
	}
	
	@Test(timeout = 3000)
	public void test_getgetFirstCidOfTime() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
			Date dt = df.parse( "2008-05-19 16:58:26.0" );
			Timestamp t = new Timestamp(dt.getTime());
			
			long cid = reader.getFirstCidOfTime(t.getTime(), 1);
			assertEquals(">> false cid", 3, cid);
			

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
	
	@Test(timeout = 5000)
	public void test_getPacketsTime() {
		try {
			SMDBreader reader = new SMDBreader();
			assertNotNull(">> Cannot create DBconnector", reader);
			
			SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
			Date dt = df.parse( "2008-05-19 16:58:26.0" );
			Timestamp from = new Timestamp(dt.getTime());
			
			dt = df.parse( "2008-05-19 16:59:00.0" );
			Timestamp to = new Timestamp(dt.getTime());
			
			
			List<String> xmls = reader.getPacketsTime(from.getTime(), to.getTime(),1);
			//List<String> xmls = reader.getPackets(3, 8,1);
			assertEquals(">> false size", 9, xmls.size());
			
			
			Packet p = (Packet) Data.getFromXML(xmls.get(0), Packet.class); 
			assertNotNull(">> Packet is null", p);
			
			Event ev = p.event;
			assertNotNull(">> Event is null", ev);
			assertEquals(">> false signature", 3, ev.signature);
			
			
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
}
