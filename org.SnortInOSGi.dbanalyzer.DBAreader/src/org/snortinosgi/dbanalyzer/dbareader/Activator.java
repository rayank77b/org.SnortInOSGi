package org.snortinosgi.dbanalyzer.dbareader;

import java.util.Hashtable;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;


public class Activator implements BundleActivator {
	
	public static final String PLUGIN_ID = "org.SnortInOSGi.dbanalyzer.DBAreader";

	// The shared instance
	private static Activator plugin;
	public BundleContext context;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		this.context = context;
		System.out.println("---- IDBAreader Activator started");
		
		final Hashtable<String,Boolean> properties = new Hashtable<String,Boolean>();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		IDBAreader reader = new DBAreader();
		context.registerService(IDBAreader.class.getName(), reader, properties);
		System.out.println(">> IDBAreader is registered  ++++++++++++");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		this.context = null;
		plugin = null;
		System.out.println("---- RebuildDB Activator stoped");
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
