/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alertsrebuilded.provider;

import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
//import org.snortinosgi.frontend.alertsrebuilded.Activator;
import org.snortinosgi.frontend.alertsrebuilded.model.Data;
import org.snortinosgi.frontend.alertsrebuilded.model.ModelProvider;

/**
 *
 */
public class AlertsLabelProvider extends LabelProvider implements ITableLabelProvider {
	//private static final Image IMG_RED = Activator.getImageDescriptor( "icons/red.gif").createImage();
	//private static final Image IMG_BLUE = Activator.getImageDescriptor( "icons/blue.gif").createImage();
	//private static final Image IMG_YELLOW = Activator.getImageDescriptor( "icons/yellow.gif").createImage();

	// String[] titles = {"cid", "SRC", "DST", "Proto", "Alert", "Prio"};
	public static final int CI_CID = 0;
	public static final int CI_SRC = 1;
	public static final int CI_DST = 2;
	public static final int CI_PROTOCOL = 3;
	public static final int CI_ALERT = 4;
	public static final int CI_PIORIOTY = 5;

	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		Data data = (Data) element;
		
		if(columnIndex < data.datas.length) {
			String title = ModelProvider.getInstance(data.sid).getTitles()[columnIndex];
			String value = data.datas[columnIndex];
			//System.out.println("i: "+columnIndex+"    val: "+value);
			
			if(title.equalsIgnoreCase(IPheader.DST) || title.equalsIgnoreCase(IPheader.SRC)) {
				//System.out.println("ip val: "+value);
				long ip = Long.valueOf(value);
				return HelpFunctions.ipLong2String(ip);
			}
			
			if(title.equalsIgnoreCase(IPheader.PROTO)) {
				int proto = Integer.valueOf(value);
				if( proto == IPheader.ICMP)
					return "ICMP";
				if( proto == IPheader.UDP)
					return "UDP";
				if( proto == IPheader.TCP) 
					return "TCP";
				if( proto == IPheader.IP) 
					return "IP";
				return value;
			}
			return value;
			
		} else
			return "";
		
		/*
		switch (columnIndex) {
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
		*/
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// In case you don't like image just return null here
		/*
		if (columnIndex == CI_PIORIOTY) {
			String a = (String)element;
			if (a.signature.priority==Signature.PRIO_BLUE) {
				return IMG_BLUE;
			} else if (a.signature.priority==Signature.PRIO_YELLOW) {
				return IMG_YELLOW;
			} else 
				return IMG_RED;
		}
		*/
		return null;
	}
	
}