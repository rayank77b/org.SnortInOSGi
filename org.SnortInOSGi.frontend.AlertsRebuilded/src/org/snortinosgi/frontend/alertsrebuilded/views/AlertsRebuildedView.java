package org.snortinosgi.frontend.alertsrebuilded.views;


import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.snortinosgi.frontend.alertsrebuilded.Activator;
import org.snortinosgi.frontend.alertsrebuilded.model.Data;
import org.snortinosgi.frontend.alertsrebuilded.model.ModelProvider;
import org.snortinosgi.frontend.alertsrebuilded.provider.AlertsContentProvider;
import org.snortinosgi.frontend.alertsrebuilded.provider.AlertsLabelProvider;


public class AlertsRebuildedView extends ViewPart {
	public static final String ID = "org.snortinosgi.frontend.alertsrebuilded.views.AlertsRebuildedView";
	public int sid;

	private TableViewer tableviewer;
	private Text fromcid = null;
	private Text tocid;
	private Text fromtime;
	private Text totime;
	
	private Combo metadatas;
	
	//private IRebuildDB rebuilder;
	private IDBAreader reader;
	
	private Composite top;
	//private Composite thisparent;
	private final int TOTALCOLUMNSIZE = 25;
	
	public void createPartControl(Composite parent) {
		GridData gd;
		
		//this.thisparent=parent;
		// get the sens
		String secID = this.getViewSite().getSecondaryId();
		
		String []secIDs = secID.split("_");
		
		sid = Integer.valueOf(secIDs[1]);
		
		this.setPartName("Rebuilded Alerts: "+sid);
		
		top = new Composite(parent, SWT.NONE);
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
		
		l = new Label(banner, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		l.setText("chose one table from the database:");
		l.setLayoutData(gd);
		
		reader = Activator.getDefault().getDBAreader();
		String tmp=null;;
		if(reader!=null)
			 tmp=reader.getAllMetadataInfos();
		else 
			System.out.println("reader is null");
		
		metadatas = new Combo(banner, SWT.DROP_DOWN|SWT.READ_ONLY);
	    String items[] = {"test", "test2", "test3"};
	    if(tmp!=null)
	    	items = tmp.split("\\|");
	    		
	    metadatas.setItems(items);
	    metadatas.select (0);

		
		Button send = new Button(banner, SWT.PUSH);
		send.setText("Send");
		
		
		
		tableviewer = new TableViewer(top, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		for (int i = 0; i < TOTALCOLUMNSIZE; i++) {  // we create TOTALCOLUMNSIZE columns, then deacitvate (visible(false)) unused
			TableColumn column = new TableViewerColumn(tableviewer, SWT.NONE).getColumn();
			column.setText("clmnr "+i);
			column.setWidth(10);
			column.setResizable(false);
			column.setMoveable(false);
			
		}
		Table table = tableviewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableviewer.setContentProvider(new AlertsContentProvider());
		tableviewer.setLabelProvider(new AlertsLabelProvider());
		
		ModelProvider.getInstance(sid).setViewer(tableviewer);
		
		tableviewer.setInput(null);
		
		
		send.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		            
		            long fcid = Long.valueOf(fromcid.getText()).longValue();
					long tcid = Long.valueOf(tocid.getText()).longValue();
		            
					long startTime = System.currentTimeMillis();
					
					String db = metadatas.getText();
					System.out.println("db "+db);
					// db should have "dbname:tablename"
					String [] tmp = db.split("\\:");
					if(tmp==null || tmp.length==0)
						return ;
					
					String dbname = tmp[0];
					String tablename = tmp[1];
					
					reader = Activator.getDefault().getDBAreader();
					String metadatastring=null;
					if(reader!=null)
						 metadatastring=reader.getMetadata(dbname, tablename);
					else 
						System.out.println("reader is null");
					
					
					String []titles=metadatastring.split(";");
					System.out.println("len: "+titles.length);
					
					Table table = tableviewer.getTable();
					
					// set all columns to void
					int i;
					for ( i = 0; i < titles.length; i++) { 
						TableColumn column = table.getColumn(i);
						column.setText(HelpFunctions.getName(titles[i]));
						column.setWidth(HelpFunctions.getBounds(titles[i]));
						column.setResizable(true);
						column.setMoveable(false);
					}
					for(int j=i; j<TOTALCOLUMNSIZE; j++) {
						TableColumn column = table.getColumn(j);
						column.setText("");
						column.setWidth(2);
						column.setResizable(false);
					}
					
					
					IDBAreader reader = Activator.getDefault().getDBAreader();
					if (reader !=null) {
						if (fcid < 1)
							fcid = 1;
						if (tcid <= fcid)
							tcid = fcid + 20;
					
						//System.out.println();
						//System.out.println(fcid+":"+tcid+"  "+metadatastring+"  "+dbname);
						List<String> alerts =reader.getDataForCids(fcid,tcid, tablename+":"+metadatastring, dbname);
						List<Data> data = Data.String2DataList(alerts, sid);
						//System.out.println(alerts);
						ModelProvider.getInstance(sid).setAlerts(data);
						ModelProvider.getInstance(sid).setTitles(titles);
						
						tableviewer.setInput(ModelProvider.getInstance(sid).getAlerts());
						tableviewer.refresh();
					}
					
					long stopTime = System.currentTimeMillis();
					System.out.println("from: "+fcid+"  diff: " +(tcid-fcid)+" => "+((stopTime-startTime)/1000.0)+ " sec");
					
		      }
		});
		
		
		// FIXME tooltip listener implementieren
	}


	public void setFocus() {
		tableviewer.getControl().setFocus();
	}
}