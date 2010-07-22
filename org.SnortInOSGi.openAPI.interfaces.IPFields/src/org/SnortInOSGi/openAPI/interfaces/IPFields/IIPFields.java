/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.IPFields;


/**
 *
 */
public interface IIPFields {
	/**
	 * get the number of count for a destanation and source ip range
	 * @param fromDstIP long begin of the destanation range
	 * @param toDstIP long end of the destanation range
	 * @param fromSrcIP long begin of the source range
	 * @param toSrcIP long end of the source range
	 * @param sid sensor id
	 * @return number of the count as long, zero if none. and negative on errors.
	 */
	public long getIPCount(long fromDstIP, long toDstIP, long fromSrcIP, long toSrcIP, int sid);
}
