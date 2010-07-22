/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.openapi.snortschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.DataException;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
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
public class test_getSQLInsert {
	
	public Event buildEvent() {
		Event event = new Event();
		
		event.sid=1;
		event.cid=12;
		event.signature=12;
		event.timestamp=12349034;
		
		return event;
	}
	
	public IPheader buildIPheader() {
		IPheader iphdr = new IPheader();
		iphdr.dst=1234393;
		iphdr.src=1233493;
		iphdr.proto=IPheader.TCP;
		return iphdr;
	}
	
	public Signature buildSignature() {
		Signature signature = new Signature();
		
		signature.class_id=23;
		signature.class_name="misc-activity";
		signature.id=11;
		signature.name="SCAN UPnP service discover attempt";
		signature.priority=1;
		signature.rev=436;
		signature.sid=11;
		return signature;
		
	}
	
	public ICMPheader buildICMPheader() {
		ICMPheader i = new ICMPheader();
		i.code=8;
		i.type=3;
		i.csum=2354;
		i.id=1;
		i.seq=0;
		return i;
	}
	
	public UDPheader buildUDPheader() {
		UDPheader u = new UDPheader();
		u.csum=230;
		u.dport=53;
		u.sport=3408;
		u.len=20;
		return u;		
	}
	
	public TCPheader buildTCPheader() {
		TCPheader t = new TCPheader();
		t.ack=230987;
		t.seq=1234908;
		t.csum=2304;
		t.dport=80;
		t.sport=349570;
		t.flags=24;
		t.off=3340;
		t.res=0;
		t.urp=0;
		t.win=10720;
		
		return t;
	}
	
	public Sensor buildSensor() {
		Sensor s = new Sensor();
		s.hostname="unknown:eth1";
		s.dev_interface="eth1";
		s.filter="NULL";
		s.detail_text="full";
		s.last_cid=9000;
		s.encoding_text="hex";
		return s;
	}
	
	public Reference buildReference() {
		Reference r = new Reference();
		r.name = "url";
		r.tag = "www.spywareguide.com/product_show.php?id=9";
		return r;
	}
	
