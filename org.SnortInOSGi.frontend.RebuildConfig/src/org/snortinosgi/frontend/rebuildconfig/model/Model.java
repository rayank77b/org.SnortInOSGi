/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.rebuildconfig.model;

/**
 *
 */
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.SnortInOSGi.openAPI.snortschema.ICMPheader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.SnortInOSGi.openAPI.snortschema.Reference;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.SnortInOSGi.openAPI.snortschema.TCPheader;
import org.SnortInOSGi.openAPI.snortschema.UDPheader;

public class Model {

    public static TreeObject createSchemaModel() {
    	TreeObject to;
    	TreeParent child; 

    	TreeParent root = new TreeParent("SnortScheme", "");
    	
    	// create Event
    	child = new TreeParent("Event", Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP);
        to = new TreeObject("cid				Unique number of each Alert/Log", Event.CID);
        child.addChild(to);
        to = new TreeObject("sid				Unique number of esach sensor", Event.SID);
        child.addChild(to);
        to = new TreeObject("signature		Unique number for each signature", Event.SIGNATUREID);
        child.addChild(to);
        to = new TreeObject("timestamp		Time stamp for Alert log", Event.TIMESTAMP);
        child.addChild(to);
        root.addChild(child);
        
        // create Signature
        child = new TreeParent("Signature", Signature.NAME+";"+Signature.CLASSNAME+";"+ Signature.PRIORITY);
        to = new TreeObject("name			The signature description", Signature.NAME);
        child.addChild(to);
        to = new TreeObject("classname		The signature class of attack name", Signature.CLASSNAME);
        child.addChild(to);
        to = new TreeObject("priority			Priority for the Signature", Signature.PRIORITY);
        child.addChild(to);
        root.addChild(child);
        
        // create Reference
        child = new TreeParent("Reference", Reference.NAME+";"+Reference.TAG);
        to = new TreeObject("name			The reference name(number or a url)", Reference.NAME);
        child.addChild(to);
        to = new TreeObject("tag				The reference ", Reference.TAG);
        child.addChild(to);
        root.addChild(child);
        
        // create IPheader
        child = new TreeParent("IP header", IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.TTL+";"+IPheader.PROTO);
        to = new TreeObject("Dst Address", IPheader.DST);
        child.addChild(to);
        to = new TreeObject("Src Address", IPheader.SRC);
        child.addChild(to);
        to = new TreeObject("id", IPheader.ID);
        child.addChild(to);
        to = new TreeObject("ttl", IPheader.TTL);
        child.addChild(to);
        to = new TreeObject("plotocoll", IPheader.PROTO);
        child.addChild(to);
        root.addChild(child);
        
        // create fields for Packet
        child = new TreeParent("Packet", Packet.PROTOCOL+";"+Packet.DSTPORT+";"+Packet.SRCPORT);
        to = new TreeObject("4 layer protocol", Packet.PROTOCOL);
        child.addChild(to);
        to = new TreeObject("4 layer dst port", Packet.DSTPORT);
        child.addChild(to);
        to = new TreeObject("4 layer src port", Packet.SRCPORT);
        child.addChild(to);
        root.addChild(child);
        
        // create ICMPheader
        child = new TreeParent("ICMP header", ICMPheader.CODE+";"+ICMPheader.TYPE);
        to = new TreeObject("code", ICMPheader.CODE);
        child.addChild(to);
        to = new TreeObject("type", ICMPheader.TYPE);
        child.addChild(to);
        root.addChild(child);
        
        // create TCPheader
        child = new TreeParent("TCP header", TCPheader.DSTPORT+";"+TCPheader.SRCPORT+";"+TCPheader.SEQ+";"+TCPheader.ACK+";"+TCPheader.OFF);
        to = new TreeObject("Dst Port", TCPheader.DSTPORT);
        child.addChild(to);
        to = new TreeObject("Src Port", TCPheader.SRCPORT);
        child.addChild(to);
        to = new TreeObject("seq", TCPheader.SEQ);
        child.addChild(to);
        to = new TreeObject("ack", TCPheader.ACK);
        child.addChild(to);
        to = new TreeObject("offset", TCPheader.OFF);
        child.addChild(to);
        root.addChild(child);
        
        // create UDPheader
        child = new TreeParent("UDP header", UDPheader.DSTPORT+";"+UDPheader.SRCPORT);
        to = new TreeObject("Dst Port", UDPheader.DSTPORT);
        child.addChild(to);
        to = new TreeObject("Src Port", UDPheader.SRCPORT);
        child.addChild(to);
        root.addChild(child);
        
        return root;
    }
    
