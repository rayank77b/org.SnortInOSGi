/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.bundle.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.snortinosgi.backend.config.model.HostXML;
import org.snortinosgi.backend.config.model.SensorXML;

/**
 *
 */
public class testingXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SensorXML sensor= new SensorXML();
		sensor.add(new HostXML("localhost", "snort100", "snort", "snortpasswrd", "root", "asdf1234", "mysql", 3306, "com.mysql.jdbc.Driver"));
		//sensor.add(new HostXML("localhost2", "snort10230", "snofrt", "snortpasdasfswrd", "root", "asdf123df4", "mysql", 3306, "com.mysql.jdbc.Driver"));

		try {
			JAXBContext context = JAXBContext.newInstance( SensorXML.class );
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( sensor, System.out );
		} catch (JAXBException e) {
			e.printStackTrace();
		} 

	}

}
