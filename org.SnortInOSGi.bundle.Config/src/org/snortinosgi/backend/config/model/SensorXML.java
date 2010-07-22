/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.config.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class SensorXML is a Object to creat a map for xml file of hosts.
 * Because here can exists severeal snort main databases.
 */
@XmlRootElement(name = "org.SnortInOSGi.bundle.Config.configuration")
public class SensorXML {
	private List<HostXML> hosts;

	public SensorXML() {
		hosts = new LinkedList<HostXML>();
	}
	
	/**
	 * add a host to the list
	 * @param host a filled host to add
	 */
	public void add(HostXML host) {
		hosts.add(host);
	}
	
	/**
	 * remove a host from the list
	 * @param host which should be deleted
	 */
	public void remove(HostXML host) {
		hosts.remove(host);
	}

	/**
	 * @return the hosts
	 */
	public List<HostXML> getHosts() {
		return hosts;
	}

	/**
	 * replace old list of hosts with a new list of hosts
	 * @param hosts the hosts to set
	 */
	public void setHosts(List<HostXML> hosts) {
		this.hosts = hosts;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SensorXML [hosts=" + hosts + "]";
	}
}
