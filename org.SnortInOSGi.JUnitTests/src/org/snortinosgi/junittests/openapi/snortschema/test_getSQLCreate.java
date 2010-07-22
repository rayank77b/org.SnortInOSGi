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
public class test_getSQLCreate {
	
	
	@Test(timeout = 3000)
	public void test_Event_getSQLInsert() {
		Event event = new Event();
		try {
			
			String toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			String ret = event.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL,eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}

	@Test(timeout = 3000)
	public void test_ICMPheader_getSQLInsert() {
		ICMPheader icmp = new ICMPheader();
		try {
			
			String toSQL = "data:"+ICMPheader.TYPE+";"+ICMPheader.TYPE;
			String ret = icmp.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(icmptype   TINYINT  UNSIGNED NOT NULL,icmptype   TINYINT  UNSIGNED NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}

	@Test(timeout = 3000)
	public void test_IPheader_getSQLInsert() {
		IPheader ip = new IPheader();
		try {
			
			String toSQL = "data:"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.PROTO+";"+IPheader.TTL;
			String ret = ip.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(iphdrdst  INT      UNSIGNED NOT NULL,iphdrsrc  INT      UNSIGNED NOT NULL,iphdrid   SMALLINT UNSIGNED,iphdrproto TINYINT  UNSIGNED NOT NULL,iphdrttl  TINYINT  UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_Reference_getSQLInsert() {
		Reference ref = new Reference();
		try {
			
			String toSQL = "data:"+Reference.NAME+";"+Reference.TAG;
			String ret = ref.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(refname  VARCHAR(20),reftag  TEXT NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_Sensor_getSQLInsert() {
		Sensor ref = new Sensor();
		try {
			
			String toSQL = "data:"+Sensor.HOSTNAME+";"+Sensor.DEVINTERFACE+";"+Sensor.DETAIL+";"+Sensor.ENCODING+";"+Sensor.FILTER;
			String ret = ref.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(sensorhostname TEXT,sensordevinterface TEXT,sensordetail TEXT,sensorencoding TEXT,sensorfilter TEXT)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_Signature_getSQLInsert() {
		Signature ref = new Signature();
		try {
			
			String toSQL = "data:"+Signature.NAME+";"+Signature.CLASSNAME+";"+Signature.PRIORITY;
			String ret = ref.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(signaturename VARCHAR(255) NOT NULL,signatureclassname VARCHAR(60) NOT NULL,signaturepriority INT UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_TCPheader_getSQLInsert() {
		TCPheader ref = new TCPheader();
		try {
			
			String toSQL = "data:"+TCPheader.DSTPORT+";"+TCPheader.SRCPORT+";"+TCPheader.SEQ+";"+TCPheader.ACK+";"+TCPheader.OFF;
			String ret = ref.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(tcpdstport  SMALLINT UNSIGNED NOT NULL,tcpsrcport  SMALLINT UNSIGNED NOT NULL,tcpseq  INT      UNSIGNED,tcpack  INT      UNSIGNED,tcpoff  TINYINT  UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	@Test(timeout = 3000)
	public void test_UDPheader_getSQLInsert() {
		UDPheader ref = new UDPheader();
		try {
			
			String toSQL = "data:"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
			String ret = ref.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(udpdstport  SMALLINT UNSIGNED NOT NULL,udpsrcport  SMALLINT UNSIGNED NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	
	@Test(timeout = 3000)
	public void test_Packet_getSQLInsert() {
		Packet pack = new Packet();
		pack.event= new Event();
		pack.iphdr= new IPheader();
		pack.icmphdr=new ICMPheader();
		pack.tcphdr=new TCPheader();
		pack.udphdr=new UDPheader();
		
		try {
			
			String toSQL = "data:"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT;
			String ret = pack.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(layer4proto   VARCHAR(10),layer4dstport SMALLINT UNSIGNED NOT NULL,layer4srcport SMALLINT UNSIGNED NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "data:"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT+";"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			ret = pack.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE data(layer4proto   VARCHAR(10),layer4dstport SMALLINT UNSIGNED NOT NULL,layer4srcport SMALLINT UNSIGNED NOT NULL,eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL,eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "data:"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT+";"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP
			+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.PROTO+";"+IPheader.TTL;
			ret = pack.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE data(layer4proto   VARCHAR(10),layer4dstport SMALLINT UNSIGNED NOT NULL,layer4srcport SMALLINT UNSIGNED NOT NULL,eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid" +
					" 	  	INT 	   UNSIGNED NOT NULL,eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL,iphdrdst  INT      UNSIGNED NOT NULL,iphdrsrc  INT      UNSIGNED NOT NULL,iphdrid   SMALLINT UNSIGNED,iphdrproto TINYINT  UNSIGNED NOT NULL,iphdrttl  TINYINT  UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "data:"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+ICMPheader.TYPE+";"+ICMPheader.TYPE;
			ret = pack.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE data(layer4proto   VARCHAR(10),layer4dstport SMALLINT UNSIGNED NOT NULL,icmptype   TINYINT  UNSIGNED NOT NULL,icmptype   TINYINT  UNSIGNED NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "dataPacket2:"+Event.CID+";"+Event.SID+";"
			+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"
			+IPheader.PROTO+";"+IPheader.TTL+";"+Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT+";"
			+TCPheader.ACK+";"+TCPheader.SEQ+";"+ICMPheader.CODE+";"+ICMPheader.TYPE+";"+UDPheader.DSTPORT+";"+UDPheader.SRCPORT;
			
			//System.out.println(toSQL);
			
			ret = pack.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE dataPacket2(eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL,iphdrdst  INT      UNSIGNED NOT NULL,iphdrsrc  INT      UNSIGNED NOT NULL,iphdrid   SMALLINT UNSIGNED,iphdrproto TINYINT  UNSIGNED NOT NULL,iphdrttl  TINYINT  UNSIGNED,layer4proto   VARCHAR(10),layer4dstport SMALLINT UNSIGNED NOT NULL,layer4srcport SMALLINT UNSIGNED NOT NULL,tcpack  INT      UNSIGNED,tcpseq  INT      UNSIGNED,icmpcode TINYINT  UNSIGNED NOT NULL,icmptype   TINYINT  UNSIGNED NOT NULL,udpdstport  SMALLINT UNSIGNED NOT NULL,udpsrcport  SMALLINT UNSIGNED NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
	
	@Test(timeout = 3000)
	public void test_Alert_getSQLInsert() {
		Alert alert = new Alert();
		alert.event= new Event();
		alert.iphdr= new IPheader();
		alert.signature = new Signature();
		
		try {
			
			String toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP;
			String ret = alert.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			String should = "CREATE TABLE data(eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL,eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.PROTO+";"+IPheader.TTL;
			ret = alert.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE data(eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL,eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL,iphdrdst  INT      UNSIGNED NOT NULL,iphdrsrc  INT      UNSIGNED NOT NULL,iphdrid   SMALLINT UNSIGNED,iphdrproto TINYINT  UNSIGNED NOT NULL,iphdrttl  TINYINT  UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
			
			toSQL = "data:"+Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP+";"+IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.PROTO+";"+IPheader.TTL+";"+Signature.NAME+";"+Signature.CLASSNAME+";"+Signature.PRIORITY;
			ret = alert.getSQLCreate(toSQL);
			
			//System.out.println(ret);
			should = "CREATE TABLE data(eventcid 	  	INT 	   UNSIGNED NOT NULL,eventsid 	  	INT 	   UNSIGNED NOT NULL," +
					"eventsignature 	INT       UNSIGNED NOT NULL,eventtimestamp 	DATETIME  NOT NULL,iphdrdst  INT      UNSIGNED NOT NULL," +
					"iphdrsrc  INT      UNSIGNED NOT NULL,iphdrid   SMALLINT UNSIGNED,iphdrproto TINYINT  UNSIGNED NOT NULL," +
					"iphdrttl  TINYINT  UNSIGNED," +
					"signaturename VARCHAR(255) NOT NULL,signatureclassname VARCHAR(60) NOT NULL,signaturepriority INT UNSIGNED)";
			assertEquals(">> error on sqlFromAlert ", should, ret);
			
		} catch (DataException e) {
			e.printStackTrace();
			fail(">> DataException");
		}		
	}
	
}
