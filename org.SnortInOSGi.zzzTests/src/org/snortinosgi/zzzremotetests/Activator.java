package org.snortinosgi.zzzremotetests;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.interfaces.getter.Getter;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.Event;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private BundleContext context;

	private int backendPort;
	private String backendIP;
	private int dbanalyzerPort;
	private String dbanalyzerIP;	
		
	public void start(BundleContext context) throws Exception {
		this.context=context;
		
		String strPort = System.getProperty("org.SnortInOSGi.backend.remotePort");
		backendPort = Integer.valueOf(strPort);
		backendIP = System.getProperty("org.SnortInOSGi.backend.remoteIP");
		
		strPort = System.getProperty("org.SnortInOSGi.dbanalyzer.remotePort");
		dbanalyzerPort = Integer.valueOf(strPort);
		dbanalyzerIP = System.getProperty("org.SnortInOSGi.dbanalyzer.remoteIP");
		
		System.out.println("Consumer: get backend    data < "+backendIP+":"+ backendPort+" >");
		System.out.println("Consumer: get dbanalyzer data < "+dbanalyzerIP+":"+ dbanalyzerPort+" >");
		
		System.out.println("Consumer: get the Service Reference RemoteOSGiService.");

		
		ISMDBreader reader = getReader();

        System.out.println("-------");
        String tmp = reader.getAlert(400L, 1);
        Alert alert = (Alert)Data.getFromXML(tmp, Alert.class);
        System.out.println(alert);
       
		// insert into event values(1,9966, 38, '2008-05-19 17:21:39');
        // select * from event where cid=9966;
        tmp = reader.getEvent(9000L, 1);
        Event event = (Event)Data.getFromXML(tmp, Event.class);
        System.out.println(event);
        
        IRebuildDB rebuilder = getRebuildDB();
        
        if( rebuilder.createDatabase("zzzmytest")!= IRebuildDB.OK )
        	System.out.println("Error on create database.");
        if ( rebuilder.createTable("zzzmytest", "CREATE TABLE blub(dbname VARCHAR(100) NOT NULL, sqlstring TEXT NOT NULL)" ) != IRebuildDB.OK ) 
			System.out.println("Error on create table.");
		
        System.out.println("table should be created.");
        /*
        ISMDBwriter writer = getWriter();
        System.out.println("delete Event");
        writer.deleteEvent(9966L, 1);
        */
        /*
        IRebuildDB rebuilder = getRebuildDB();
        String metadatastring = rebuilder.getMetadata("zzztestSnort1", "data2");
        System.out.println("meta: "+metadatastring);
        
        //data2:eventcid;eventsid;eventtimestamp;signaturename;signatureclassname;signaturepriority;iphdrdst;iphdrsrc;iphdrproto
        rebuilder.copyAlerts(400, 401, 1,metadatastring, "zzztestSnort1");
        
        IDBAreader dbareader = getDBAreader();
        tmp = dbareader.getDataForCid(400, metadatastring, "zzztestSnort1");
        System.out.println("results: "+tmp);
        */
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Consumer: stoped.");
	}
	
//#####################################################################################################################################	
	public ISMDBreader getReader() {
		return Getter.getReader(context, backendPort, backendIP);
	}

	public ISMDBwriter getWriter() {
		return Getter.getWriter(context, backendPort, backendIP);
	}

	public IRebuildDB getRebuildDB() {
		return Getter.getRebuildDB(context, dbanalyzerPort, dbanalyzerIP);
	}
	
	public IDBAreader getDBAreader() {
		return Getter.getDBAreader(context, dbanalyzerPort, dbanalyzerIP);
	}
}