    public static TreeObject createAlertSchemaModel() {
    	TreeObject to;
    	TreeParent child; 

    	// Alert is restricted during to have only event, iphdr and siganture
    	
    	
    	TreeParent root = new TreeParent("SnortScheme", "");
    	
    	// create Event
    	child = new TreeParent("Event", Event.CID+";"+Event.SID+";"+Event.SIGNATUREID+";"+Event.TIMESTAMP);
        to = new TreeObject("cid				Unique number of each Alert/Log", Event.CID);
        child.addChild(to);
        to = new TreeObject("sid				Unique number of esach sensor", Event.SID);
        child.addChild(to);
        to = new TreeObject("signature		Unique number for each signature", Event.SIGNATUREID);
        child.addChild(to);
        to = new TreeObject("timestamp		Time stamp for Alert log", Event.TIMESTAMP);
        child.addChild(to);
        root.addChild(child);
        
        // create Signature
        child = new TreeParent("Signature", Signature.NAME+";"+Signature.CLASSNAME+";"+ Signature.PRIORITY);
        to = new TreeObject("name			The signature description", Signature.NAME);
        child.addChild(to);
        to = new TreeObject("classname		The signature class of attack name", Signature.CLASSNAME);
        child.addChild(to);
        to = new TreeObject("priority			Priority for the Signature", Signature.PRIORITY);
        child.addChild(to);
        root.addChild(child);
        

        
        // create IPheader
        child = new TreeParent("IP header", IPheader.DST+";"+IPheader.SRC+";"+IPheader.ID+";"+IPheader.TTL+";"+IPheader.PROTO);
        to = new TreeObject("Dst Address", IPheader.DST);
        child.addChild(to);
        to = new TreeObject("Src Address", IPheader.SRC);
        child.addChild(to);
        to = new TreeObject("id", IPheader.ID);
        child.addChild(to);
        to = new TreeObject("ttl", IPheader.TTL);
        child.addChild(to);
        to = new TreeObject("plotocoll", IPheader.PROTO);
        child.addChild(to);
        root.addChild(child);

        
        return root;
    }
    
    public static TreeParent createBackupSchemaModel(String tablename) {
    	TreeObject to;
    	TreeParent child; 

    	TreeParent root = new TreeParent("BackupSnortScheme", "");
    	
    	
    	child = new TreeParent(tablename, "");
    	
    	// create Event
        to = new TreeObject("cid				Unique number of each Alert/Log", Event.CID);
        child.addChild(to);
        to = new TreeObject("sid				Unique number of esach sensor", Event.SID);
        child.addChild(to);
        to = new TreeObject("timestamp		Time stamp for Alert log", Event.TIMESTAMP);
        child.addChild(to);
        
        // create Signature
        to = new TreeObject("name			The signature description", Signature.NAME);
        child.addChild(to);
        to = new TreeObject("classname		The signature class of attack name", Signature.CLASSNAME);
        child.addChild(to);
        to = new TreeObject("priority			Priority for the Signature", Signature.PRIORITY);
        child.addChild(to);
        
        // create Reference
        to = new TreeObject("name			The reference name(number or a url)", Reference.NAME);
        child.addChild(to);
        to = new TreeObject("tag				The reference ", Reference.TAG);
        child.addChild(to);
        
        // create IPheader
        to = new TreeObject("Dst Address", IPheader.DST);
        child.addChild(to);
        to = new TreeObject("Src Address", IPheader.SRC);
        child.addChild(to);
        to = new TreeObject("plotocoll", IPheader.PROTO);
        child.addChild(to);
        
        to = new TreeObject("4 layer protocol", Packet.PROTOCOL);
        child.addChild(to);
        to = new TreeObject("4 layer dst port", Packet.DSTPORT);
        child.addChild(to);
        to = new TreeObject("4 layer src port", Packet.SRCPORT);
        child.addChild(to);
        
        to = new TreeObject("code", ICMPheader.CODE);
        child.addChild(to);
        to = new TreeObject("type", ICMPheader.TYPE);
        child.addChild(to);
        
        to = new TreeObject("Dst Port", TCPheader.DSTPORT);
        child.addChild(to);
        to = new TreeObject("Src Port", TCPheader.SRCPORT);
        child.addChild(to);
        to = new TreeObject("seq", TCPheader.SEQ);
        child.addChild(to);
        to = new TreeObject("ack", TCPheader.ACK);
        child.addChild(to);
        to = new TreeObject("offset", TCPheader.OFF);
        child.addChild(to);
        
        to = new TreeObject("Dst Port", UDPheader.DSTPORT);
        child.addChild(to);
        to = new TreeObject("Src Port", UDPheader.SRCPORT);
        child.addChild(to);
        
        root.addChild(child);
        
        return root;
    }
    
    public static TreeParent createAlertBackupSchemaModel(String tablename) {
    	TreeObject to;
    	TreeParent child; 

    	TreeParent root = new TreeParent("BackupSnortScheme", "");
    	
    	// Alert is restricted during to have only event, iphdr and siganture
    	
    	child = new TreeParent(tablename, "");
    	
    	// create Event
        to = new TreeObject("cid				Unique number of each Alert/Log", Event.CID);
        child.addChild(to);
        to = new TreeObject("sid				Unique number of esach sensor", Event.SID);
        child.addChild(to);
        to = new TreeObject("timestamp		Time stamp for Alert log", Event.TIMESTAMP);
        child.addChild(to);
        
        // create Signature
        to = new TreeObject("name			The signature description", Signature.NAME);
        child.addChild(to);
        to = new TreeObject("classname		The signature class of attack name", Signature.CLASSNAME);
        child.addChild(to);
        to = new TreeObject("priority			Priority for the Signature", Signature.PRIORITY);
        child.addChild(to);
        

        
        // create IPheader
        to = new TreeObject("Dst Address", IPheader.DST);
        child.addChild(to);
        to = new TreeObject("Src Address", IPheader.SRC);
        child.addChild(to);
        to = new TreeObject("plotocoll", IPheader.PROTO);
        child.addChild(to);
        


        
        root.addChild(child);
        
        return root;
    }
}