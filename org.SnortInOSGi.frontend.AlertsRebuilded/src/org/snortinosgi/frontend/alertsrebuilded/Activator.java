package org.snortinosgi.frontend.alertsrebuilded;


import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.SnortInOSGi.frontend.AlertsRebuilded";

	// The shared instance
	private static Activator plugin;
	public BundleContext context;
	
	private int dbanalyzerPort;
	private String dbanalyzerIP;
	
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
		System.out.println("Acitvator Alerts started");
		this.context = context;
		String strPort = System.getProperty("org.SnortInOSGi.dbanalyzer.remotePort");
		dbanalyzerPort = Integer.valueOf(strPort);
		dbanalyzerIP = System.getProperty("org.SnortInOSGi.dbanalyzer.remoteIP");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		this.context = null;
		plugin = null;
		System.out.println("Acitvator Alerts stoped");
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
	
	public IRebuildDB getRebuildDB() {
		return Getter.getRebuildDB(context, dbanalyzerPort, dbanalyzerIP);
	}
	
	public IDBAreader getDBAreader() {
		return Getter.getDBAreader(context, dbanalyzerPort, dbanalyzerIP);
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
