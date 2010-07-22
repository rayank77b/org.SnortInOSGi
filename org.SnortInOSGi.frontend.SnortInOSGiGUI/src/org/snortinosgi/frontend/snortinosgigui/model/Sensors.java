/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.snortinosgi.frontend.snortinosgigui.XMLModels.SensorXML;
import org.snortinosgi.frontend.snortinosgigui.XMLModels.SensorsXML;

/**
 * Sesnsors describe a list of sensors defined in Sensor class. This class is used in xml-mapping.
 */
public class Sensors {
	public List<Sensor> sensor;

	public Sensors(String xmlFile) {
		sensor = new ArrayList<Sensor>();
		
		SensorXML sensor = null;
		JAXBContext context;
		
		try {
			context = JAXBContext.newInstance( SensorsXML.class );
			Unmarshaller um = context.createUnmarshaller(); 
			
			SensorsXML sensors = (SensorsXML)um.unmarshal( new FileReader( xmlFile ) ); 
			
			for ( Iterator<SensorXML> i = sensors.getSensor().iterator(); i.hasNext(); )
			{
				sensor = (SensorXML)i.next();
				this.sensor.add(new Sensor(sensor, this));
			}	
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void addChild(Sensor child) {
		sensor.add(child);
		child.setParent(this);
	}
	
	public void removeChild(Sensor child) {
		sensor.remove(child);
		child.setParent(null);
	}
	
	public Sensor[] getChildren() {
		return (Sensor[]) sensor.toArray(new Sensor[sensor.size()]);
	}
	public boolean hasChildren() {
		return sensor.size()>0;
	}
	
	public String toString() {
		return "Sensors:";
	}
}
