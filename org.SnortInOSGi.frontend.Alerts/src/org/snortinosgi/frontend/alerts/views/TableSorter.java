/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alerts.views;

import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 *
 */
public class TableSorter extends ViewerSorter {
	
	public static final int CI_CID = 0;
	public static final int CI_SRC = 1;
	public static final int CI_DST = 2;
	public static final int CI_PROTOCOL = 3;
	public static final int CI_ALERT = 4;
	public static final int CI_PIORIOTY = 5;
	
	private int columnIndex;
	// private static final int ASCENDING = 0;
	private static final int DESCENDING = 1;

	private int direction = DESCENDING;

	public TableSorter() {
		this.columnIndex = 0;
		direction = DESCENDING;
	}

	public void setColumn(int column) {
		if (column == this.columnIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.columnIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Alert alerts1 = (Alert) e1;
		Alert alerts2 = (Alert) e2;
		
		int rc = 0;
		switch (columnIndex) {
		case CI_CID:
			rc = alerts1.event.cid>alerts2.event.cid?1:-1;
			break;
		case CI_SRC:
			rc = alerts1.iphdr.src>alerts2.iphdr.src?1:-1;
			break;
		case CI_DST:
			rc = alerts1.iphdr.dst>alerts2.iphdr.dst?1:-1;
			break;
		case CI_PROTOCOL:
			rc = alerts1.iphdr.proto>alerts2.iphdr.proto?1:-1;
			break;
		case CI_ALERT:
			rc = alerts1.signature.name.compareTo(alerts2.signature.name);
			break;
		case CI_PIORIOTY:
			rc = alerts1.signature.priority>alerts2.signature.priority?1:-1;
			break;
		default:
			rc = 0;
		}

		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}
}
	