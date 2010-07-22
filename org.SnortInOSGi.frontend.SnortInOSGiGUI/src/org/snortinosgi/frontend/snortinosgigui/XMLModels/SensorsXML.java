/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.XMLModels;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name = "org.SnortInOSGi.frontend.configuration")
public class SensorsXML {
	private List<SensorXML> sensor;
	
	/**
	 * 
	 */
	public SensorsXML() {
		sensor = new LinkedList<SensorXML>();
	}

	/**
	 * @param sensor
	 */
	public SensorsXML(List<SensorXML> sensor) {
		this.sensor = sensor;
	}

	public void add(SensorXML s) {
		sensor.add(s);
	}
	
	public void remove(SensorXML s) {
		sensor.remove(s);
	}

	/**
	 * @return the sensor
	 */
	public List<SensorXML> getSensor() {
		return sensor;
	}

	/**
	 * @param sensor the sensor to set
	 */
	public void setSensor(List<SensorXML> sensor) {
		this.sensor = sensor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SensorsXML [sensor=" + sensor + "]";
	}
	
	
}
