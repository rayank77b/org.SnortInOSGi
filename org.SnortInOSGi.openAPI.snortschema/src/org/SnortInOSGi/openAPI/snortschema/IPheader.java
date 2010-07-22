/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class IPheader is a map for table iphdr in snort main database.
 * Includes table opt as a list.
 */
@XmlRootElement(name = "IPheader")
public class IPheader extends Data{
	/**
	 * DST,SRC,ID,TTL,PROTO declare how the fields should named in db analyzer database 
	 */
	public static String DST = "iphdrdst";
	public static String SRC = "iphdrsrc";
	public static String ID  = "iphdrid";
	public static String TTL = "iphdrttl";
	public static String PROTO = "iphdrproto";
	
	/**
	 * CDST,CSRC,CID,CTTL,CPROTO declare the strings for create table sql-statement
	 */
	public static String CDST = "iphdrdst  INT      UNSIGNED NOT NULL";
	public static String CSRC = "iphdrsrc  INT      UNSIGNED NOT NULL";
	public static String CID  = "iphdrid   SMALLINT UNSIGNED";
	public static String CTTL = "iphdrttl  TINYINT  UNSIGNED";
	public static String CPROTO = "iphdrproto TINYINT  UNSIGNED NOT NULL";

	/**
	 * some constants for upper layer protocols
	 */
	public static int ICMP = 1;
	public static int TCP = 6;
	public static int IP = 4;
	public static int UDP = 17;
	public static int RESERVED = 255;
	
	/**
	 * necessary fields of table iphdr
	 */
	public long src;    // ip source address
	public long dst;    // ip destination address
	public int ver;     // version
	public int hlen;    // header length
	public int tos;     // TOS
	public int len;     // data length 
	public int id;      // packet ID number
	public int flags;   // flags (fragment)
	public int off;     // offset
	public int ttl;     // time to live
	public int proto;   // nex header protocol
	public int csum;    // checksum
           
	public List<Option> opts;  
	
	public IPheader() {	
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(DST)) {
			sql1 = DST;
			sql2 = "'" +this.dst+"'";
		} else if( value.equalsIgnoreCase(SRC)) {
			sql1 = SRC;
			sql2 = "'" +this.src+"'";
		} else if( value.equalsIgnoreCase(ID)) {
			sql1 = ID;
			sql2 = "'" +this.id+"'";
		} else if( value.equalsIgnoreCase(TTL)) {
			sql1 = TTL;
			sql2 = "'" +this.ttl+"'";
		} else if( value.equalsIgnoreCase(PROTO)) {
			sql1 = PROTO;
			sql2 = "'" +this.proto+"'";
		} else {
			throw new DataException("ERROR on IPheader getSQLValues");
		}
		String []tmp = {sql1,sql2};
		return tmp;
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLCreateValue(java.lang.String)
	 */
	@Override
	public String getSQLCreateValue(String value) throws DataException {
		String sql1;
		// get all event field
		if( value.equalsIgnoreCase(DST)) {
			sql1 = CDST;
		} else if( value.equalsIgnoreCase(SRC)) {
			sql1 = CSRC;
		} else if( value.equalsIgnoreCase(ID)) {
			sql1 = CID;
		} else if( value.equalsIgnoreCase(TTL)) {
			sql1 = CTTL;
		} else if( value.equalsIgnoreCase(PROTO)) {
			sql1 = CPROTO;
		} else {
			throw new DataException("ERROR on IPheader getSQLValues");
		}
		return sql1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IPheader [csum=" + csum + ", "
				+ ", dst=" + dst + ", flags=" + flags + ", hlen=" + hlen
				+ ", id=" + id + ", len=" + len + ", off=" + off + ", opts="
				+ opts + ", proto=" + proto + ", src=" + src + ", tos=" + tos
				+ ", ttl=" + ttl + ", ver=" + ver + "]";
	}
}
