/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.XMLModels;

import java.util.LinkedList;
import java.util.List;


public class SensorXML {
	public String ipaddr;
	public int sid;
	private List<CommandViewXML> commands;
		
	/**
	 * 
	 */
	public SensorXML() {
		 commands = new LinkedList<CommandViewXML>();
	}
	
	/**
	 * @param ipaddr
	 * @param sid
	 * @param commands
	 */
	public SensorXML(String ipaddr, int sid, List<CommandViewXML> commands) {
		this.ipaddr = ipaddr;
		this.sid = sid;
		this.commands = commands;
	}

	public void add(CommandViewXML c) {
		commands.add(c);
	}
	
	public void remove(CommandViewXML c) {
		commands.remove(c);
	}
	
	/**
	 * @return the commands
	 */
	public List<CommandViewXML> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(List<CommandViewXML> commands) {
		this.commands = commands;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SensorXML [commands=" + commands + ", ipaddr=" + ipaddr
				+ ", sid=" + sid + "]";
	}
}