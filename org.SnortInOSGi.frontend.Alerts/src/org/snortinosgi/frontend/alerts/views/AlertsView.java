package org.snortinosgi.frontend.alerts.views;

import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.snortinosgi.frontend.alerts.Activator;


public class AlertsView extends ViewPart {
	public static final String ID = "org.snortinosgi.frontend.alerts.views.AlertsView";
	
	public static final String alertPacketView ="org.snortinosgi.frontend.alertpacketdetails.views.AlertDetailsView";
	
	public int sid;

	private TableViewer tableviewer;
	private TableSorter tableSorter;

	private Text fromcid = null;
	private Text tocid;
	private Text fromtime;
	private Text totime;
	private Button send;
	private Label outtime;

	

	public void createPartControl(Composite parent) {
		GridData gd;
		
		String secID = this.getViewSite().getSecondaryId();
		
		String []secIDs = secID.split("_");
		
		sid = Integer.valueOf(secIDs[1]);
		
		this.setPartName("Alerts: "+sid);
		
		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);
		
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.numColumns = 4;
		banner.setLayout(layout);
		
		Label l = new Label(banner, SWT.NONE);
		l.setText(""); 
		l = new Label(banner, SWT.NONE);
		l.setText("from"); 
		l = new Label(banner, SWT.NONE);
		l.setText("to");
		l = new Label(banner, SWT.NONE);
		l.setText("send request");
		l = new Label(banner, SWT.NONE);
		l.setText("Cid");
		
		Listener digitListener = new Listener() {  // allow only numbers
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		};
		
		fromcid =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(100, 20);
		fromcid.setLayoutData(gd);
		fromcid.setText("0");
		fromcid.addListener(SWT.Verify, digitListener);
		
		tocid =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(100, 20);
		tocid.setLayoutData(gd);
		tocid.setText("0");
		tocid.addListener(SWT.Verify, digitListener);
		
		l = new Label(banner, SWT.NONE);
		l.setText("");
		l = new Label(banner, SWT.NONE);
		l.setText("Time");
		
		// FIXME create another form of time input
		fromtime =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(100, 20);
		fromtime.setLayoutData(gd);
		fromtime.setText("0");
		
		totime =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(100, 20);
		totime.setLayoutData(gd);
		totime.setText("0");
		
		send = new Button(banner, SWT.PUSH);
		send.setText("  Send  ");
		send.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  	send.setText("Wait..");
		    	  	send.redraw();
		            //System.out.println(e);
		            long fcid = Long.valueOf(fromcid.getText()).longValue();
					long tcid = Long.valueOf(tocid.getText()).longValue();
					
					long startTime = System.currentTimeMillis();

		            
					ISMDBreader reader = Activator.getDefault().getReader();
					
					if (reader != null) {
						if (fcid < 1)
							fcid = 1;
						if (tcid <= fcid)
							tcid = fcid + 20;
	
						//System.out.println("sid: "+sid);
						List<Alert> alerts = HelpFunctions.convertXML2AlertList(reader.getAlerts(fcid, tcid, sid));
						ModelProvider.getInstance(sid).setAlerts(alerts);
						tableviewer.setInput(ModelProvider.getInstance(sid).getAlerts());
						tableviewer.refresh();
						
					} 
					long stopTime = System.currentTimeMillis();
					System.out.println("from: "+fcid+"  diff: " +(tcid-fcid)+" => "+((stopTime-startTime)/1000.0)+ " sec");
					double erg =((stopTime-startTime)/1000.0);
					outtime.setText("from: "+fcid+"  diff: " +(tcid-fcid)+" => "+erg+ " sec");
					
					send.setText("  Send  ");
		        }
		      });
		
		outtime = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=4;
		outtime.setLayoutData(gd);
		outtime.setText("from: xxxxxxxxxxx  diff: xxxxxx => xxxxxxxxx sec");
		
		
		tableviewer = new TableViewer(top, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		//tableviewer.setLayoutData(new GridData(GridData.FILL_BOTH));
		String[] titles = {"cid", "SRC", "DST", "Proto", "Alert", "Prio"};
		int[] bounds = { 70, 200, 200, 70, 500, 50 };
		for (int i = 0; i < titles.length; i++) {
			final int index = i;
			final TableViewerColumn columnviewer = new TableViewerColumn(tableviewer, SWT.NONE);
			final TableColumn column = columnviewer.getColumn();
			column.setText(titles[i]);
			column.setWidth(bounds[i]);
			column.setResizable(true);
			column.setMoveable(true);
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					tableSorter.setColumn(index);
					int dir = tableviewer.getTable().getSortDirection();
					if (tableviewer.getTable().getSortColumn() == column) {
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {

						dir = SWT.DOWN;
					}
					tableviewer.getTable().setSortDirection(dir);
					tableviewer.getTable().setSortColumn(column);
					tableviewer.refresh();
				}
			});

		}
		Table table = tableviewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableviewer.setContentProvider(new AlertsContentProvider());
		tableviewer.setLabelProvider(new AlertsLabelProvider());
		
		ModelProvider.getInstance(sid).setViewer(tableviewer);
		tableviewer.setInput(ModelProvider.getInstance(sid).getAlerts());
		
		tableSorter = new TableSorter();
		tableviewer.setSorter(tableSorter);

		tableviewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
					Object selectedItem = ((StructuredSelection)event.getSelection()).getFirstElement();
					//System.out.println(selectedItem);
					if(selectedItem instanceof Alert) {
						//System.out.println("Alert");
						Alert alert = (Alert)selectedItem;
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
								showView(alertPacketView, alertPacketView+"_"+alert.event.sid+"_"+alert.event.cid,  IWorkbenchPage.VIEW_ACTIVATE);
						} catch (PartInitException e) {
							//e.printStackTrace();
							System.out.println("ERROR: cant call AlertPacketDetails with "+alert.event.sid+":"+alert.event.cid);
						}
					}
			}
		});

		// FIXME tooltip listener implementieren
	}


	public void setFocus() {
		tableviewer.getControl().setFocus();
	}
}