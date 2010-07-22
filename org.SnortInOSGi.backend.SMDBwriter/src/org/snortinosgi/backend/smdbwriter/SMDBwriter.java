/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.smdbwriter;

import java.sql.SQLException;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 * implementation ISMDBwriter for providing writeable functionality to snort main database via DBconnector.
 */
public class SMDBwriter implements ISMDBwriter {

	DBconnector databaseconnector;
	/**
	 * Constructor for SMDBwriter. create the database connection. Set a root access to database.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public SMDBwriter() throws ClassNotFoundException, SQLException {
		databaseconnector = new DBconnector(true, "localhost");
		databaseconnector.connect();
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#delelteICMPheader(long, int)
	 */
	@Override
	public void deleteICMPheader(long cid, int sid) {
		String sql = "DELETE FROM icmphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		try {
			databaseconnector.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			//System.out.println("ERROR on deleting: sql("+sql+")");
		}	
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#delelteIPheader(long, int)
	 */
	@Override
	public void deleteIPheader(long cid, int sid) {
		String sql = "DELETE FROM iphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		try {
			databaseconnector.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			//System.out.println("ERROR on deleting: sql("+sql+")");
		}

	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#delelteTCPheader(long, int)
	 */
	@Override
	public void deleteTCPheader(long cid, int sid) {
		String sql = "DELETE FROM tcphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		try {
			databaseconnector.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			//System.out.println("ERROR on deleting: sql("+sql+")");
		}

	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#delelteUDPheader(long, int)
	 */
	@Override
	public void deleteUDPheader(long cid, int sid) {
		String sql = "DELETE FROM udphdr WHERE cid='"+cid+"' AND sid='"+sid+"'";
		try {
			databaseconnector.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			//System.out.println("ERROR on deleting: sql("+sql+")");
		}

	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#deleteCID(long, int)
	 */
	@Override
	public void deleteCID(long cid, int sid) {
		deleteEvent(cid,sid);
		deleteIPheader(cid,sid);
		deleteICMPheader(cid,sid);
		deleteTCPheader(cid,sid);
		deleteUDPheader(cid,sid);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter#deleteEvent(long, int)
	 */
	@Override
	public void deleteEvent(long cid, int sid) {
		String sql = "DELETE FROM event WHERE cid='"+cid+"' AND sid='"+sid+"'";
		//System.out.println("deleteEvent: sql: "+sql);
		try {
			databaseconnector.executeCommand(sql);
			//System.out.println("Row is deleted successfully.");
		} catch (SQLException e) {
			System.out.println("ERROR on deleting: sql("+sql+")");
		}

	}
}
