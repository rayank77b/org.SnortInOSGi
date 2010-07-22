/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * HelpFunctions are some static functions which are used by several objects, 
 * but should not implemetned as methods.
 * String2ipLong()/ipLong2String() convert the dot string ip in long ip
 * convertXML2...List() convert a list of xml-string in a list of objects.
 * getField() get results from ResultSet (database result object) and how datas are stored string
 * getBounds(), getName() are used for creating tables
 */
public class HelpFunctions {
	
	/**
	 * String2ipLong() convert a dot string ip ex: "192.168.12.34" to a long number.
	 * @param ip	ip addresse as dot string as "192.168.12.34"
	 * @return		ip addresse as long number
	 */
	public static long String2ipLong(String ip) {
		String []tmp = ip.split("\\.");
		
		if(tmp.length!=4)
			return -4L;
		
		long ip1 = Long.valueOf(tmp[0]);
		long ip2 = Long.valueOf(tmp[1]);
		long ip3 = Long.valueOf(tmp[2]);
		long ip4 = Long.valueOf(tmp[3]);
		
		if(ip1>255 || ip2>255 || ip3>255 || ip4>255)
			return -1L;
		if(ip1<0 || ip2<0 || ip3<0 || ip4<0)
			return -2L;
		
		return ip4+ip3*256+ip2*256*256+ip1*256*256*256;
	}
	
	/**
	 * ipLong2String() convert a long number ip to a dot string ip "192.168.12.34"
	 * @param ip	ip addresse as long number
	 * @return		ip addresse as dot string "192.168.12.34"
	 */
	public static String ipLong2String(long ip) {
		
		long ip1, ip2, ip3, ip4;
		ip4=ip&0x0ff;
		ip3=ip&0x0ff00;
		ip3=ip3>>8;
		ip2=ip&0x0ff0000;
		ip2=ip2>>16;
		ip1=ip&0x0ff000000;
		ip1=ip1>>24;
		String s= new String(ip1+"."+ip2+"."+ip3+"."+ip4);
		
		return s;
	}
	
	/**
	 * convert a list of xml string of object Alert to a list of Alert objects
	 * @param xmls	a list of xml-string of object Alert
	 * @return	a list of Alert objects
	 */
	public static List<Alert> convertXML2AlertList(List<String> xmls) {
		List<Alert> alerts = new LinkedList<Alert>();
		
		for(String xml : xmls) {
			alerts.add((Alert) Data.getFromXML(xml, Alert.class));
		}
		return alerts;
	}
	
	/**
	 * convert a list of xml string of object Reference to a list of Reference objects
	 * @param xmls	a list of xml-string of object Reference
	 * @return	a list of Reference objects
	 */
	public static List<Reference> convertXML2ReferenceList(List<String> xmls) {
		List<Reference> refs = new LinkedList<Reference>();
		
		for(String xml : xmls) {
			refs.add((Reference) Data.getFromXML(xml, Reference.class));
		}
		return refs;
	}
	
