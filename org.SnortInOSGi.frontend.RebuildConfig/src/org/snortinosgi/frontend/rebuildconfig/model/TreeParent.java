/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.rebuildconfig.model;

/**
 *
 */
import java.util.ArrayList;

public class TreeParent extends TreeObject {
	private ArrayList<TreeObject> children;
	
	public TreeParent(String name, String tag) {
		super(name, tag);
		children = new ArrayList<TreeObject>();
	}
	
	public void addChild(TreeObject child) {
		for(TreeObject obj : getChildren()) {  
			// if there another child with the same name => ignore
			if(obj.getName().equalsIgnoreCase(child.getName()))
				return;
		}
		children.add(child);
		child.setParent(this);
	}
	
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	
	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	}
	
	public boolean hasChildren() {
		return children.size()>0;
	}
}
