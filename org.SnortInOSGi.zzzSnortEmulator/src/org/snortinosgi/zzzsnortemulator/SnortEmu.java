/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.zzzsnortemulator;

import java.sql.SQLException;

import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 *
 */
public class SnortEmu extends Thread {

	public DBconnector dbc;
	public int fast;
	public boolean run=false;
	
	
	public SnortEmu(int fast) throws ClassNotFoundException, SQLException {
		
		dbc = new DBconnector(true, "localhost");
		dbc.connect();
		
		this.fast= fast;
		run=false;
		System.out.println("SnortEmu()");
		
	}
	
	public void run() {
		System.out.println("run()");
		
		int cid=10001;
		
		while(run) {

			String event = "INSERT INTO event(sid,cid, signature,timestamp) VALUES('1','"+cid+"', '51', '2010-05-03 15:54:34')";
			exec(event);
			//System.out.println(event);
			
			
			String iphdr = "INSERT INTO iphdr(sid,cid,ip_src,ip_dst,ip_ver,ip_hlen,ip_tos,ip_len,ip_id,ip_flags,ip_off,ip_ttl,ip_proto,ip_csum) " +
					"VALUES('1','"+cid+"','3257391626','2255234159','4','5','0','453','17980','0','0','55','6','4613')";
			exec(iphdr);
			//System.out.println(iphdr);
			
			String tcphdr = "INSERT INTO tcphdr(sid,cid,tcp_sport,tcp_dport,tcp_seq,tcp_ack,tcp_off,tcp_res,tcp_flags,tcp_win,tcp_csum,tcp_urp) " +
					"VALUES('1','"+cid+"','42498','80','1966360972','3536945545','8','0','24','17520','37997','0')";
			exec(tcphdr);
			cid++;
			
			try {
				Thread.sleep(fast);
			} catch (InterruptedException e) {
			}
		}
	}
	
	
	/*
	 * delete  from iphdr where cid>10000;
	 * delete  from tcphdr where cid>10000;
	 * delete  from event where cid>10000;
	 * 
	 * 
	 * 
	 * 
	 * */
	 
	public void exec(String sql) {
		try {
			dbc.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			System.out.println("sql errors.");
			run=false;
		}
	}
}
