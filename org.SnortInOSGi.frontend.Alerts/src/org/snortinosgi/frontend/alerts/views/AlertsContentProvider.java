/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alerts.views;

import java.util.List;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 *
 */
public class AlertsContentProvider implements IStructuredContentProvider {
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
	public void dispose() {
	}
	public Object[] getElements(Object parent) {
		@SuppressWarnings("unchecked")
		List<Alert> alerts = (List<Alert>) parent;
		return alerts.toArray();
	}
}