/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.bundle.dbconnector;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Activator of Bundle DBconnector. This Activator register the DBconnector Object to locale service with a tracker on start.
 * FIXME: This activator has a log implementation. Which is not functionaly yet. Should be fixed. 
 */
public class Activator implements BundleActivator {

	private ServiceTracker simpleLogServiceTracker;
	private SimpleLogService simpleLogService;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("---DBconnector Activator started");
		Hashtable<String,String> ht = new Hashtable<String,String>();
		// register the service
		context.registerService(
				SimpleLogService.class.getName(), 
				new SimpleLogServiceImpl(), 
				ht);
		
		// create a tracker and track the log service
		simpleLogServiceTracker = new ServiceTracker(context, SimpleLogService.class.getName(), null);
		simpleLogServiceTracker.open();
		
		// grab the service
		simpleLogService = (SimpleLogService) simpleLogServiceTracker.getService();

		//if(simpleLogService != null)
		//	simpleLogService.log("Yee ha, I'm logging!");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if(simpleLogService != null)
			simpleLogService.log("Yee ha, I'm logging!");
		
		// close the service tracker
		simpleLogServiceTracker.close();
		simpleLogServiceTracker = null;
		
		simpleLogService = null;
	}

}
