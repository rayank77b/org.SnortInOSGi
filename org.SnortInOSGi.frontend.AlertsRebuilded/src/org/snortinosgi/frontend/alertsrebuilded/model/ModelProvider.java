/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alertsrebuilded.model;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;

public class ModelProvider {

	private static ModelProvider content;
	private List<Data> alerts;
	private TableViewer viewer;
	private String[] titles;

	private ModelProvider(int sid) {
		alerts = new ArrayList<Data>();
		
			
	}

	public static synchronized ModelProvider getInstance(int sid) {
		if (content != null) {
			return content;
		}
		content = new ModelProvider(sid);
		return content;
	}

	public List<Data> getAlerts() {
		return alerts;
	}
	
	public void setAlerts(List<Data> alerts) {
		this.alerts = alerts;
	}
	
	public void setViewer(TableViewer v) {
		viewer = v;
	}
	public TableViewer getViewer() {
		return viewer;
	}
	public void setTitles(String[]titles) {
		this.titles=titles;
	}
	public String[] getTitles() {
		return this.titles;
	}
}