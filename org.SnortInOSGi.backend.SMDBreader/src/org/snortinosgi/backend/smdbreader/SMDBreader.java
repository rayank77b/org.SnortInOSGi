/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.smdbreader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
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
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 * Implementation of the ISMDBreader. Some __getXXX() functions are private and 
 * used for reduce redunancy. Or are not public open.
 */
public class SMDBreader implements ISMDBreader {
	
	DBconnector databaseconnector;
	/**
	 * SMDBreader constructor. Create the database connection with DBconnector as snort user! Not root access.
	 * Snort user should have a read only access to snort main database.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public SMDBreader() throws ClassNotFoundException, SQLException {
		databaseconnector = new DBconnector(false, "localhost");
		databaseconnector.connect();
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getAlert(long, int)
	 */
	@Override
	public String getAlert(long cid, int sid) {
		Alert alert = new Alert();
		Event ev =  __getEvent(cid,sid);
		if(ev==null)
			return null;
		alert.event = ev;
		
		IPheader ip = __getIPheader(cid, sid);
		if(ip==null)
			return null;
		alert.iphdr = ip;
		
		Signature sig = __getSignature(alert.event.signature);
		if(sig==null)
			return null;
		alert.signature = sig;
		

		try {
			return alert.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getAlerts(long, long, int)
	 */
	@Override
	public List<String> getAlerts(long fromcid, long tocid, int sid) {
		List<String> alerts = new ArrayList<String>();
		
		for(long cid=fromcid; cid<=tocid; cid++) {
			String alert = getAlert(cid, sid);
			if(alert!=null)
				alerts.add(alert);
		}

		return alerts;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getAlertsTime(long, long, int)
	 */
	@Override
	public List<String> getAlertsTime(long fromtime, long totime, int sid) {
		long fromcid = getFirstCidOfTime(fromtime, sid);
		long tocid   = getLastCidOfTime(totime, sid);
		
		return getAlerts(fromcid, tocid, sid);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getCountCID(long, long, int)
	 */
	@Override
	public int getCountCID(long fromcid, long tocid, int sid) {
		String sql="SELECT COUNT(*) FROM event WHERE cid>='"+fromcid+"' AND cid<='"+tocid+"' and sid='"+sid+"'";
		//System.out.println(sql);
		int nr=-1;
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) 
				nr = rs.getInt(1);
			
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return nr;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getCountCID(long, long, int, int)
	 */
	@Override
	public int getCountCID(long fromcid, long tocid, int sid, int protocol) {
		String sql="SELECT COUNT(cid) FROM iphdr WHERE cid>='"+fromcid+"' " +
		"AND cid<='"+tocid+"' " +
		"AND sid='"+sid+"' " +
		"AND iphdr.ip_proto='"+protocol+"'";
		//System.out.println(sql);
		int nr=-1;
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) 
				nr = rs.getInt(1);
			
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return nr;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getCountTime(long, long, int)
	 */
	@Override
	public int getCountTime(long fromtime, long totime, int sid) {
		Timestamp from = new Timestamp(fromtime);
		Timestamp to   = new Timestamp(totime);
		String sql="SELECT COUNT(*) FROM event WHERE timestamp>='"+from.toString()+"' AND timestamp<='"+to.toString()+"' and sid='"+sid+"'";
		//System.out.println(sql);
		int nr=-1;
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) 
				nr = rs.getInt(1);
			
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return nr;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getCountTime(long, long, int, int)
	 */
	@Override
	public int getCountTime(long fromtime, long totime, int sid, int protocol) {
		long fromcid=getFirstCidOfTime(fromtime, sid);
		long tocid=getLastCidOfTime(totime, sid);

		return getCountCID(fromcid, tocid, sid, protocol);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getPayload(long, int)
	 */
	@Override
	public String getPayload(long cid, int sid) {
		String sql="SELECT data_payload FROM data WHERE cid='"+cid+"' AND sid='"+sid+"'";
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			String payload=null;
			while ( rs.next() ) {
				payload = rs.getString("data_payload");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return payload;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getEvent(long, int)
	 */
	@Override
	public String getEvent(long cid, int sid) {
		Event event = __getEvent(cid, sid);
		try {
			if (event!=null)
				return event.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * get the event table information for looking cid as private function
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return Event information as an Event object
	 */
	
	private Event __getEvent(long cid, int sid) {
		Event event = null;

		String sql="SELECT * FROM event WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println(">>getEvent: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			// get the first event found
			while ( rs.next() ) {
				event = new Event();
				event.cid = cid;
				event.sid = sid;
				event.signature = rs.getInt("signature");
				event.timestamp = rs.getTimestamp("timestamp").getTime();
				
				Statement st = rs.getStatement();
				rs.close();
				st.close();
				return event;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getFirstCidOfTime(long, int)
	 */
	@Override
	public long getFirstCidOfTime(long time, int sid) {
		Timestamp t = new Timestamp(time);
		String sql="SELECT cid FROM event WHERE timestamp>='"+t.toString()+"' AND sid='"+sid+"' limit 1";
		//System.out.println(">>getIPheader: "+sql);
		int ret=-1;
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				ret = rs.getInt("cid");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getFirstTimeOfCid(long, int)
	 */
	@Override
	public long getFirstTimeOfCid(long cid, int sid) {
		String sql="SELECT timestamp FROM event WHERE cid='"+cid+"' AND sid='"+sid+"'";
		
		Timestamp timestamp=null;
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				timestamp = rs.getTimestamp("timestamp");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return timestamp.getTime();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getICMPheader(long, int)
	 */
	@Override
	public String getICMPheader(long cid, int sid) {
		ICMPheader icmp = __getICMPheader(cid, sid);
		try {
			if(icmp!=null)
				return icmp.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get the ICMP header data of a alert/log as private function
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return ICMP information as an ICMPheader object
	 */
	
	private ICMPheader __getICMPheader(long cid, int sid) {
		ICMPheader icmp=null;
		
		String sql="SELECT * FROM icmphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println(">>getIPheader: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				icmp = new ICMPheader();
				icmp.code = rs.getInt("icmp_code");
				icmp.csum = rs.getInt("icmp_csum");
				icmp.id = rs.getInt("icmp_id");
				icmp.seq = rs.getInt("icmp_seq");
				icmp.type = rs.getInt("icmp_type");	
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return icmp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getIPheader(long, int)
	 */
	@Override
	public String getIPheader(long cid, int sid) {
		IPheader ip = __getIPheader(cid, sid);
		try {
			if (ip!=null)
				return ip.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * get the IP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return IP header information as an IPheader object
	 */
	
	private IPheader __getIPheader(long cid, int sid) {
		IPheader ipheader = null;

		String sql="SELECT * FROM iphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println(">>getIPheader: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				ipheader = new IPheader();
				ipheader.src = rs.getLong("ip_src");
				ipheader.dst = rs.getLong("ip_dst");
				ipheader.ver = rs.getInt("ip_ver");
				ipheader.hlen = rs.getInt("ip_hlen");
				ipheader.tos = rs.getInt("ip_tos");
				ipheader.len = rs.getInt("ip_len");
				ipheader.id = rs.getInt("ip_id");
				ipheader.flags = rs.getInt("ip_flags");
				ipheader.off = rs.getInt("ip_off");
				ipheader.off = rs.getInt("ip_off");
				ipheader.ttl = rs.getInt("ip_ttl");
				ipheader.proto = rs.getInt("ip_proto");
				ipheader.csum = rs.getInt("ip_csum");
				Option op = __getOption(cid, sid, 0);
				if(op!=null) {
					ipheader.opts = new ArrayList<Option>();
					ipheader.opts.add(op);
					ipheader.opts.add(__getOption(cid, sid, 1));
					ipheader.opts.add(__getOption(cid, sid, 2));
				}
				Statement st = rs.getStatement();
				rs.close();
				st.close();
				return ipheader;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getLastCid(int)
	 */
	@Override
	public long getLastCid(int sid) {
		String sql="SELECT last_cid FROM sensor WHERE sid='"+sid+"'";
		//System.out.println(">>getEvent: "+sql);
		
		long last_cid=0;
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				last_cid = rs.getLong("last_cid");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return last_cid;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getLastCidOfTime(long, int)
	 */
	@Override
	public long getLastCidOfTime(long time, int sid) {
		long cid = getFirstCidOfTime(time, sid);
		if(cid > 0 )
			return cid;
		return getLastCid(sid);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getLatestAlerts(int, int)
	 */
	@Override
	public List<String> getLatestAlerts(int nr, int sid) {
		long lastCid = getLastCid(sid);
		
		// maximal only 100 alerts
		if(nr > 100)
			nr = 100;
		
		int from = (int)lastCid - nr+1;
		
		return getAlerts(from, (int)lastCid, sid);
	}
	/**
	 * get Option information for a certain alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return Option information as an Option object
	 */

	// helpfunction
	private Option __getOption(long cid, int sid, int optid) {
		String sql="SELECT * FROM opt WHERE cid='"+cid+"' AND sid='"+sid+"' AND optid='"+optid+"'";
		Option op=null;
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);

			while ( rs.next() ) {
				op = new Option();
				op.proto = rs.getInt("opt_proto");
				op.code = rs.getInt("opt_code");
				op.len = rs.getInt("opt_len");
				op.data = rs.getString("opt_data");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return op;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getPacket(long, int)
	 */
	@Override
	public String getPacket(long cid, int sid) {
		Packet packet = new Packet();
		Event ev = __getEvent(cid,sid);
		packet.event = ev;
		
		IPheader ip = __getIPheader(cid, sid);
		packet.iphdr = ip;
		
		if(ip!=null) {
			if(ip.proto == IPheader.ICMP) {
				packet.icmphdr = __getICMPheader(cid, sid);
			} else if (ip.proto == IPheader.TCP) {
				packet.tcphdr = __getTCPheader(cid, sid);
			} else if (ip.proto == IPheader.UDP) {
				packet.udphdr = __getUDPheader(cid, sid);
			}
		}

		try {
			return packet.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getPackets(long, long, int)
	 */
	@Override
	public List<String> getPackets(long fromcid, long tocid, int sid) {
		List<String> packets = new ArrayList<String>();
		for(long cid=fromcid; cid<=tocid; cid++) {
			Packet p = (Packet) Data.getFromXML(getPacket(cid, sid), Packet.class);
			if(p.event!=null) {
				try {
					packets.add(p.toXML());
				} catch (JAXBException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return packets;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getPacketsTime(long, long, int)
	 */
	@Override
	public List<String> getPacketsTime(long fromtime, long totime, int sid) {
		long fromcid=getFirstCidOfTime(fromtime, sid);
		long tocid=getLastCidOfTime(totime, sid);
		
		//System.out.println("f: "+fromcid+"   t: "+tocid);
		return getPackets(fromcid, tocid, sid);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getReference(int)
	 */
	@Override
	public List<String> getReference(int sigId) {
		String sql="SELECT ref_tag, ref_system_name FROM sig_reference " +
		"INNER JOIN reference ON sig_reference.ref_id=reference.ref_id " +
		"INNER JOIN reference_system ON reference.ref_system_id=reference_system.ref_system_id  " +
		"WHERE sig_reference.sig_id='"+sigId+"'";
		
		List<String> refs = new LinkedList<String>();
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
		
			while ( rs.next() ) {
				Reference ref = new Reference();
				ref.tag = rs.getString("ref_tag");
				ref.name = rs.getString("ref_system_name");
				try {
					refs.add(ref.toXML());
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return refs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getSensor(int)
	 */
	@Override
	public String getSensor(int sid) {
		String sql="SELECT hostname, interface, filter, last_cid, detail_text, encoding_text " +
		"FROM sensor " +
		"INNER JOIN detail ON sensor.detail=detail.detail_type " +
		"INNER JOIN encoding ON sensor.encoding=encoding.encoding_type " +
		"WHERE sid='"+sid+"'";
		//System.out.println(">>getEvent: "+sql);
		
		Sensor sensor = null;
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				sensor = new Sensor();
				sensor.detail_text = rs.getString("detail_text");
				sensor.dev_interface = rs.getString("interface");
				sensor.encoding_text = rs.getString("encoding_text");
				sensor.hostname = rs.getString("hostname");
				sensor.filter = rs.getString("filter");
				sensor.last_cid = rs.getLong("last_cid");
			}
			try {
				Statement st = rs.getStatement();
				rs.close();
				st.close();
				return sensor.toXML();
			} catch (JAXBException e) {
				e.printStackTrace();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getSensorList()
	 */
	@Override
	public List<String> getSensorList() {
		String sql="SELECT sid FROM sensor";
		
		List<String> sensors = new ArrayList<String>();
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				int sid = rs.getInt("sid");
				sensors.add(getSensor(sid));
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return sensors;
		} catch (SQLException e) {
			e.printStackTrace();
			return sensors;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getSignature(int)
	 */
	@Override
	public String getSignature(int sigId) {
		Signature signature = __getSignature(sigId);
		try {
			if (signature!=null)
				return signature.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * get the signature info 
	 * @param sigId	the signature ID
	 * @return 		return signature information as Signature object
	 */
	
	private Signature __getSignature(int sigId) {
		Signature signature=null;
		
		String sql="SELECT * FROM signature WHERE sig_id='"+sigId+"'";
		//System.out.println(">>getIPheader: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				signature = new Signature();
				signature.id = sigId;
				signature.class_id = rs.getInt("sig_class_id");
				signature.priority = rs.getInt("sig_priority");
				signature.rev = rs.getInt("sig_rev");
				signature.sid = rs.getInt("sig_sid");
				signature.name = rs.getString("sig_name");
				
				signature.class_name = getSignatureClassName(sigId);
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return signature;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getSignatureClassName(int)
	 */
	@Override
	public String getSignatureClassName(int sigClassId) {
		String sql="SELECT sig_class_name FROM sig_class WHERE sig_class_id='"+sigClassId+"'";
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			String ret="";
			while ( rs.next() ) {
				ret = rs.getString("sig_class_name");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getTCPheader(long, int)
	 */
	@Override
	public String getTCPheader(long cid, int sid) {
		TCPheader tcp = __getTCPheader(cid, sid);
		try {
			if (tcp!=null)
				return tcp.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * get the TCP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return TCP header information as TCPheader object
	 */
	
	private TCPheader __getTCPheader(long cid, int sid) {
		TCPheader tcp=null;
		
		String sql="SELECT * FROM tcphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println(">>getIPheader: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				tcp = new TCPheader();
				tcp.sport = rs.getInt("tcp_sport");
				tcp.dport = rs.getInt("tcp_dport");
				tcp.seq = rs.getLong("tcp_seq");
				tcp.ack = rs.getLong("tcp_ack");
				tcp.off = rs.getInt("tcp_off");
				tcp.res = rs.getInt("tcp_res");
				tcp.flags = rs.getInt("tcp_flags");
				tcp.win = rs.getInt("tcp_win");
				tcp.csum = rs.getInt("tcp_csum");
				tcp.urp = rs.getInt("tcp_urp");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return tcp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getUDPheader(long, int)
	 */
	@Override
	public String getUDPheader(long cid, int sid) {
		UDPheader udp = __getUDPheader(cid, sid);
		
		try {
			if (udp!=null)
				return udp.toXML();
		} catch (JAXBException e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * get the UDP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return UDP header information as UDPheader object
	 */

	private UDPheader __getUDPheader(long cid, int sid) {
		UDPheader udp=null;
		
		String sql="SELECT * FROM udphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println(">>getIPheader: "+sql);
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				udp = new UDPheader();
				udp.dport = rs.getInt("udp_dport");
				udp.sport = rs.getInt("udp_sport");
				udp.len = rs.getInt("udp_len");
				udp.csum = rs.getInt("udp_csum");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return udp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader#getSignaturePriorityCount(int, long, long, int)
	 */
	@Override
	public int getSignaturePriorityCount(int priority, long fromcid,
			long tocid, int sid) {
		String sql = "SELECT count(event.cid) FROM event " +
				"LEFT JOIN signature ON event.signature=signature.sig_id " +
				"WHERE cid>='"+fromcid+"' and cid<='"+tocid+"' and signature.sig_priority='"+priority+"'";
			//System.out.println(sql);
			int nr=-1;
			
			try {
				ResultSet rs = databaseconnector.executetSQL(sql);
				
				while ( rs.next() ) 
					nr = rs.getInt(1);
				
				Statement st = rs.getStatement();
				rs.close();
				st.close();
				return nr;
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
	}
}
