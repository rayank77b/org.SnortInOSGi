/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snortinosgi.frontend.snortinosgigui.XMLModels.CommandViewXML;
import org.snortinosgi.frontend.snortinosgigui.XMLModels.SensorXML;

/**
 * Sensor describe a list of commands. This class is used in xml-mapping.
 */
public class Sensor {
	public String ipaddr;
	public int sid;
	public List<CommandView> commands;
	public Sensors parent;
	
	public Sensor(SensorXML s, Sensors p) {
		this.ipaddr = s.ipaddr;
		this.sid = s.sid;
		this.parent = p;
		commands = new ArrayList<CommandView>();

		for ( Iterator<CommandViewXML> i = s.getCommands().iterator(); i.hasNext(); )
		{
			CommandViewXML c = (CommandViewXML)i.next();
			this.commands.add(new CommandView(c, this));
		}
	}
	
	public void setParent(Sensors parent) {
		this.parent = parent;
	}
	public Sensors getParent() {
		return parent;
	}
	
	public void addChild(CommandView child) {
		commands.add(child);
		child.setParent(this);
	}
	
	public void removeChild(CommandView child) {
		commands.remove(child);
		child.setParent(null);
	}
	
	public CommandView[] getChildren() {
		return (CommandView[]) commands.toArray(new CommandView[commands.size()]);
	}
	public boolean hasChildren() {
		return commands.size()>0;
	}
	
	public String toString() {
		return ipaddr+":["+sid+"]";
	}
}
