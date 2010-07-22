package org.snortinosgi.frontend.snortinosgigui.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.snortinosgi.frontend.snortinosgigui.Activator;
import org.snortinosgi.frontend.snortinosgigui.model.CommandView;
import org.snortinosgi.frontend.snortinosgigui.model.Sensor;
import org.snortinosgi.frontend.snortinosgigui.model.Sensors;

public class View extends ViewPart {
	public static final String ID = "org.SnortInOSGi.frontend.SnortInOSGiGUI.view";
	//public static final String xmlSensorsFile = "/home/ray/daten/dissertation/ConfigSensors.xml";

	private TreeViewer viewer;

		
	class ViewContentProvider implements IStructuredContentProvider,  ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof CommandView) {
				return ((CommandView) child).parent;
			} else if (child instanceof Sensor) {
				return ((Sensor) child).parent;
			} 
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof Sensors) {
				return ((Sensors) parent).getChildren();
			} else if (parent instanceof Sensor) {
				return ((Sensor) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof Sensors)
				return ((Sensors) parent).hasChildren();
			if (parent instanceof Sensor)
				return ((Sensor) parent).hasChildren();
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof Sensor)
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		Sensors sensors = new Sensors(Activator.getDefault().xmlSensorsFile);
		viewer.setInput(sensors);
		viewer.expandAll();
		
		// 
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
			       // if the selection is empty clear the label
			       if(event.getSelection().isEmpty()) {
			    	   System.out.println("event.getSelection().isEmpty()");
			           return;
			       }
			       if(event.getSelection() instanceof IStructuredSelection) {
			           IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			           Object obj = selection.getFirstElement();
			           
			           if( obj instanceof CommandView) {
			        	   CommandView cv = (CommandView)obj;
			        	   try {
			        		   Sensor sensor = cv.getParent();
			        		   String secondID = "sensor.id."+sensor.ipaddr+"_"+sensor.sid;
			        		   
			        		   
			        		   PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			        		   		showView(cv.ViewID, secondID, IWorkbenchPage.VIEW_ACTIVATE);
			        	   } catch (PartInitException e) {
			        		   System.out.println("canot show view: "+cv.ViewID);
			        		   Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			        		   MessageDialog.openError(shell, "Error", "Error: can't open view: "+cv.ViewID);
			        		   //e.printStackTrace();
			        	   }
			           }
			       }
			   }
			});
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}