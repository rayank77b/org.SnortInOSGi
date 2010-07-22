/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.backend;

/**
 * ISMDBwriter is an interface to provide write functionality (most to delete data in  database) to snort main database.
 * Currently it doesnt delete siganture, references and sensor data in snort main database.
 */
public interface ISMDBwriter {
	
	/**
	 * delete all data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteCID(long cid, int sid);
	
	/**
	 * delete event data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteEvent(long cid, int sid);

	/**
	 * delete IP header data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteIPheader(long cid, int sid);

	/**
	 * delete ICMP header data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteICMPheader(long cid, int sid);

	/**
	 * delete UDP header data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteUDPheader(long cid, int sid);

	/**
	 * delete TCP header data in database for certain cid
	 * @param cid	cid of alert/log
	 * @param sid	the sensor id
	 */
	public void deleteTCPheader(long cid, int sid);
	
	// signature should be not deleted. because don't take to much memory 
	// and is needed by other events
	//public void delelte(long cid, int sid);
}
