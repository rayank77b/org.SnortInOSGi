/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.model;

import org.snortinosgi.frontend.snortinosgigui.XMLModels.CommandViewXML;

/**
 * CommandView is an object which describe a command. This class is used in xml-mapping.
 */
public class CommandView {
	public String CommandName;
	public String ViewID;
	public Sensor parent;
	
	public CommandView(CommandViewXML c, Sensor p) {
		this.CommandName = c.CommandName;
		this.ViewID = c.ViewID;
		this.parent = p;
	}

	public void setParent(Sensor parent) {
		this.parent = parent;
	}
	public Sensor getParent() {
		return parent;
	}
	
	public String toString() {
		return CommandName;
	}
}
