/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.openapi.snortschema;

import static org.junit.Assert.*;

import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Option;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.Reference;
import org.SnortInOSGi.openAPI.snortschema.Sensor;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.SnortInOSGi.openAPI.snortschema.UDPheader;
import org.junit.Test;

/**
 *
 */
public class TestSnortSchema {
		
	public Event createEvent() {
		Event ev = new Event();
		ev.cid=234L;
		ev.sid=12;
		ev.signature=3;
		ev.timestamp=1234567L;
		return ev;
	}
	
	@Test(timeout = 3000)
	public void testEvent() {
		Event ev = createEvent();
		
		try {
			String tmp = ev.toXML();
			//System.out.println(tmp);
			assertEquals(">> fail on event", 174, tmp.length());
			
			ev = (Event) Data.getFromXML(tmp, Event.class);
			
			assertEquals(">> fail on event", 234L, ev.cid);
			assertEquals(">> fail on event", 12, ev.sid);
			
			ev = (Event) Data.getFromXML(null, Event.class);
			assertNull(">>> not null", ev);
			
			//ev = Event.getFromXML("vasouds");
			//assertNull(">>> not null", ev);
			
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	public IPheader createIPheader() {
		IPheader ip = new IPheader();
		ip.dst = 34503404L;
		ip.src = 39349493L;
		ip.proto = IPheader.ICMP;
		ip.opts = new ArrayList<Option>();
		Option op = new Option();
		op.id = 0;
		op.proto = 6;
		op.code = 1;
		op.len = 0;
		op.data = null;
		ip.opts.add(op);
		op = new Option();
		op.id = 1;
		op.proto = 6;
		op.code = 1;
		op.len = 0;
		op.data = null;
		ip.opts.add(op);
		op = new Option();
		op.id = 2;
		op.proto = 6;
		op.code = 1;
		op.len = 8;
		op.data = "003D39749E8A2735";
		ip.opts.add(op);
		return ip;
	}
	
	@Test(timeout = 3000)
	public void testIPheader() {
		IPheader ip = createIPheader();		
		
		try {
			String tmp = ip.toXML();
			assertEquals(">> fail on IPheader", 678, tmp.length());
			//System.out.println(tmp);
			
			ip = (IPheader) Data.getFromXML(tmp, IPheader.class);
			
			assertEquals(">> fail on IPheader", 34503404L, ip.dst);
			assertEquals(">> fail on IPheader", 39349493L, ip.src);
			
			ip = (IPheader) Data.getFromXML(null, IPheader.class);
			assertNull(">>> not null", ip);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testICMPheader() {
		ICMPheader ic = new ICMPheader();
		ic.code = 0;
		ic.id = 4;
		ic.csum = 23954;
		ic.seq = 9454;
		ic.type = 8;
		
		try {
			String tmp = ic.toXML();
			assertEquals(">> fail on ICMPheader", 179, tmp.length());
			//System.out.println(tmp);
			
			ic = (ICMPheader) Data.getFromXML(tmp, ICMPheader.class);
			
			assertEquals(">> fail on IPheader", 23954, ic.csum);
			assertEquals(">> fail on IPheader", 8, ic.type);
			
			ic = (ICMPheader) Data.getFromXML(null, ICMPheader.class);
			assertNull(">>> not null",  ic);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testTCPheader() {
		TCPheader tcp = new TCPheader();
		tcp.ack = 23929339L;
		tcp.csum = 238;
		tcp.dport = 80;
		tcp.flags =0;
		tcp.off = 0;
		tcp.res = 1;
		tcp.seq = 23993493L;
		tcp.sport = 23320;
		tcp.urp =0;
		tcp.win = 4012;
		
		try {
			String tmp = tcp.toXML();
			assertEquals(">> fail on ICMPheader", 289, tmp.length());
			//System.out.println(tmp);
			
			tcp = (TCPheader) Data.getFromXML(tmp, TCPheader.class);
			
			assertEquals(">> fail on IPheader", 80, tcp.dport);
			assertEquals(">> fail on IPheader", 4012, tcp.win);
			
			tcp = (TCPheader) Data.getFromXML(null, TCPheader.class);
			assertNull(">>> not null", tcp);
			
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testUDPheader() {
		UDPheader udp = new UDPheader();
		udp.csum = 2393;
		udp.dport = 53;
		udp.sport = 23038;
		udp.len = 20;
		
		try {
			String tmp = udp.toXML();
			assertEquals(">> fail on ICMPheader", 168, tmp.length());
			//System.out.println(tmp);
			
			udp = (UDPheader) Data.getFromXML(tmp, UDPheader.class);
			
			assertEquals(">> fail on IPheader", 53, udp.dport);
			assertEquals(">> fail on IPheader", 20, udp.len);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testSensor() {
		Sensor sen = new Sensor();
		sen.detail_text = "full";
		sen.dev_interface = "eth0";
		sen.encoding_text = "hex";
		sen.filter = null;
		sen.hostname = "unknown:eth1";
		sen.last_cid = 100;
		sen.sid = 1;
		
		try {
			String tmp = sen.toXML();
			assertEquals(">> fail on ICMPheader", 274, tmp.length());
			//System.out.println(tmp);
			
			sen = (Sensor) Data.getFromXML(tmp, Sensor.class);
			
			assertEquals(">> fail on IPheader", "full", sen.detail_text);
			assertEquals(">> fail on IPheader", "unknown:eth1", sen.hostname);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testReference() {
		Reference ref = new Reference();
		ref.name = "url";
		ref.tag = "http://asdflu.de";

		try {
			String tmp = ref.toXML();
			assertEquals(">> fail on Reference", 134, tmp.length());
			//System.out.println(tmp);
			
			ref = (Reference) Data.getFromXML(tmp, Reference.class);
			
			assertEquals(">> fail on IPheader", "url", ref.name);
			assertEquals(">> fail on IPheader", "http://asdflu.de", ref.tag);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	public Signature createSignature() {
		Signature sig = new Signature();
		sig.class_id = 1;
		sig.class_name = "attempted-recon";
		sig.id = 1;
		sig.name = "WEB-CGI upload.pl access";
		sig.priority = 2;
		sig.rev = 5;
		sig.sid = 891;
		return sig;
	}
	
	@Test(timeout = 3000)
	public void testSignature() {
		Signature sig = createSignature();

		try {
			String tmp = sig.toXML();
			assertEquals(">> fail on ICMPheader", 273, tmp.length());
			//System.out.println(tmp);
			
			sig = (Signature)Data.getFromXML(tmp, Signature.class);
			
			assertEquals(">> fail on IPheader", "WEB-CGI upload.pl access", sig.name);
			
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
	}
	
	@Test(timeout = 3000)
	public void testAlert() {
		Signature sig = createSignature();
		IPheader ip = createIPheader();	
		Event ev = createEvent();
		Alert alert = new Alert();
		alert.event = ev;
		alert.iphdr = ip;
		alert.signature = sig;
		
		try {
			String tmp = alert.toXML();
			assertEquals(">> fail on ICMPheader", 1216, tmp.length());
			//System.out.println(tmp);
			
			alert = (Alert) Data.getFromXML(tmp, Alert.class);
			
			assertEquals(">> fail on IPheader", "WEB-CGI upload.pl access", alert.signature.name);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
		
	}
	
	@Test(timeout = 3000)
	public void testPacket() {
		IPheader ip = createIPheader();	
		Event ev = createEvent();
		ICMPheader icmp = new ICMPheader();
		icmp.code = 0;
		icmp.id = 4;
		icmp.csum = 23954;
		icmp.seq = 9454;
		icmp.type = 8;
		Packet pack = new Packet();
		pack.event = ev;
		pack.iphdr = ip;
		pack.icmphdr = icmp;
		
		
		try {
			String tmp = pack.toXML();
			assertEquals(">> fail on ICMPheader", 1110, tmp.length());
			//System.out.println(tmp);
			
			pack = (Packet) Data.getFromXML(tmp, Packet.class);
			
			assertEquals(">> fail on IPheader", 3, pack.event.signature);
			assertEquals(">> fail on IPheader",  8, pack.icmphdr.type);
		} catch (JAXBException e) {
			fail("TestSnortSchema fail on jaxb marshaling");
		}
		
	}

}