	/**
	 * getField() return a string of datas from a ResultSet object, getted from a query(sql) result.
	 * @param rs	ResultSet result from a database sql query request.
	 * @param sqlstring	how the data are stored in database as metdata string 
	 * @return	a string of data stored as "data1|data2|data3|...". On error return null.
	 * @throws SQLException
	 */
	public static String getField(ResultSet rs, String sqlstring) throws SQLException {
		String []tmp = sqlstring.split(":");
		
		if(tmp.length!=2)
			return null;
		
		String []fields = tmp[1].split(";");
		if(fields.length==0)
			fields[0]=tmp[1];
		
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<fields.length; i++) {
			// Event
			if(fields[i].equalsIgnoreCase(Event.CID))
				buffer.append(Long.toString(rs.getLong(Event.CID))+"|");
			if(fields[i].equalsIgnoreCase(Event.SID))
				buffer.append(Long.toString(rs.getLong(Event.SID))+"|");
			if(fields[i].equalsIgnoreCase(Event.SIGNATUREID))
				buffer.append(Integer.toString(rs.getInt(Event.SIGNATUREID))+"|");
			if(fields[i].equalsIgnoreCase(Event.TIMESTAMP)) 
				buffer.append(rs.getTimestamp(Event.TIMESTAMP).toString()+"|");
			
				
			
			// IPheader
			if(fields[i].equalsIgnoreCase(IPheader.DST))
				buffer.append(Long.toString(rs.getLong(IPheader.DST))+"|");
			if(fields[i].equalsIgnoreCase(IPheader.SRC))
				buffer.append(Long.toString(rs.getLong(IPheader.SRC))+"|");
			if(fields[i].equalsIgnoreCase(IPheader.ID))
				buffer.append(Integer.toString(rs.getInt(IPheader.ID))+"|");
			if(fields[i].equalsIgnoreCase(IPheader.TTL))
				buffer.append(Integer.toString(rs.getInt(IPheader.TTL))+"|");
			if(fields[i].equalsIgnoreCase(IPheader.PROTO))
				buffer.append(Integer.toString(rs.getInt(IPheader.PROTO))+"|");
			
			// Tcphdr
			if(fields[i].equalsIgnoreCase(TCPheader.ACK))
				buffer.append(Long.toString(rs.getLong(TCPheader.ACK))+"|");
			if(fields[i].equalsIgnoreCase(TCPheader.SEQ))
				buffer.append(Long.toString(rs.getLong(TCPheader.SEQ))+"|");
			if(fields[i].equalsIgnoreCase(TCPheader.OFF))
				buffer.append(Integer.toString(rs.getInt(TCPheader.OFF))+"|");
			if(fields[i].equalsIgnoreCase(TCPheader.SRCPORT))
				buffer.append(Integer.toString(rs.getInt(TCPheader.SRCPORT))+"|");
			if(fields[i].equalsIgnoreCase(TCPheader.DSTPORT))
				buffer.append(Integer.toString(rs.getInt(TCPheader.DSTPORT))+"|");
			
			// icmphdr
			if(fields[i].equalsIgnoreCase(ICMPheader.TYPE))
				buffer.append(Integer.toString(rs.getInt(ICMPheader.TYPE))+"|");
			if(fields[i].equalsIgnoreCase(ICMPheader.CODE))
				buffer.append(Integer.toString(rs.getInt(ICMPheader.CODE))+"|");
			
			// udphdr
			if(fields[i].equalsIgnoreCase(UDPheader.DSTPORT))
				buffer.append(Integer.toString(rs.getInt(UDPheader.DSTPORT))+"|");
			if(fields[i].equalsIgnoreCase(UDPheader.SRCPORT))
				buffer.append(Integer.toString(rs.getInt(UDPheader.SRCPORT))+"|");
			
			// packet
			if(fields[i].equalsIgnoreCase(Packet.PROTOCOL))
				buffer.append(rs.getString(Packet.PROTOCOL)+"|");
			if(fields[i].equalsIgnoreCase(Packet.DSTPORT))
				buffer.append(Integer.toString(rs.getInt(Packet.DSTPORT))+"|");
			if(fields[i].equalsIgnoreCase(Packet.SRCPORT))
				buffer.append(Integer.toString(rs.getInt(Packet.SRCPORT))+"|");
			
			// reference
			if(fields[i].equalsIgnoreCase(Reference.TAG))
				buffer.append(rs.getString(Reference.TAG)+"|");
			if(fields[i].equalsIgnoreCase(Reference.NAME))
				buffer.append(rs.getString(Reference.NAME)+"|");
			
			// sensor
			if(fields[i].equalsIgnoreCase(Sensor.HOSTNAME))
				buffer.append(rs.getString(Sensor.HOSTNAME)+"|");
			if(fields[i].equalsIgnoreCase(Sensor.DEVINTERFACE))
				buffer.append(rs.getString(Sensor.DEVINTERFACE)+"|");
			if(fields[i].equalsIgnoreCase(Sensor.FILTER))
				buffer.append(rs.getString(Sensor.FILTER)+"|");
			if(fields[i].equalsIgnoreCase(Sensor.ENCODING))
				buffer.append(rs.getString(Sensor.ENCODING)+"|");
			if(fields[i].equalsIgnoreCase(Sensor.DETAIL))
				buffer.append(rs.getString(Sensor.DETAIL)+"|");
			
			//signature
			if(fields[i].equalsIgnoreCase(Signature.NAME))
				buffer.append(rs.getString(Signature.NAME)+"|");
			if(fields[i].equalsIgnoreCase(Signature.CLASSNAME))
				buffer.append(rs.getString(Signature.CLASSNAME)+"|");
			if(fields[i].equalsIgnoreCase(Signature.PRIORITY))
				buffer.append(Integer.toString(rs.getInt(Signature.PRIORITY))+"|");
		}
		
