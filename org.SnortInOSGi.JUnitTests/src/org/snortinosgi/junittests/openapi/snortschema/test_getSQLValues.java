/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.openapi.snortschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.SnortInOSGi.openAPI.snortschema.DataException;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.SnortInOSGi.openAPI.snortschema.UDPheader;
import org.junit.Test;


/**
 *
 */
public class test_getSQLValues {
	
	public Event buildEvent() {
		Event event = new Event();
		
		event.sid=1;
		event.cid=4;
		event.signature=4;
		event.timestamp=12349034;
		
		return event;
	}
	
	public IPheader buildIPheader() {
		IPheader iphdr = new IPheader();
		iphdr.dst=2255234159L;
		iphdr.src=1032854078L;
		iphdr.proto=IPheader.RESERVED;
		iphdr.ver=4;
		iphdr.csum=30320;
		iphdr.hlen=5;
		iphdr.len=170;
		iphdr.id=60;
		
		return iphdr;
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
	
	@Test(timeout = 3000)
	public void test_Packet_getSQLInsert() {
		Packet p = new Packet();
		p.event = buildEvent();
		p.iphdr = buildIPheader();
		
		//System.out.println("packet: "+p.toString());
		//System.out.println("event: "+p.event.toString());
		//System.out.println("iphdr: "+p.iphdr.toString());
		try {
			
			String toSQL = "dataPacket2:"+Event.CID+";"+Event.SID+";"
			+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"
			+IPheader.PROTO+";"+IPheader.TTL+";"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT+";"
			+TCPheader.ACK+";"+TCPheader.SEQ+";"+ICMPheader.CODE+";"+ICMPheader.TYPE+";"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
			
			
			String ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			String should = "INSERT INTO dataPacket2(" +
					"eventcid,eventsid,eventtimestamp," +
					"iphdrdst,iphdrsrc,iphdrid,iphdrproto,iphdrttl," +
					"layer4proto,layer4dstport,layer4srcport," +
					"tcpack,tcpseq," +
					"icmpcode,icmptype," +
					"udpdstport,udpsrcport) " +
					"VALUES('4','1','1970-01-01 04:25:49.034'," +
					"'2255234159','1032854078','60','255','0','unknown','0','0','0','0','0','0','0','0')";
			
			assertEquals(">> error on sqlFromAlert ", should, ret);

			
			p.iphdr.proto=IPheader.TCP;
			p.tcphdr = buildTCPheader();
			
			ret = p.getSQLInsert(toSQL);
			//System.out.println(ret);
			should = "INSERT INTO dataPacket2(eventcid,eventsid,eventtimestamp,iphdrdst,iphdrsrc,iphdrid,iphdrproto,iphdrttl," +
					"layer4proto,layer4dstport,layer4srcport,tcpack,tcpseq,icmpcode,icmptype,udpdstport,udpsrcport) " +
					"VALUES('4','1','1970-01-01 04:25:49.034','2255234159','1032854078','60','6'," +
					"'0','tcp','80','349570','230987','1234908','0','0','0','0')";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		} 		
	}

}
