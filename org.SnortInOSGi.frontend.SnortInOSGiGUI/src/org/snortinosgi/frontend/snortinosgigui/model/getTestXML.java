/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.model;


/**
 *
 */
public class getTestXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sensors sensors = new Sensors("/home/ray/daten/dissertation/ConfigSensors.xml");
		System.out.println("sensors: "+sensors);
		
		for (Sensor sensor : sensors.sensor) {
			System.out.println("  sensor: "+sensor);
			for(CommandView cv : sensor.commands) {
				System.out.println("    command: "+cv+"  ("+cv.ViewID+")");
			}
		}

	}

}
