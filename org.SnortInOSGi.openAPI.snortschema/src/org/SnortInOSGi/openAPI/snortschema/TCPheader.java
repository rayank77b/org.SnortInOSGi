/**
 * @author Andrej Frank, 2009,2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class TCPheader is a map for table tcphdr in snort main database.
 */
@XmlRootElement(name = "TCPheader")
public class TCPheader extends Data{
	/**
	 * SRCPORT,DSTPORT,SEQ,ACK,OFF declare how the fields should named in db analyzer database 
	 */
	public static String SRCPORT = "tcpsrcport";
	public static String DSTPORT = "tcpdstport";
	public static String SEQ 	= "tcpseq";
	public static String ACK 	= "tcpack";
	public static String OFF 	= "tcpoff";
	
	/**
	 * CSRCPORT,CDSTPORT,CSEQ,CACK,COFF declare the strings for create table sql-statement
	 */
	public static String CSRCPORT 	= "tcpsrcport  SMALLINT UNSIGNED NOT NULL";
	public static String CDSTPORT 	= "tcpdstport  SMALLINT UNSIGNED NOT NULL";
	public static String CSEQ 		= "tcpseq  INT      UNSIGNED";
	public static String CACK 		= "tcpack  INT      UNSIGNED";
	public static String COFF 		= "tcpoff  TINYINT  UNSIGNED";
	
	/**
	 * necessary attribtes of table tcphdr
	 */
	public int sport;   // source port
	public int dport;   // destination port
	public long seq;    // sequence number
	public long ack;    // acknowledgment number
	public int off;     // offset
	public int res;     // ??
	public int flags;   // flags (ack, seq, urg)
	public int win;     // window size
	public int csum;    // checksum
	public int urp;     // urgent pointer

	public TCPheader() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TCPheader [ack=" + ack + ", csum=" + csum + ", dport=" + dport
				+ ", flags=" + flags + ", off=" + off + ", res=" + res
				+ ", seq=" + seq + ", sport=" + sport + ", urp=" + urp
				+ ", win=" + win + "]";
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.snortschema.Data#getSQLValues(java.lang.String)
	 */
	@Override
	public String[] getSQLValues(String value)  throws DataException{
		String sql1, sql2;
		// get all event field
		if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 =  SRCPORT;
			sql2 = "'" +this.sport+"'";
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = DSTPORT;
			sql2 = "'" +this.dport+"'";
		} else if( value.equalsIgnoreCase(SEQ)) {
			sql1 = SEQ;
			sql2 = "'" +this.seq+"'";
		} else if( value.equalsIgnoreCase(ACK)) {
			sql1 = ACK;
			sql2 = "'" +this.ack+"'";
		} else if( value.equalsIgnoreCase(OFF)) {
			sql1 = OFF;
			sql2 = "'" +this.off+"'";
		} else {
			throw new DataException("ERROR on TCPheader getSQLValues");
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
		if( value.equalsIgnoreCase(SRCPORT)) {
			sql1 = CSRCPORT;
		} else if( value.equalsIgnoreCase(DSTPORT)) {
			sql1 = CDSTPORT;
		} else if( value.equalsIgnoreCase(SEQ)) {
			sql1 = CSEQ;
		} else if( value.equalsIgnoreCase(ACK)) {
			sql1 = CACK;
		} else if( value.equalsIgnoreCase(OFF)) {
			sql1 = COFF;
		} else {
			throw new DataException("ERROR on TCPheader getSQLCreateValue");
		}
		return sql1;
	}	
	
}
