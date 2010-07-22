/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.rebuildconfig.model;

/**
 *
 */

public class TreeObject {
	private String name;
	private TreeParent parent;
	private String tag;
	
	public TreeObject(String name, String tag) {
		this.name = name;
		this.tag = tag;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getTag() {
		return tag;
	}
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}
}
