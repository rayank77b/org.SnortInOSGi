/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alerts.views;


import java.util.ArrayList;
import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.eclipse.jface.viewers.TableViewer;
import org.snortinosgi.frontend.alerts.Activator;

public class ModelProvider {

	private static ModelProvider content;
	private List<Alert> alerts;
	private TableViewer viewer;

	private ModelProvider(int sid) {
		// create a array of Alerts
		// connect to database and get latest 15 alerts
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null)
			alerts = HelpFunctions.convertXML2AlertList(reader.getLatestAlerts(15, sid));
		else
			alerts = new ArrayList<Alert>();
	}

	public static synchronized ModelProvider getInstance(int sid) {
		if (content != null) {
			return content;
		}
		content = new ModelProvider(sid);
		return content;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}
	
	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public void setViewer(TableViewer v) {
		viewer = v;
	}
	public TableViewer getViewer() {
		return viewer;
	}
}
