/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.snortinosgigui.XMLModels;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


/**
 * create a xml file for test purposes
 */
public class createXMLFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SensorsXML sensors= new SensorsXML();
		//sensor.add(new HostXML("localhost", "snort100", "snort", "snortpasswrd", "root", "asdf1234", "mysql", 3306, "com.mysql.jdbc.Driver"));
		
		SensorXML sensor = new SensorXML();
		sensor.ipaddr="localhost";
		sensor.sid = 1;
		sensor.add(new CommandViewXML("info", "org.SnortInOSGi.frontend.infos.InfosView"));
		sensor.add(new CommandViewXML("alerts", "org.snortinosgi.frontend.alerts.views.AlertsView"));
		sensor.add(new CommandViewXML("statistic", "org.SnortInOSGi.frontend.ProtocolStatistics.View"));
		sensor.add(new CommandViewXML("rebuild", "org.snortinosgi.frontend.rebuild.views.RebuildView"));
		sensor.add(new CommandViewXML("ipseek", "org.snortinosgi.frontend.ipseek.views.IPSeekView"));
		sensors.add(sensor);
		
		sensor = new SensorXML();
		sensor.ipaddr="localhost";
		sensor.sid = 2;
		sensor.add(new CommandViewXML("info", "org.SnortInOSGi.frontend.infos.InfosView"));
		sensor.add(new CommandViewXML("alerts", "org.snortinosgi.frontend.alerts.views.AlertsView"));
		sensor.add(new CommandViewXML("statistic", "org.SnortInOSGi.frontend.ProtocolStatistics.View"));
		sensor.add(new CommandViewXML("rebuild", "org.snortinosgi.frontend.rebuild.views.RebuildView"));
		sensor.add(new CommandViewXML("ipseek", "org.snortinosgi.frontend.ipseek.views.IPSeekView"));
		sensors.add(sensor);
		

		try {
			JAXBContext context = JAXBContext.newInstance( SensorsXML.class );
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( sensors, System.out );
		} catch (JAXBException e) {
			e.printStackTrace();
		} 

	}

}
