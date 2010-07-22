/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.backend.smdbreader;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.snortinosgi.backend.smdbreader.SMDBreader;




public class testTimestamp {

	public static  void createDatasetTimeline() throws ClassNotFoundException, SQLException {
		

		SMDBreader reader = new SMDBreader();
			
		int sid = 1;	
		// row keys...
		String []series = {"TCP", "UDP", "ICMP" };		
		int []protocol = {IPheader.TCP, IPheader.UDP, IPheader.ICMP };

				
		long now = reader.getFirstTimeOfCid(reader.getLastCid(sid), sid);
				
		//System.out.println("now : " + new Timestamp(now)+ "    long: "+now);
			
		
				for(int hour=0; hour<6; hour++) {
							
					
					long fromcid = reader.getFirstCidOfTime((now-(hour+1)*1000*60*60), sid);
					long tocid = reader.getLastCidOfTime((now-hour*1000*60*60), sid);
					
					
					for(int proto=0; proto<3; proto++) {
						System.out.print("  from: " + new Timestamp((now-hour*1000*60*60))+"  fromcid: "+fromcid+" || to: " + new Timestamp((now-(hour+1)*1000*60*60))+"  tocid: "+tocid);
						float cnt = reader.getCountCID(fromcid, tocid, sid, protocol[proto]);
						System.out.println(" >> hour: "+hour+"   series: "+series[proto]+"   count: "+cnt);
						
					}
					 
				}
				
	}
	
	
	
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );

		
		try {
			Date dt = df.parse( "2008-05-19 16:58:08.0" );
			Timestamp t = new Timestamp(dt.getTime());
			System.out.println(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		try {
			createDatasetTimeline();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

}
