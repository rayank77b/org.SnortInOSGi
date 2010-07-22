/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Packet is a set of other objects: Event, IPheader, TCPheader, UDPheader and ICMPheader.
 * It should represent the packet himself with all data. But nothing about Alert.
 */
@XmlRootElement(name = "Packet")
public class Packet extends Data{
	/**
	 * PROTOCOL,DSTPORT,SRCPORT are additional attributes for upper layer information 
	 */
	public static String PROTOCOL = "layer4proto";
	public static String DSTPORT = "layer4dstport";
	public static String SRCPORT = "layer4srcport";
	
	/**
	 * CPROTOCOL,CDSTPORT,CSRCPORT are additional datas for sql-creation for upper layer
	 */
	public static String CPROTOCOL = "layer4proto   VARCHAR(10)";
	public static String CDSTPORT  = "layer4dstport SMALLINT UNSIGNED NOT NULL";
	public static String CSRCPORT  = "layer4srcport SMALLINT UNSIGNED NOT NULL";
	
	/**
	 * necessary to stored objects in Packet
	 */
	public Event event;
	public IPheader iphdr;
	public TCPheader tcphdr;
	public UDPheader udphdr;
	public ICMPheader icmphdr;
	
	public Packet() {
		event=null;
		iphdr=null;
		tcphdr=null;
		udphdr=null;
		icmphdr=null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Packet [event=" + event + ", icmphdr=" + icmphdr + ", iphdr="
				+ iphdr + ", tcphdr=" + tcphdr + ", udphdr=" + udphdr + "]";
	}

	/**
	 * get Event, IP|TCP|UDP|ICMPheader objects SQLValues
	 * @param value name of the attribute
	 * @return return a string array of fieldname and value
	 * @throws DataException
	 */
	public String[] getLayer4SQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(PROTOCOL)) {
			sql1 =  PROTOCOL;
			if( this.iphdr.proto==IPheader.TCP)
				sql2 = "'tcp'";
			else if( this.iphdr.proto==IPheader.UDP)
				sql2 = "'udp'";
			else if( this.iphdr.proto==IPheader.ICMP)
				sql2 = "'icmp'";
			else if( this.iphdr.proto==IPheader.IP)
				sql2 = "'ip'";
			else 
				sql2 = "'unknown'";
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = DSTPORT;
			if( this.iphdr.proto==IPheader.TCP && this.tcphdr!=null)
				sql2 = "'" +this.tcphdr.dport+"'";
			else if( this.iphdr.proto==IPheader.UDP && this.udphdr!=null)
				sql2 = "'" +this.udphdr.dport+"'";
			else 
				sql2 = "'0'";
		} else if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 = SRCPORT;
			if( this.iphdr.proto==IPheader.TCP && this.tcphdr!=null)
				sql2 = "'" +this.tcphdr.sport+"'";
			else if( this.iphdr.proto==IPheader.UDP && this.udphdr!=null)
				sql2 = "'" +this.udphdr.sport+"'";
			else 
				sql2 = "'0'";
		} else {
			throw new DataException("ERROR on IPheader getSQLValues");
		}
		String []tmp = {sql1,sql2};
		//System.out.println("value: "+value+"   sql1: "+sql1+"   sql2: "+sql2);
		return tmp;
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String []tmp;
		
		// test for event
		try {
			tmp = event.getSQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for iphdr
		try {
			tmp = iphdr.getSQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for layer 4 field
		try {
			tmp = getLayer4SQLValues(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for tcp
		if(tcphdr!=null) {
			try {
				tmp = tcphdr.getSQLValues(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		} 
		
		// test for udp
		if(udphdr!=null) {
			try {
				tmp = udphdr.getSQLValues(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		}
		
		// test for icmp
		if(icmphdr!=null) {
			try {
				tmp = icmphdr.getSQLValues(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		}

		if(isTCPUDPICMPField(value))  {
			String sql1, sql2;
			sql1=value;
			sql2="'0'";
			String []tmp2={sql1, sql2};
			return tmp2;
		}
		
		throw new DataException("ERROR on Packet getSQLValues value:["+value+"]");
	}

	/**
	 * test if the request value is a part of TCP/UDP/ICMPheader
	 * @param value name for the object attribute
	 * @return return true ist the value a part of TCP/UDP/ICMPheader
	 */
	public static boolean isTCPUDPICMPField(String value) {
		if( value.equalsIgnoreCase(ICMPheader.CODE)) {
			return true;
		}
		if( value.equalsIgnoreCase(ICMPheader.TYPE)) {
			return true;
		}
		if( value.equalsIgnoreCase(UDPheader.DSTPORT)) {
			return true;
		}
		if( value.equalsIgnoreCase(UDPheader.SRCPORT)) {
			return true;
		}
		if( value.equalsIgnoreCase(TCPheader.ACK)) {
			return true;
		}
		if( value.equalsIgnoreCase(TCPheader.DSTPORT)) {
			return true;
		}
		if( value.equalsIgnoreCase(TCPheader.OFF)) {
			return true;
		}
		if( value.equalsIgnoreCase(TCPheader.SEQ)) {
			return true;
		}
		if( value.equalsIgnoreCase(TCPheader.SRCPORT)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param value
	 * @return
	 * @throws DataException 
	 */
	private String getLayer4SQLCreateValue(String value) throws DataException {
		String sql1;
		// get all event field
		if( value.equalsIgnoreCase(PROTOCOL)) {
			sql1 = CPROTOCOL;
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = CDSTPORT;
		} else if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 = CSRCPORT;
		} else {
			throw new DataException("ERROR on IPheader getSQLValues");
		}
		return sql1;
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLCreateValue(java.lang.String)
	 */
	@Override
	public String getSQLCreateValue(String value) throws DataException {
		String tmp;
	
		// test for event
		try {
			tmp = event.getSQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for iphdr
		try {
			tmp = iphdr.getSQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for layer 4 field
		try {
			tmp = getLayer4SQLCreateValue(value);
			if( tmp!=null)
				return tmp;
		} catch (DataException e) {
		}
		
		// test for tcp
		if(tcphdr!=null) {
			try {
				tmp = tcphdr.getSQLCreateValue(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		}
		
		// test for udp
		if(udphdr!=null) {
			try {
				tmp = udphdr.getSQLCreateValue(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		}
		
		// test for icmp
		if(icmphdr!=null) {
			try {
				tmp = icmphdr.getSQLCreateValue(value);
				if( tmp!=null)
					return tmp;
			} catch (DataException e) {
			}
		}

		throw new DataException("ERROR on Packet getSQLValues");
	}
}