	@Test(timeout = 3000)
	public void test_Event_getSQLInsert() {
		Event event = buildEvent();
		
		try {
			String toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.TIMESTAMP;
			String ret = event.getSQLInsert(toSQL);
			String should = "INSERT INTO data(eventcid,eventsid,eventtimestamp) VALUES('12','1','1970-01-01 04:25:49.034')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_Signature_getSQLInsert() {
		Signature signature = buildSignature();
		
		try {
			String toSQL = "data:"+Signature.NAME+";"+Signature.CLASSNAME+";"+Signature.PRIORITY;
			String ret = signature.getSQLInsert(toSQL);
			String should = "INSERT INTO data(signaturename,signatureclassname,signaturepriority) VALUES('SCAN UPnP service discover attempt','misc-activity','1')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_IPheader_getSQLInsert() {
		IPheader ipheader = buildIPheader();
		
		try {
			String toSQL = "data:"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.PROTO+";"+IPheader.TTL;
			String ret = ipheader.getSQLInsert(toSQL);
			String should = "INSERT INTO data(iphdrdst,iphdrsrc,iphdrid,iphdrproto,iphdrttl) VALUES('1234393','1233493','0','6','0')";
			assertEquals(">> error on signature.getSQLInsert(); ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_Alert_getSQLInsert() {
		// build the alert
		Alert alert = new Alert();
		alert.event = buildEvent();
		alert.iphdr = buildIPheader();
		alert.signature = buildSignature();

		try {
			
			String toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.TIMESTAMP;
			String ret = alert.getSQLInsert(toSQL);
			String should = "INSERT INTO data(eventcid,eventsid,eventtimestamp) VALUES('12','1','1970-01-01 04:25:49.034')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC;
			System.out.println(toSQL);
			ret = alert.getSQLInsert(toSQL);
			
			should = "INSERT INTO data(eventcid,eventsid,eventtimestamp,iphdrdst,iphdrsrc) VALUES('12','1','1970-01-01 04:25:49.034','1234393','1233493')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			toSQL = "data:"+Event.CID+";"+Signature.NAME+";"+Signature.CLASSNAME+";"+Signature.PRIORITY;
			ret = alert.getSQLInsert(toSQL);
			should = "INSERT INTO data(eventcid,signaturename,signatureclassname,signaturepriority) VALUES('12','SCAN UPnP service discover attempt','misc-activity','1')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_ICMPheader_getSQLInsert() {
		ICMPheader icmph = buildICMPheader();
		
		try {
			String toSQL = "data:"+ICMPheader.CODE+";"+ICMPheader.TYPE;
			String ret = icmph.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(icmpcode,icmptype) VALUES('8','3')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_UDPheader_getSQLInsert() {
		UDPheader udp = buildUDPheader();
		
		try {
			String toSQL = "data:"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
			String ret = udp.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(udpdstport,udpsrcport) VALUES('53','3408')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_TCPheader_getSQLInsert() {
		TCPheader tcp = buildTCPheader();
		
		try {
			String toSQL = "data:"+TCPheader.DSTPORT+";"+TCPheader.SRCPORT+";"+TCPheader.SEQ+";"+TCPheader.ACK+";"+TCPheader.OFF;
			String ret = tcp.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(tcpdstport,tcpsrcport,tcpseq,tcpack,tcpoff) VALUES('80','349570','1234908','230987','3340')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_Reference_getSQLInsert() {
		Reference ref = buildReference();
		
		try {
			String toSQL = "data:"+Reference.NAME+";"+Reference.TAG;
			String ret = ref.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(refname,reftag) VALUES('url','www.spywareguide.com/product_show.php?id=9')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_Sensor_getSQLInsert() {
		Sensor sen = buildSensor();
		
		try {
			String toSQL = "data:"+Sensor.HOSTNAME+";"+Sensor.DEVINTERFACE+";"+Sensor.DETAIL+";"+Sensor.ENCODING+";"+Sensor.FILTER;
			String ret = sen.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(sensorhostname,sensordevinterface,sensordetail,sensorencoding,sensorfilter) VALUES('unknown:eth1','eth1','full','hex','NULL')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
	
	@Test(timeout = 3000)
	public void test_Packet_getSQLInsert() {
		Packet p = new Packet();
		p.event = buildEvent();
		p.iphdr = buildIPheader();
		p.tcphdr = buildTCPheader();
		
		try {
			String toSQL = "data:"+Event.SID+";"+Event.CID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			toSQL = toSQL+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.PROTO;
			toSQL = toSQL+";"+TCPheader.DSTPORT+";"+TCPheader.SRCPORT;
			
			String ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO data(eventsid,eventcid,eventsignature,eventtimestamp,iphdrdst,iphdrsrc,iphdrproto,tcpdstport,tcpsrcport) VALUES('1','12','12','1970-01-01 04:25:49.034','1234393','1233493','6','80','349570')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			try {
				toSQL = "data:"+Event.SID+";"+Event.CID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
				toSQL = toSQL+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.PROTO;
				toSQL = toSQL+";"+ICMPheader.TYPE+";"+ICMPheader.CODE;
				
				ret = p.getSQLInsert(toSQL);
				//System.out.println(ret);
				should = "INSERT INTO data(eventsid,eventcid,eventsignature,eventtimestamp,iphdrdst,iphdrsrc,iphdrproto,icmptype,icmpcode) VALUES('1','12','12','1970-01-01 04:25:49.034','1234393','1233493','6','0','0')";
				assertEquals(">> error on sqlFromAlert ", should, ret);
			} catch (DataException e) {
				fail("invoce a exception, icmpheader can be zero");
			}
			
			// test with icmp
			p.tcphdr=null;
			p.iphdr.proto = IPheader.ICMP;
			p.icmphdr=buildICMPheader();
			toSQL = "data:"+Event.SID+";"+Event.CID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			toSQL = toSQL+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.PROTO;
			toSQL = toSQL+";"+ICMPheader.TYPE+";"+ICMPheader.CODE;
			
			ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			should = "INSERT INTO data(eventsid,eventcid,eventsignature,eventtimestamp,iphdrdst,iphdrsrc,iphdrproto,icmptype,icmpcode) VALUES('1','12','12','1970-01-01 04:25:49.034','1234393','1233493','1','3','8')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			// test with udp
			p.udphdr = buildUDPheader();
			p.iphdr.proto = IPheader.UDP;
			p.icmphdr = null;
			toSQL = "data:"+Event.SID+";"+Event.CID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			toSQL = toSQL+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.PROTO;
			toSQL = toSQL+";"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
			
			ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			should = "INSERT INTO data(eventsid,eventcid,eventsignature,eventtimestamp,iphdrdst,iphdrsrc,iphdrproto,udpdstport,udpsrcport) VALUES('1','12','12','1970-01-01 04:25:49.034','1234393','1233493','17','53','3408')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			p.udphdr = buildUDPheader();
			p.icmphdr = null;
			p.iphdr.proto = IPheader.UDP;
			toSQL = "data:"+Event.SID+";"+Event.CID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			toSQL = toSQL+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.PROTO;
			toSQL = toSQL+";"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT;
			
			ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			should = "INSERT INTO data(eventsid,eventcid,eventsignature,eventtimestamp,iphdrdst,iphdrsrc,iphdrproto,layer4proto,layer4dstport,layer4srcport) VALUES('1','12','12','1970-01-01 04:25:49.034','1234393','1233493','17','udp','53','3408')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}
}
