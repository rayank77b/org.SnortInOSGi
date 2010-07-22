/**
 * @author Andrej Frank, 2009,2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.interfaces.getter;

import java.io.IOException;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import ch.ethz.iks.r_osgi.RemoteOSGiException;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;

/**
 * Getter class provide a wrapper for getting Remote Services. 
 * For getting remote services use getReader(), getWriter(), getDBAreader() or getRebuildeDB() methods. 
 */
public class Getter {
	
	/**
	 * main method to get remote services with r-osgi. Need class name to get the right service. 
	 * Dont use this function. Instead use the next methods to get the right remote object. 
	 * @param context	BundleContext to grant access to other methods. Get it from Activator.
	 * @param Port		port number of the remote services
	 * @param IP		remote IP address of the remote services
	 * @param className	name of the class. example ISMDBreader.class.getName()
	 * @return	object of the remote service. Return null on errors.
	 */
	public static Object getX(BundleContext context, int Port, String IP, String className) {
		final ServiceReference serviceRef = context.getServiceReference(RemoteOSGiService.class.getName());
		
		//System.out.println("getter [1]");
		if (serviceRef == null) {
	          System.out.println("R-OSGi service not found!  Wrong port??");
	          return null;
	    } else {
	    	
	    	//System.out.println("getter [2]");
	    	final RemoteOSGiService remote = (RemoteOSGiService) context.getService(serviceRef);
	    	//System.out.println("getter [3]");
	    	
	        try {
	        	//System.out.println("getter [4]");
	        	// here must be a timeout
	        	remote.connect(new URI("r-osgi://"+IP+":"+(Port)));  
	        	
	        	//System.out.println("getter [5]");
	        	URI uri = new URI("r-osgi://"+IP+":"+Port);
	            final RemoteServiceReference[] references = remote.getRemoteServiceReferences(uri , className, null);
	              
	            //System.out.println("getter [6]");
	            if (references == null) {
	            	System.out.println("Consumer:  reference is null!");
	            	return null;
	            } else {
	                  Object obj = remote.getRemoteService(references[0]);
	                  return obj;
	              }
	          } catch (RemoteOSGiException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
	        	  //System.out.println("Consumer: finnally");
	              context.ungetService(serviceRef);
	        }
	    }
	}
	
	/**
	 * getReader() delivers an ISMDBreader object from backend
	 * @param context		BundleContext to grant access to other methods. Get it from Activator.
	 * @param backendPort	backend port number of the remote services
	 * @param backendIP		remote IP address of the backend
	 * @return	return ISMDBreader object. Return null on errors.
	 */
	public static ISMDBreader getReader(BundleContext context, int backendPort, String backendIP) {
		ISMDBreader reader = (ISMDBreader) getX(context, backendPort, backendIP, ISMDBreader.class.getName());
		if(reader!=null)
			return reader;
		else
			return null;
	}
	
	/**
	 * getWriter() delivers an ISMDBwriter object from backend
	 * @param context		BundleContext to grant access to other methods. Get it from Activator.
	 * @param backendPort	backend port number of the remote services
	 * @param backendIP		remote IP address of the backend
	 * @return	return ISMDBwriter object. Return null on errors.
	 */
	public static ISMDBwriter getWriter(BundleContext context, int backendPort, String backendIP) {
		ISMDBwriter tmp = (ISMDBwriter) getX(context, backendPort, backendIP, ISMDBwriter.class.getName());
		if(tmp!=null)
			return tmp;
		else
			return null;
	}

	/**
	 * getRebuildDB() delivers an IRebuildDB object from db analyzer
	 * @param context		BundleContext to grant access to other methods. Get it from Activator.
	 * @param dbanalyzerPort	db analyzer port number of the remote services
	 * @param dbanalyzerIP	remote IP address of the db analyzer
	 * @return	return IRebuildDB object. Return null on errors.
	 */
	public static IRebuildDB getRebuildDB(BundleContext context, int dbanalyzerPort, String dbanalyzerIP) {
		IRebuildDB tmp = (IRebuildDB) getX(context, dbanalyzerPort,dbanalyzerIP, IRebuildDB.class.getName());
		if(tmp!=null)
			return tmp;
		else
			return null;
	}
	
	/**
	 * getDBAreader() delivers an IDBAreader object from db analyzer
	 * @param context		BundleContext to grant access to other methods. Get it from Activator.
	 * @param dbanalyzerPort	db analyzer port number of the remote services
	 * @param dbanalyzerIP	remote IP address of the db analyzer
	 * @return	return IDBAreader object. Return null on errors.
	 */
	public static IDBAreader getDBAreader(BundleContext context, int dbanalyzerPort, String dbanalyzerIP) {
		IDBAreader tmp = (IDBAreader) getX(context, dbanalyzerPort, dbanalyzerIP, IDBAreader.class.getName());
		if(tmp!=null)
			return tmp;
		else
			return null;
	}

}
