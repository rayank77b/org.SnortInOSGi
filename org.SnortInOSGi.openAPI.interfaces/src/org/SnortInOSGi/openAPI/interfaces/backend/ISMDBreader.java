/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.backend;

import java.util.List;

// TODO create a logger!!!

/**
 * ISMDBreader is an interface to provide a readonly access to snort main database.
 * The data will be stored in snortschema objects (Alert, Event, Packet, ...) 
 * and converted in xml strings. This xml strings can be transported throuhg network as 
 * simple strings. On receiver side the receiver must translate the xml strings in objects back.
 */
public interface ISMDBreader {
	
	/**
	 * get the first found cid for looking time
	 * @param time	time as long for searched cid
	 * @param sid	the sensor id
	 * @return 		first found cid 
	 */
	public long getFirstCidOfTime(long time, int sid);
	
	/**
	 * get the last found cid for looking time
	 * @param time	time as long for searched cid
	 * @param sid	the sensor id
	 * @return 		last found cid
	 */
	public long getLastCidOfTime(long time, int sid);
	
	/**
	 * get the first found time for looking cid
	 * @param cid	the looking cid
	 * @param sid	the sensor id
	 * @return 		first found time of cid as long
	 */
	public long getFirstTimeOfCid(long cid, int sid);
	
	/**
	 * get the number of alerts/logs between the time interval
	 * @param fromtime	start time of interval as long
	 * @param time	end time of interval as long
	 * @param sid	the sensor id
	 * @return 		numbers of found cids
	 */
	public int getCountTime(long fromtime, long totime, int sid);
	
	/**
	 * get the number of alerts/logs between the time interval for special protocol
	 * @param fromtime	start time of interval as long
	 * @param time	end time of interval as long
	 * @param sid	the sensor id
	 * @return 		numbers of found cids
	 */
	public int getCountTime(long fromtime, long totime, int sid, int protocol);

	/**
	 * get the number of alerts/logs between the cid interval
	 * @param fromcid	start cid of interval 
	 * @param time	end cid of interval
	 * @param sid	the sensor id
	 * @return 		numbers of found cids
	 */
	public int getCountCID(long fromcid, long tocid, int sid);
	
	/**
	 * get the number of alerts/logs between the cid interval for special protocol
	 * @param fromcid	start cid of interval 
	 * @param time	end cid of interval
	 * @param sid	the sensor id
	 * @return 		numbers of found cids
	 */
	public int getCountCID(long fromcid, long tocid, int sid, int protocol);
	
	// Sensor
	/**
	 * get information about sensor
	 * @param sid	the sensor id
	 * @return 		return a xml string for sensor information
	 */
	public String getSensor(int sid);

	/**
	 * get informations about all sensors
	 * @return 		return a list of xml strings for sensors information
	 */
	public List<String> getSensorList();
	
	/**
	 * get last cid number from sensor information table, ATTENTION: must not be the real last cid!!!
	 * @param sid	the sensor id
	 * @return 		return the last cid
	 */
	public long getLastCid(int sid);
	
	// Event
	/**
	 * get the event table information for looking cid
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of event information
	 */
	public String getEvent(long cid, int sid);
	
	// header
	/**
	 * get the data payload of a packet/alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of data (most hex encoded)
	 */
	public String getPayload(long cid, int sid);
	
	//public Option getOption(long cid, int sid, int optid);
	/**
	 * get the IP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of IP header
	 */
	public String getIPheader(long cid, int sid);

	/**
	 * get the ICMP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of ICMP header
	 */
	public String getICMPheader(long cid, int sid);
	
	/**
	 * get the TCP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of TCP header
	 */
	public String getTCPheader(long cid, int sid);
	
	/**
	 * get the UDP header data of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of UDP header
	 */
	public String getUDPheader(long cid, int sid);

	// Signature 
	/**
	 * get reference informations (url info) for a signature
	 * @param sigId	the signature ID
	 * @return 		return a list of xml strings of reference information
	 */
	public List<String> getReference(int sigId);
	
	/**
	 * get the signature class name (art of the attack)
	 * @param sigClassId	the signature class ID
	 * @return 		return a string of signature class name
	 */
	public String getSignatureClassName(int sigClassId);
	
	/**
	 * get the signature info 
	 * @param sigId	the signature ID
	 * @return 		return a xml string of signature information
	 */
	public String getSignature(int sigId);
	
	// Packet
	/**
	 * get the Packet information of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of Packet information
	 */
	public String getPacket(long cid, int sid);

	/**
	 * get the Packets information of a alert/log for an interval of cids
	 * @param fromcid   start cid of the interval
	 * @param tocid   end cid of the interval
	 * @param sid	the sensor id
	 * @return 		return a list of xml strings of Packets information
	 */
	public List<String> getPackets(long fromcid, long tocid, int sid);

	/**
	 * get the Packets information of a alert/log for an interval of time
	 * @param fromtime   start time of the interval
	 * @param totime   end time of the interval
	 * @param sid	the sensor id
	 * @return 		return a list of xml strings of Packets information
	 */
	public List<String> getPacketsTime(long fromtime, long totime, int sid);
	
	// Alert
	/**
	 * get the Alert information of a alert/log
	 * @param cid   cid of the alert/log
	 * @param sid	the sensor id
	 * @return 		return a xml string of Alert information
	 */
	public String getAlert(long cid, int sid);
	
	/**
	 * get nr number of lasts Alerts informations 
	 * @param nr    the number of last alerts
	 * @param sid	the sensor id
	 * @return 		return a list of xml strings of Alerts information
	 */
	public List<String> getLatestAlerts(int nr, int sid);

	/**
	 * get the Alerts information of a alert/log for an interval of cids
	 * @param fromcid   start cid of the interval
	 * @param tocid   end cid of the interval
	 * @param sid	the sensor id
	 * @return 		return a list of xml strings of Alerts information
	 */
	public List<String> getAlerts(long fromcid, long tocid, int sid);

	/**
	 * get the Alerts information of a alert/log for an interval of time
	 * @param fromtime   start time of the interval
	 * @param totime   end time of the interval
	 * @param sid	the sensor id
	 * @return 		return a list of xml strings of Alerts information
	 */
	public List<String> getAlertsTime(long fromtime, long totime, int sid);


	// Statistics
	/**
	 * get the number of of signatures which have certain priority in an interval of cids
	 * @param priority   the looking priority (high, middle or low)
	 * @param fromcid   start cid of the interval
	 * @param tocid   end cid of the interval
	 * @param sid	the sensor id
	 * @return 		return the number of found signatures wiht certain priority
	 */
	public int getSignaturePriorityCount(int priority, long fromcid, long tocid, int sid);
	
}
