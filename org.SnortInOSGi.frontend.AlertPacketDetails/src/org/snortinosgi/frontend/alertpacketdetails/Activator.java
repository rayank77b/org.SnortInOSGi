package org.snortinosgi.frontend.alertpacketdetails;

import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.Reference;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.SnortInOSGi.frontend.AlertPacketDetails";

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
		System.out.println("Acitvator AlertsDetails started");
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
		plugin = null;
		super.stop(context);
		System.out.println("Acitvator AlertsDetails stoped");
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public ISMDBreader getReader() {
		return Getter.getReader(context, backendPort, backendIP);
	}
	
	public Alert getAlert(long cid, int sid) {
		Alert alert = new Alert();
		
		ISMDBreader reader = getReader();
		if(reader!=null) {
			String xml = reader.getAlert(cid, sid);
			alert = (Alert) Data.getFromXML(xml, Alert.class);
		}
		return alert;
	}
	
	public Packet getPacket(long cid, int sid) {
		Packet packet = new Packet();
		
		ISMDBreader reader = getReader();
		if(reader!=null) {
			String xml = reader.getPacket(cid, sid);
			packet = (Packet) Data.getFromXML(xml, Packet.class);
		}
		return packet;
	}
	
	public List<Reference> getReference(int sig_id) {
		List<Reference> refs = null;
		
		ISMDBreader reader = getReader();
		if(reader!=null) {
			List<String> xmls = reader.getReference(sig_id);
			refs = HelpFunctions.convertXML2ReferenceList(xmls);
		}
		return refs;
	}
	
	public String getData(long cid, int sid) {
		String data = null;
		
		ISMDBreader reader = getReader();
		if(reader!=null) {
			data = reader.getPayload(cid, sid);
		}
		return data;
		
	}
}
