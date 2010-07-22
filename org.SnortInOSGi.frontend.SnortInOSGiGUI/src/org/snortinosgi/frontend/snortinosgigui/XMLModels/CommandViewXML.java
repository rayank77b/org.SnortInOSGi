/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.XMLModels;


/**
 *
 */
public class CommandViewXML {
	public String CommandName;
	public String ViewID;
	
	/**
	 * 
	 */
	public CommandViewXML() {
	}
	
	/**
	 * @param commandName
	 * @param viewID
	 */
	public CommandViewXML(String commandName, String viewID) {
		CommandName = commandName;
		ViewID = viewID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommandViewXML [CommandName=" + CommandName + ", ViewID="
				+ ViewID + "]";
	}
}
