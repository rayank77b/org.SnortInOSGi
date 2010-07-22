package org.snortinosgi.frontend.protocolstatistics;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.SnortInOSGi.frontend.ProtocolStatistics";

	// The shared instance
	private static Activator plugin;
	public BundleContext context;
	
	private int backendPort;
	private String backendIP;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		System.out.println("Acitvator ProtocolStatistics started");
		this.context = context;
		String strPort = System.getProperty("org.SnortInOSGi.backend.remotePort");
		backendPort = Integer.valueOf(strPort);
		backendIP = System.getProperty("org.SnortInOSGi.backend.remoteIP");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		this.context = null;
		plugin = null;
		System.out.println("Acitvator ProtocolStatistics stoped");
		super.stop(context);
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

}