		String ret = buffer.toString();
		if( ret.endsWith("|"))
			return ret.substring(0, ret.length()-1);
		return ret;
	}
	
	/**
	 * getBounds() return a bound for table row width.
	 * @param s	string of the field.
	 * @return	width of the table row
	 */
	public static int getBounds(String s) {
		// event
		if(s.equalsIgnoreCase(Event.CID))
			return 70;
		else if(s.equalsIgnoreCase(Event.SID))
			return 70;
		else if(s.equalsIgnoreCase(Event.SIGNATUREID))
			return 70;
		else if(s.equalsIgnoreCase(Event.TIMESTAMP))
			return 200;
		
		// icmp header
		else if(s.equalsIgnoreCase(ICMPheader.TYPE))
			return 70;
		else if(s.equalsIgnoreCase(ICMPheader.CODE))
			return 70;
		
		// IP header
		else if(s.equalsIgnoreCase(IPheader.DST))
			return 200;
		else if(s.equalsIgnoreCase(IPheader.SRC))
			return 200;
		else if(s.equalsIgnoreCase(IPheader.ID))
			return 70;
		else if(s.equalsIgnoreCase(IPheader.TTL))
			return 70;
		else if(s.equalsIgnoreCase(IPheader.PROTO))
			return 70;
		
		// Packet
		else if(s.equalsIgnoreCase(Packet.DSTPORT))
			return 100;
		else if(s.equalsIgnoreCase(Packet.SRCPORT))
			return 100;
		else if(s.equalsIgnoreCase(Packet.PROTOCOL))
			return 100;
		
		// Reference
		else if(s.equalsIgnoreCase(Reference.TAG))
			return 70;
		else if(s.equalsIgnoreCase(Reference.NAME))
			return 100;
		
		// Sensor
		else if(s.equalsIgnoreCase(Sensor.DETAIL))
			return 150;
		else if(s.equalsIgnoreCase(Sensor.DEVINTERFACE))
			return 150;
		else if(s.equalsIgnoreCase(Sensor.ENCODING))
			return 150;
		else if(s.equalsIgnoreCase(Sensor.FILTER))
			return 150;
		else if(s.equalsIgnoreCase(Sensor.HOSTNAME))
			return 150;
		
		// Signature
		else if(s.equalsIgnoreCase(Signature.NAME))
			return 500;
		else if(s.equalsIgnoreCase(Signature.CLASSNAME))
			return 100;
		else if(s.equalsIgnoreCase(Signature.PRIORITY))
			return 70;
		
		// TCP
		else if(s.equalsIgnoreCase(TCPheader.DSTPORT))
			return 70;
		else if(s.equalsIgnoreCase(TCPheader.SRCPORT))
			return 70;
		else if(s.equalsIgnoreCase(TCPheader.SEQ))
			return 70;
		else if(s.equalsIgnoreCase(TCPheader.ACK))
			return 70;
		else if(s.equalsIgnoreCase(TCPheader.OFF))
			return 70;
		
		// udp
		else if(s.equalsIgnoreCase(UDPheader.DSTPORT))
			return 70;
		else if(s.equalsIgnoreCase(UDPheader.SRCPORT))
			return 70;
		
		return 10;
	}
	
	/**
	 * getName() return the name of the table field. Used for example in table columns name.
	 * @param s	string of the field.
	 * @return	name of the field
	 */
	public static String getName(String s) {
		if(s.equalsIgnoreCase(Event.CID))
			return "CID";
		else if(s.equalsIgnoreCase(Event.SID))
			return "SID";
		else if(s.equalsIgnoreCase(Event.SIGNATUREID))
			return "SIGID";
		else if(s.equalsIgnoreCase(Event.TIMESTAMP))
			return "TIME";
		else if(s.equalsIgnoreCase(ICMPheader.TYPE))
			return "ICMPTYPE";
		else if(s.equalsIgnoreCase(ICMPheader.CODE))
			return "ICMPCODE";
		else if(s.equalsIgnoreCase(IPheader.DST))
			return "IP DST";
		else if(s.equalsIgnoreCase(IPheader.SRC))
			return "IP SRC";
		else if(s.equalsIgnoreCase(IPheader.ID))
			return "IP ID";
		else if(s.equalsIgnoreCase(IPheader.TTL))
			return "TTL";
		else if(s.equalsIgnoreCase(IPheader.PROTO))
			return "PROTO";
		
		else if(s.equalsIgnoreCase(Packet.DSTPORT))
			return "PORT DST";
		else if(s.equalsIgnoreCase(Packet.SRCPORT))
			return "PORT SRC";
		else if(s.equalsIgnoreCase(Packet.PROTOCOL))
			return "PROTO";
		
		else if(s.equalsIgnoreCase(Reference.TAG))
			return "REF TAG";
		else if(s.equalsIgnoreCase(Reference.NAME))
			return "REF NAME";
		
		else if(s.equalsIgnoreCase(Sensor.DETAIL))
			return "SENSOR DETAIL";
		else if(s.equalsIgnoreCase(Sensor.DEVINTERFACE))
			return "SENSOR INTERF";
		else if(s.equalsIgnoreCase(Sensor.ENCODING))
			return "SENSOR ENCODING";
		else if(s.equalsIgnoreCase(Sensor.FILTER))
			return "SENSOR FILTER";
		else if(s.equalsIgnoreCase(Sensor.HOSTNAME))
			return "HOSTNAME";
		
		else if(s.equalsIgnoreCase(Signature.NAME))
			return "SIGNATURE";
		else if(s.equalsIgnoreCase(Signature.CLASSNAME))
			return "SIGN CLASS";
		else if(s.equalsIgnoreCase(Signature.PRIORITY))
			return "SIGN PRIO";
		
		else if(s.equalsIgnoreCase(TCPheader.DSTPORT))
			return "TCPPORT DST";
		else if(s.equalsIgnoreCase(TCPheader.SRCPORT))
			return "TCPPORT SRC";
		else if(s.equalsIgnoreCase(TCPheader.SEQ))
			return "SEQ";
		else if(s.equalsIgnoreCase(TCPheader.ACK))
			return "ACK";
		else if(s.equalsIgnoreCase(TCPheader.OFF))
			return "OFF";
		
		else if(s.equalsIgnoreCase(UDPheader.DSTPORT))
			return "UDPPORT DST";
		else if(s.equalsIgnoreCase(UDPheader.SRCPORT))
			return "UDPPORT SRC";
		
		return "--";
	}
	
}
