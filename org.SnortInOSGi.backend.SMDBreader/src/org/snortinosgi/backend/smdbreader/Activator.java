/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.smdbreader;

import java.util.Hashtable;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

/**
 * Activator of Bundle SMDBreader. This Activator register the SMDBreader Object to remote service on start.
 */
public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("---SMDBreader Activator started");
		
		final Hashtable<String,Boolean> properties = new Hashtable<String,Boolean>();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		ISMDBreader reader = new SMDBreader();
		context.registerService(ISMDBreader.class.getName(), reader, properties);
		
		System.out.println(">> ISMDBreader is registered  ++++++++++++");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("---SMDBreader Activator stoped");
	}

}
