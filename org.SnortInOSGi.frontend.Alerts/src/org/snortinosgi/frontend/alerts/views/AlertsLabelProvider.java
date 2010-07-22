/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alerts.views;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.snortinosgi.frontend.alerts.Activator;

/**
 *
 */
public class AlertsLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final Image IMG_RED = Activator.getImageDescriptor( "icons/red.gif").createImage();
	private static final Image IMG_BLUE = Activator.getImageDescriptor( "icons/blue.gif").createImage();
	private static final Image IMG_YELLOW = Activator.getImageDescriptor( "icons/yellow.gif").createImage();

	// String[] titles = {"cid", "SRC", "DST", "Proto", "Alert", "Prio"};
	public static final int CI_CID = 0;
	public static final int CI_SRC = 1;
	public static final int CI_DST = 2;
	public static final int CI_PROTOCOL = 3;
	public static final int CI_ALERT = 4;
	public static final int CI_PIORIOTY = 5;

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Alert alerts = (Alert) element;
		
		switch (columnIndex) {
		case CI_CID:
			return new Long(alerts.event.cid).toString();
		case CI_SRC:
			return HelpFunctions.ipLong2String(alerts.iphdr.src);
		case CI_DST:
			return HelpFunctions.ipLong2String(alerts.iphdr.dst);
		case CI_PROTOCOL:
			if( alerts.iphdr.proto == IPheader.ICMP)
				return "ICMP";
			if( alerts.iphdr.proto == IPheader.UDP)
				return "UDP";
			if( alerts.iphdr.proto == IPheader.TCP) 
				return "TCP";
			if( alerts.iphdr.proto == IPheader.IP) 
				return "IP";
			return "unkn";
		case CI_ALERT:
			if(alerts.signature.name.length()>41)
				return alerts.signature.name.substring(0, 40)+"...";
			else
				return alerts.signature.name;
		case CI_PIORIOTY:
			return "";
		default:
			return "ERROR:should not happen";
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// In case you don't like image just return null here
		if (columnIndex == CI_PIORIOTY) {
			Alert a = (Alert)element;
			if (a.signature.priority==Signature.PRIO_BLUE) {
				return IMG_BLUE;
			} else if (a.signature.priority==Signature.PRIO_YELLOW) {
				return IMG_YELLOW;
			} else 
				return IMG_RED;
		}
		return null;
	}
	
	
}