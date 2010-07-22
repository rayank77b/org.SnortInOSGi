package org.snortinosgi.dbanalyzer.rebuilddb;

import java.util.Hashtable;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.SnortInOSGi.dbanalyzer.RebuildDB";
	
	// The shared instance
	private static Activator plugin;
	public BundleContext context;
	
	private int backendPort;
	private String backendIP;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		this.context = context;
		System.out.println("---- RebuildDB Activator started");
		
		String strPort = System.getProperty("org.SnortInOSGi.backend.remotePort");
		backendPort = Integer.valueOf(strPort);
		backendIP = System.getProperty("org.SnortInOSGi.backend.remoteIP");
		
		final Hashtable<String,Boolean>  properties = new Hashtable<String,Boolean>();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		
		IRebuildDB rebuilder = new RebuildDB();
		context.registerService(IRebuildDB.class.getName(), rebuilder,properties);
		System.out.println(">> IRebuildDB is registered  ++++++++++++");
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
	
	/**
	 * 
	 * @return the 
	 */
	public ISMDBreader getReader() {
		return Getter.getReader(context, backendPort, backendIP);
	}
	
	/**
	 * 
	 * @return the 
	 */
	public ISMDBwriter getWriter() {
		return Getter.getWriter(context, backendPort, backendIP);
	}
}
