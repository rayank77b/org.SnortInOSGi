package org.snortinosgi.frontend.ipfields;

import org.SnortInOSGi.openAPI.interfaces.IPFields.IIPFields;
import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.SnortInOSGi.frontend.IPFields";

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
		System.out.println("Acitvator IPFields started");
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
		System.out.println("Acitvator IPFields stoped");
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
	 * get the remote IPFields instance from the r-osgi remote service. Call it from the Getter in org.SnortInOSGi.openAPI.interfaces bundle.
	 * @return the instace of the IPFields runing on backend
	 */
	public IIPFields getIPFieldsInstance() {
		IIPFields ipfield = (IIPFields) Getter.getX(this.context, this.backendPort, this.backendIP, IIPFields.class.getName());
		if(ipfield!=null)
			return ipfield;
		else
			return null;
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
