package org.snortinosgi.backend.ipfields;

import java.util.Hashtable;


import org.SnortInOSGi.openAPI.interfaces.IPFields.IIPFields;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("---SMDBreader Activator started");
		
		final Hashtable<String,Boolean> properties = new Hashtable<String,Boolean>();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		IIPFields ipfields = new IPFields();
		context.registerService(IIPFields.class.getName(), ipfields, properties);
		
		System.out.println(">> IIPFields is registered  ++++++++++++");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
