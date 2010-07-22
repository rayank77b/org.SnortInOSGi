/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.smdbwriter;

import java.util.Hashtable;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

/**
 * Activator of Bundle SMDBwriter. This Activator register the SMDBwriter Object to remote service on start.
 */
public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("SMDBWriter is started");
		
		final Hashtable<String, Boolean> properties = new Hashtable<String, Boolean>();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		ISMDBwriter writer = new SMDBwriter();
		context.registerService(ISMDBwriter.class.getName(), writer, properties);
		System.out.println(">> ISMDBwriter is registered  ++++++++++++");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("SMDBWriter is stoped");
	}

}
