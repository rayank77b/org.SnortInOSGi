/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.ipfields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.SnortInOSGi.openAPI.interfaces.IPFields.IIPFields;
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 *
 */
public class IPFields implements IIPFields {

	DBconnector databaseconnector;
	
	public IPFields() throws ClassNotFoundException, SQLException {
		databaseconnector = new DBconnector(false, "localhost");
		databaseconnector.connect();
	}
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.IPFields.IIPFields#getIPCount(long, long, long, long, int)
	 */
	@Override
	public long getIPCount(long fromDstIP, long toDstIP, long fromSrcIP,
			long toSrcIP, int sid) {
		String sql="SELECT COUNT(*) AS cnt FROM iphdr WHERE ip_src>='"+fromSrcIP+"' AND ip_src<='"+toSrcIP+"' " +
				" AND ip_dst>='"+fromDstIP+"' AND ip_dst<='"+toDstIP+"'  AND sid='"+sid+"'";
		long number=0;
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) 
				number = rs.getLong("cnt");
			
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return number;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
	}

}
