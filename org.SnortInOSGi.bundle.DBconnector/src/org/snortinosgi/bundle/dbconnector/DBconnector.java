/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 *
 */
package org.snortinosgi.bundle.dbconnector;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.snortinosgi.backend.config.model.HostXML;
import org.snortinosgi.backend.config.model.SensorXML;


/**
 * class DBconnector provide an api to database access. It should be used only locale.
 * org.SnortInOSGi.bundle.dbconnector.configFile - save the path to the xml-config file.
 */
public class DBconnector implements IDBconnector {
	/**
	 * database connection 
	 */
	Connection dbconnection;

	/**
	 * access as root (root=true) or as snort user (root=false)
	 */
	private boolean root;
	/**
	 * hostname of the database
	 */
	private String host;
	
	/**
	 * create a connection
	 * @param root	declare either connect as root (true) or as snort user (root=false)
	 * @param host	hostname of the database
	 */
	public DBconnector(boolean root, String host) {
		this.root=root;
		this.host=host;
	}
	
	/**
	 * connect() connect to the database and save the connection in private attribute.
	 * The configuration are getted from xml-config file "Config.xml".
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void  connect() throws ClassNotFoundException, SQLException {
		
		HostXML host = getSensorData(this.host);
		
		Class.forName( host.getDbdriver() );
		if(this.root)
			dbconnection = (Connection) DriverManager.getConnection( host.createDbUrl(), host.getRootusername(), host.getRootuserpassword() );
		else
			dbconnection = (Connection) DriverManager.getConnection( host.createDbUrl(), host.getSnortusername(), host.getSnortuserpassword() );
	}
	
	/**
	 * read the xml config file and convert it to a HostXML object.
	 * @param hostname	hostname of the database.
	 * @return	HostXML object with stored configuration data from xml-file
	 */
	public HostXML getSensorData(String hostname) {
		HostXML host = null;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance( SensorXML.class );
			Unmarshaller um = context.createUnmarshaller(); 
			
			String xmlFilePath = System.getProperty("org.SnortInOSGi.bundle.dbconnector.configFile");
			SensorXML sensor = (SensorXML)um.unmarshal( new FileReader( xmlFilePath ) ); 
			
			// FIXME  sensor has three same instances of host in list, should be one
			for ( Iterator<HostXML> i = sensor.getHosts().iterator(); i.hasNext(); )
			{
				host = (HostXML)i.next();
				if(host.getHostname().equals(hostname))
					return host;
			}	
			return null;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.snortinosgi.backend.dbconnector.IDBconnector#executeSQL(java.lang.String)
	 */
	@Override
	public ResultSet executetSQL(String sql) throws SQLException {
		return  this.dbconnection.createStatement().executeQuery(sql); 
	}
	
	/* (non-Javadoc)
	 * @see org.snortinosgi.backend.dbconnector.IDBconnector#executeCommand(java.lang.String)
	 */
	@Override
	public int executeCommand(String sql)  throws SQLException {
		return  this.dbconnection.createStatement().executeUpdate(sql);
	}

}
