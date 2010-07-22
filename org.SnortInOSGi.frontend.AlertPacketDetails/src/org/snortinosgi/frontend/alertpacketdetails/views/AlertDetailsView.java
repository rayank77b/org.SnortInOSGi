package org.snortinosgi.frontend.alertpacketdetails.views;

import java.sql.Timestamp;
import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.Reference;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.snortinosgi.frontend.alertpacketdetails.Activator;







public class AlertDetailsView extends ViewPart {

	public static final String ID = "org.snortinosgi.frontend.alertpacketdetails.views.AlertDetailsView";

	public long cid;
	public int sid;


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		GridData gd;
		GridLayout layout;
		Label l;
		
		
		String secID = this.getViewSite().getSecondaryId();
		
		
		String []tmpsidcid = secID.split("_");
		
		sid = Integer.valueOf(tmpsidcid[1]);
		cid = Long.valueOf(tmpsidcid[2]);
		
		
		Alert alert = Activator.getDefault().getAlert(cid, sid);
		Packet packet = Activator.getDefault().getPacket(cid, sid);
		
		List<Reference> reference = Activator.getDefault().getReference(alert.event.signature);
		String data = Activator.getDefault().getData(cid, sid);
		
		//////////////////////////////////////////////////
		
		layout = new GridLayout();
		layout.marginHeight = 15;
		layout.marginWidth  = 15;
		layout.numColumns   = 6;
		parent.setLayout(layout);
		
		
		l = new Label(parent, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=6;
		l.setLayoutData(gd);
		Timestamp t = new Timestamp(alert.event.timestamp);
		l.setText("Informations about Event CID: " +cid+ "    SID: "+sid+"  detected on "+t.toString());
		
		
		Group groupalert = new Group(parent, SWT.SHADOW_ETCHED_IN);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=3;
		groupalert.setLayoutData(gd);
	    groupalert.setText("Alert Details");
	    layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.numColumns   = 2;
		groupalert.setLayout(layout);
	    
	    Group grouppacket = new Group(parent, SWT.SHADOW_ETCHED_IN);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=3;
		grouppacket.setLayoutData(gd);
		grouppacket.setText("Packet Details");
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.numColumns   = 2;
		grouppacket.setLayout(layout);
		
		Group groupdata = new Group(parent, SWT.SHADOW_ETCHED_IN);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=6;
		groupdata.setLayoutData(gd);
		groupdata.setText("Data Details");
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.numColumns   = 2;
		groupdata.setLayout(layout);
		
		// build the alert info (signature, reference (url???), alertpaternrule, sensor info
		l = new Label(groupalert, SWT.WRAP);
		l.setText("Signature  ");
		l = new Label(groupalert, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint=200;
		l.setLayoutData(gd);
		l.setText(alert.signature.name);
		
		l = new Label(groupalert, SWT.WRAP);
		l.setText("Signature class  ");
		l = new Label(groupalert, SWT.WRAP);
		l.setText(alert.signature.class_name);
		
		l = new Label(groupalert, SWT.WRAP);
		l.setText("Priority/Risk  ");
		l = new Label(groupalert, SWT.WRAP);
		if(alert.signature.priority==Signature.PRIO_LOW) {
			l.setText("LOW");
			l.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		} else if(alert.signature.priority==Signature.PRIO_MIDDLE) {
			l.setText("MIDDLE");
			l.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		} else {
			l.setText("HIGH");
			l.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
		} 
		
		l = new Label(groupalert, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		
		l = new Label(groupalert, SWT.WRAP);
		l.setText("References  ");
		l = new Label(groupalert, SWT.WRAP);
		l.setText(" ");
		for(Reference ref : reference) {
			l = new Label(groupalert, SWT.WRAP);
			l.setText("   ");
			Text txtref = new Text(groupalert, SWT.WRAP);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.widthHint=200;
			txtref.setLayoutData(gd);
			txtref.setText("<html>"+buildReferenceString(ref)+"</html>");
		}
		
		l = new Label(groupalert, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		
		l = new Label(groupalert, SWT.WRAP);
		l.setText("Signature ID  ");
		
		l = new Label(groupalert, SWT.WRAP);
		l.setText(Integer.toString(alert.signature.sid));
		
		l = new Label(groupalert, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		
		// TODO sensor infos
		
		// build the packet info (iphdr, layer 4header)
		l = new Label(grouppacket, SWT.WRAP);
		l.setText("IP Source  ");
		l = new Label(grouppacket, SWT.WRAP);
		l.setText(HelpFunctions.ipLong2String(packet.iphdr.src));
		
		l = new Label(grouppacket, SWT.WRAP);
		l.setText("IP Destination  ");
		l = new Label(grouppacket, SWT.WRAP);
		l.setText(HelpFunctions.ipLong2String(packet.iphdr.dst));
		
		l = new Label(grouppacket, SWT.WRAP);
		l.setText("ID  ");
		l = new Label(grouppacket, SWT.WRAP);
		l.setText(Integer.toString(packet.iphdr.id));
		
		l = new Label(grouppacket, SWT.WRAP);
		l.setText("TTL  ");
		l = new Label(grouppacket, SWT.WRAP);
		l.setText(Integer.toString(packet.iphdr.ttl));
		
		l = new Label(grouppacket, SWT.WRAP);
		l.setText("Next Protocol  ");
		l = new Label(grouppacket, SWT.WRAP);
		l.setText(Integer.toString(packet.iphdr.proto)+" ("+getProtocol(packet.iphdr.proto)+")");
		
		l = new Label(grouppacket, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		
		if(packet.iphdr.proto==IPheader.TCP && packet.tcphdr!=null) {
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("TCP  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(" ");
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  Destination Port  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Integer.toString(packet.tcphdr.dport));
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  Source Port  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Integer.toString(packet.tcphdr.sport));
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  SequenceNr  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Long.toString(packet.tcphdr.seq));
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  AcknowledgmentNr  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Long.toString(packet.tcphdr.ack));
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  Offset  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Integer.toString(packet.tcphdr.off));
			
		} else if(packet.iphdr.proto==IPheader.UDP && packet.udphdr!=null) {
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("UDP  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(" ");
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  Destination Port  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Integer.toString(packet.udphdr.dport));
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("  Source Port  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(Integer.toString(packet.udphdr.sport));
			
		} else if(packet.iphdr.proto==IPheader.ICMP && packet.icmphdr!=null) {
			l = new Label(grouppacket, SWT.WRAP);
			l.setText("ICMP  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(" ");
			
			String []icmp=getICMPTypCode(packet.icmphdr.type, packet.icmphdr.code);
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(" Type  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(icmp[0]);
			
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(" Code  ");
			l = new Label(grouppacket, SWT.WRAP);
			l.setText(icmp[1]);
			
		}  
		
		// build the data info  (hex, base, ...)
		Text txtdata = new Text(groupdata, SWT.READ_ONLY|SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.heightHint = 400;
		gd.widthHint  = 800;
		txtdata.setLayoutData(gd);
		txtdata.setText(setBreak(data));
		
		
	}
	
	public String toChars1(String tmp) {
		char []c = tmp.toCharArray();
		
		StringBuilder buff = new StringBuilder();
		for(int i=0; i<c.length; i=i+2) {
			String s = Character.toString(c[i])+Character.toString(c[i+1]);
			buff.append(s+" ");
		}
		
		return buff.toString();
	}
	
	public String toChars2(String tmp) {
		char []c = tmp.toCharArray();
		
		StringBuilder buff = new StringBuilder();
		for(int i=0; i<c.length; i=i+2) {
			String s = Character.toString(c[i])+Character.toString(c[i+1]);
			buff.append(getChar(Integer.parseInt(s, 16)));
		}
		return buff.toString();
	}
	
	public static char getChar(int i) {
		if(i>32&&i<126)
			return (char)i;
		else
			return '.';
	}
	
	public String setBreak(String data) {
		String tmp;
		StringBuffer buff = new StringBuffer();
		//System.out.println("len: "+data.length());
		int i=0;
		for(i=0; i< data.length()-32; i=i+32) {
		
			if(i/2<16) 
				buff.append("000"+i/2+"    ");
			else if(i/2<112)
				buff.append("00"+i/2+"    ");
			else 
				buff.append("0"+i/2+"    ");
			tmp = data.substring(i, i+32);
			buff.append(toChars1(tmp)+"       ");
			buff.append(toChars2(tmp)+"\n");
			
		}
		if(i/2<16) 
			buff.append("000"+i/2+"    ");
		else if(i/2<112)
			buff.append("00"+i/2+"    ");
		else 
			buff.append("0"+i/2+"    ");
		tmp = data.substring(i, data.length());
		buff.append(toChars1(tmp)+"       ");
		buff.append(toChars2(tmp)+"\n");
		
		return buff.toString();
	}
	
	public String getProtocol(int p) {
		switch(p) {
		case 6: 
			return "TCP"; 
		case 1: return "ICMP"; 
		case 17: return "UDP";
		case 4: return "IP";
		case 255: return "reserved"; 
		default: return  "--";
		}
	}

	public String buildReferenceString(Reference r) {
		if(r.name.equalsIgnoreCase("nessus"))
			return "Nessus: "+r.tag;
		if(r.name.equalsIgnoreCase("cve"))
			return "CVE: <a href=\"http://web.nvd.nist.gov/view/vuln/detail?vulnId="+r.tag+"\">http://web.nvd.nist.gov/view/vuln/detail?vulnId="+r.tag+"</a>";
		if(r.name.equalsIgnoreCase("bugtraq"))
			return "Bugtraq: "+r.tag;
		if(r.name.equalsIgnoreCase("url"))
			return "URL: "+r.tag;
		if(r.name.equalsIgnoreCase("arachNIDS"))
			return "arachNIDS: "+r.tag;
		if(r.name.equalsIgnoreCase("McAfee"))
			return "McAfee: "+r.tag;

		return r.name+">>"+r.tag;
	}
	
	public String[] getICMPTypCode(int type, int code) {
		String strtype = null;
		String strcode = null;
		
		switch(type) {
		case 0:
			strtype="Echo Reply"; strcode="0";
			break;
		case 3:
			strtype="Destination Unreachable"; 
			switch(code) {
			case 0:
				strcode="Destination network unreachable";
				break;
			case 1:
				strcode="Destination host unreachable";
				break;
			case 2:
				strcode="Destination protocol unreachable";
				break;
			case 3:
				strcode="Destination port unreachable";
				break;
			case 4:
				strcode="Fragmentation required, and DF flag set";
				break;
			case 5:
				strcode="Source route failed";
				break;
			case 6:
				strcode="Destination network unknown";
				break;
			case 7:
				strcode="Destination host unknown";
				break;
			case 8:
				strcode="Source host isolated";
				break;
			case 9:
				strcode="Network administratively prohibited";
				break;
			case 10:
				strcode="Host administratively prohibited";
				break;
			case 11:
				strcode="Network unreachable for TOS";
				break;
			case 12:
				strcode="Host unreachable for TOS";
				break;
			case 13:
				strcode="Communication administratively prohibited";
				break;
			default:
				strcode="unknown code "+String.valueOf(code);
			}
			break;
		case 4:
			strtype="Source Quench"; strcode="Source quench (congestion control)";
			break;
		case 5:
			strtype="Redirect Message"; 
			switch(code) {
			case 0:
				strcode="Redirect Datagram for the Network";
				break;
			case 1:
				strcode="Redirect Datagram for the Host";
				break;
			case 2:
				strcode="Redirect Datagram for the TOS & network";
				break;
			case 3:
				strcode="Redirect Datagram for the TOS & host";
				break;
			default:
				strcode="unknown code "+String.valueOf(code);
			}
			break;
		case 6:
			strtype=" "; strcode="Alternate Host Address";
			break;
		case 8:
			strtype="Echo Request"; strcode="Echo Request";
			break;
		case 9:
			strtype="Router Advertisement"; strcode="Router Advertisement";
			break;
		case 10:
			strtype="Router Solicitation"; strcode="Router Solicitation";
			break;
		case 11:
			strtype="Time Exceeded";
			switch(code) {
			case 0:
				strcode="TTL expired in transit";
				break;
			case 1:
				strcode="Fragment reassembly time exceeded";
				break;
			default:
				strcode="unknown code "+String.valueOf(code);
			}
			break;
		case 12:
			strtype="Parameter Problem: Bad IP header"; 
			switch(code) {
			case 0:
				strcode="Pointer indicates the error";
				break;
			case 1:
				strcode="Missing a required option";
				break;
			case 2:
				strcode="Bad length";
				break;
			default:
				strcode="unknown code "+String.valueOf(code);
			}
			break;
		case 13:
			strtype="Timestamp"; strcode="Timestamp";
			break;
		case 14:
			strtype="Timestamp Reply"; strcode="Timestamp Reply";
			break;
		case 15:
			strtype="Information Request"; strcode="Information Request";
			break;
		case 16:
			strtype="Information Reply"; strcode="Information Reply";
			break;
		case 17:
			strtype="Address Mask Request"; strcode="Address Mask Request";
			break;
		case 18:
			strtype="Address Mask Reply"; strcode="Address Mask Reply";
			break;
		case 30:
			strtype="Traceroute"; strcode="Information Request";
			break;
		case 32:
			strtype=" "; strcode="Mobile Host Redirect";
			break;
			
		default:
			strtype=String.valueOf(type);
			strtype=String.valueOf(code);
		}
		String []ret = {strtype, strcode};
		return ret;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}
}