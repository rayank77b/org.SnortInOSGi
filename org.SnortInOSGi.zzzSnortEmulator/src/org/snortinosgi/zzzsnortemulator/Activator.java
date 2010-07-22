package org.snortinosgi.zzzsnortemulator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

	SnortEmu sn;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		String sfast = System.getProperty("fast");
		int fast = Integer.parseInt(sfast);
		
		System.out.println("fast: "+fast);
		// 100  => 10 alerts/s
		// 20   => 50 alerts/s
		// 10   => 100 alerts/s
		sn = new SnortEmu(fast);
		
		sn.run=true;
		sn.start();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		sn.run=false;
		sn.join();
		
	}
	

}
