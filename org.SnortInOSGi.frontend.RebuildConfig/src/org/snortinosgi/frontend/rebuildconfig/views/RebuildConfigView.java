package org.snortinosgi.frontend.rebuildconfig.views;


import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.DataException;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.snortinosgi.frontend.rebuildconfig.Activator;
import org.snortinosgi.frontend.rebuildconfig.model.Model;
import org.snortinosgi.frontend.rebuildconfig.model.TreeObject;
import org.snortinosgi.frontend.rebuildconfig.model.TreeParent;
import org.snortinosgi.frontend.rebuildconfig.provider.ViewContentProvider;
import org.snortinosgi.frontend.rebuildconfig.provider.ViewLabelProvider;


public class RebuildConfigView extends ViewPart {
	public static final String ID = "org.snortinosgi.frontend.rebuildconfig.views.RebuildConfigView";
	private TreeViewer viewer;
	private TreeViewer backupviewer;
	private TreeParent backuproot;
	private Text txtDatabasename, txtTablename, txtFromCid, txtToCid, txtFromIP, txtToIP;
	private Button deleteItems;
	private Shell lShell;
	
	private long fromCid, toCid;
	private String backupstring;
	private IRebuildDB rebuilder;
	private String dbname;
	private String tablename;
	
	private boolean deleteAlerts;
	private boolean copyEnded;

	public void createPartControl(Composite parent) {
		GridData gd;
		GridLayout layout;
		backuproot = Model.createAlertBackupSchemaModel("data");
		
		lShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.numColumns   = 2;
		parent.setLayout(layout);
		
		Composite left = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;
		left.setLayout(layout);
		gd = new GridData(GridData.FILL_VERTICAL);
		left.setLayoutData(gd);
		
		Composite right = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 4;
		right.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		right.setLayoutData(gd);
		
		// set the left side
		viewer = new TreeViewer(left, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(Model.createAlertSchemaModel());
		viewer.expandAll();

		// set the right side
		Label l = new Label(right, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=4;
		l.setLayoutData(gd);
		l.setText("Copy to the new database as a backup.");
		
		l = new Label(right, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		l.setText("give the new database name: ");
		
		txtDatabasename = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		txtDatabasename.setLayoutData(gd);
		txtDatabasename.setText("zzztestSnort1");
		
		l = new Label(right, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		l.setText("give the new table name: ");
		
		txtTablename = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		txtTablename.setLayoutData(gd);
		txtTablename.setText("data");
		txtTablename.addListener(SWT.Verify, new Listener() {  // dont allow spaces
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if ((chars[i] == ' ')) {
						e.doit = false;
						return;
					}
				}
			}
		});
		txtTablename.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text tmp = (Text)e.widget;
				String txt = tmp.getText();
				TreeParent parent = (TreeParent)backuproot.getChildren()[0];
				parent.setName(txt);
				backupviewer.refresh();
			}
		});
		
		Composite compViewer = new Composite(right, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;
		compViewer.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan=4;
		compViewer.setLayoutData(gd);
		// set the right side
		backupviewer = new TreeViewer(compViewer, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);		
		backupviewer.setContentProvider(new ViewContentProvider());
		backupviewer.setLabelProvider(new ViewLabelProvider());

		backupviewer.setInput(backuproot);
		backupviewer.expandAll();
		
		
	    // the add button add from the left tree to the rihgt tree
	    Button add = new Button(right, SWT.PUSH);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		add.setLayoutData(gd);
	    add.setText("Add");
	    add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if((txtDatabasename.getText().length()==0)||(txtTablename.getText().length()==0)) {
					DialogError("You must set the Databasename or Tablename");
					return;
				}
				ISelection select = viewer.getSelection();
				if(select.isEmpty()) {
			           //System.out.println("is empty");
			           return;
			    }
				if(select instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)select;
					for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						
						if(obj instanceof TreeParent ) {
							continue;
						}
						if(obj instanceof TreeObject ) {
							TreeObject field = (TreeObject)obj;
							//System.out.println("trObjname: "+field.getName()+"   tag: "+field.getTag());
							TreeParent trpar = (TreeParent)backuproot.getChildren()[0];
							trpar.addChild(new TreeObject(field.getName(), field.getTag()));
						}
					}
					backupviewer.refresh();
					backupviewer.expandAll();
				}
			}
		});

	    // del a selected field in the right three
	    Button del = new Button(right, SWT.PUSH);
	    del.setText("Delete");
	    del.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				ISelection select = backupviewer.getSelection();
				if(select.isEmpty()) {
			           //System.out.println("is empty");
			           return;
			    }
				if(select instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)select;
					for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
						Object obj = iterator.next();
						if(obj instanceof TreeObject ) {
							TreeObject field = (TreeObject)obj;
							TreeParent trpar = (TreeParent)backuproot.getChildren()[0];
							trpar.removeChild(field);
						}
					}
					backupviewer.refresh();
				}
			}
		});
	    
	    // del all fields in the right tree 
	    Button delAll = new Button(right, SWT.PUSH);
	    delAll.setText("Delete All");
	    delAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				TreeParent trpar = (TreeParent)backuproot.getChildren()[0];
				for(TreeObject trobj : trpar.getChildren()) {
					trpar.removeChild(trobj);
				}
				backupviewer.refresh();
			}
		});
	    
	    deleteItems = new Button(right, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=4;
		deleteItems.setLayoutData(gd);
		deleteItems.setText("delete the data from Snort main database");
		deleteItems.setSelection(false);

		l = new Label(right, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		l.setText("Range of cid (from, to)");
		
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
		
		txtFromCid = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=1;
		txtFromCid.setLayoutData(gd);
		txtFromCid.setText("0");
		txtFromCid.addListener(SWT.Verify, digitListener);
		
		txtToCid = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=1;
		txtToCid.setLayoutData(gd);
		txtToCid.setText("100");
		txtToCid.addListener(SWT.Verify, digitListener);
		
		l = new Label(right, SWT.WRAP);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=2;
		l.setLayoutData(gd);
		l.setText("Range of IP (from, to)");
		
		Listener ipListener = new Listener() {  // allow only numbers
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9' || chars[i]=='.')) {
						e.doit = false;
						return;
					}
				}
			}
		};
		
		txtFromIP = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=1;
		txtFromIP.setLayoutData(gd);
		txtFromIP.setText("0.0.0.0");
		txtFromIP.addListener(SWT.Verify, ipListener);
		
		txtToIP = new Text(right, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=1;
		txtToIP.setLayoutData(gd);
		txtToIP.setText("255.255.255.255");
		txtToIP.addListener(SWT.Verify, ipListener);
	    
		
		
	    // create the string for the backup and call a thread method on rebuildDB
	    Button backup = new Button(right, SWT.PUSH);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan=4;
		backup.setLayoutData(gd);
	    backup.setText("Send for Backup");
	    backup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
								
				// at first test the range of cids and IPs. Then the correctness of IPs.
				fromCid = Long.valueOf(txtFromCid.getText()).longValue();
				toCid   = Long.valueOf(txtToCid.getText()).longValue();
				if(fromCid>toCid) {
					DialogError("The from Cid must be lower than the to Cid!");
					return;
				}
				
				if((toCid-fromCid)>1000) {
					DialogError("Max allowed only a difference of 1000 cids copy!");
					return;
				}
				
				long fromIP = HelpFunctions.String2ipLong(txtFromIP.getText());
				long toIP = HelpFunctions.String2ipLong(txtToIP.getText());
				if(fromIP>toIP) {
					DialogError("The from IP must be lower than the to IP!");
					return;
				}
				if( fromIP<0 || toIP<0) {
					DialogError("There is an Error in the IP input!");
					return;
				}
											
				dbname = txtDatabasename.getText();
				tablename = txtTablename.getText();
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(tablename+":");
				TreeParent parent = (TreeParent)backuproot.getChildren()[0];
				boolean voidTree = true;
				for (TreeObject child : parent.getChildren()) {
					buffer.append(child.getTag()+";");
					voidTree = false;
				}
				if(voidTree) {
					DialogError("There are no items in Backup (right tree). Please select some fields");
					return;
				}
				String tmp = buffer.toString();
				backupstring = tmp.substring(0, tmp.length()-1);
				
				// test for eventcid and eventsid, they must be there 
				if(!backupstring.matches(".*"+org.SnortInOSGi.openAPI.snortschema.Event.CID+".*") ||
						!backupstring.matches(".*"+org.SnortInOSGi.openAPI.snortschema.Event.SID+".*")) {
					DialogError("You must have Eventcid and Eventsid fields in your database!!");
					return;
				}
					
				
				// get the rebuilder again, 
				rebuilder = getRebuilder();
				if(rebuilder==null)
					return;
				
				// test if the table exists
				rebuilder.setDatabase(dbname);
				if(rebuilder.testTableExists(tablename)==IRebuildDB.ERROR) {
					if( DialogWarning("The table doensnot exists. Should be created?  Cancel to abort Backup.") != SWT.OK ) 
						return;
					rebuilder = getRebuilder();  // because the rebuilder can get lost during the wait time
					if(rebuilder==null)
						return;
					
					String sqlcreate = getSQLCreate(backupstring);
					
					if ( rebuilder.createTable(dbname, sqlcreate) != IRebuildDB.OK ) {
						// maybe is there no database exists
						rebuilder.createDatabase(dbname);   // attemp to create database then table
						if ( rebuilder.createTable(dbname, sqlcreate) != IRebuildDB.OK ) { 
							DialogError("Error on create table.");
							return ;
						}
					}
					rebuilder.insertMetadata(dbname, backupstring);
					
				}
				
				// test if the metadata and backupstring are equal
				String metadatastring = rebuilder.getMetadata(dbname, tablename);
				if( metadatastring==null || !metadatastring.equalsIgnoreCase(backupstring)) {  
					DialogError("Error your fields doesnt equal to exists metadata. Should be ["+metadatastring+"]");
					return ;
				}
				
				deleteAlerts=false;
				if(deleteItems.getSelection()) {
					// FIXME at time we dont implement delete 
					if(DialogWarning("Do you want really to delete old items?")!=SWT.OK) 
						return;
					deleteAlerts=true;
				}
				
				// get the rebuilder again, 
				rebuilder = getRebuilder();
				if(rebuilder==null) 
					return;
				
				// now start the copy 
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(lShell);
				try {
					dialog.run(true, true, new IRunnableWithProgress(){
					 

						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask("Please wait, backup in progress ...", 1000);
							
							copyEnded = false;
							new Thread() {
								public void run() {
									if(deleteAlerts)
										rebuilder.copyAlertsAndDelete(fromCid, toCid, 1,backupstring, dbname);
									else
										rebuilder.copyAlerts(fromCid, toCid, 1,backupstring, dbname);
									copyEnded = true;
								}
							}.start();
							
							long cids = toCid - fromCid;
							long diff = 100; // time difference 60 ms  => this time must be in future dynamically calculated
							
							long increment = 4*1000/cids;
							
							
							while (!copyEnded) {
								Thread.sleep(4*diff);
								monitor.worked((int)increment);
							}
							monitor.worked(1000);
							Thread.sleep(diff);
					        monitor.done();
							
						}
					});
				} catch (InvocationTargetException e1) {
					DialogError("Canot call Invocation Target (canot call the copyAlerts method)");
					return;
				} catch (InterruptedException e1) {
					DialogError("copyAlerts method was interupted");
				}
				
			}
		});
	}
	
	public IRebuildDB getRebuilder() {
		IRebuildDB rebuilder = Activator.getDefault().getRebuildDB();
		if(rebuilder == null) {
			DialogError("Cannot connect to Rebuild on DBAnalyzer!");
			return null;
		}
		return rebuilder;
	}
	
	public String getSQLCreate(String sqlstring) {
		Alert alert = new Alert();
		alert.event= new org.SnortInOSGi.openAPI.snortschema.Event();
		alert.iphdr= new IPheader();
		alert.signature = new Signature();
		
		String sql =null;
		try {
			sql = alert.getSQLCreate(sqlstring);
			//System.out.println("sql: "+sql);
		} catch (DataException e) {
			//e.printStackTrace();
			return null;
		}
		
		return sql;
	}
	
	public void DialogError(String message) {
		MessageBox messageBox = new MessageBox(this.lShell,  SWT.CANCEL);
		messageBox.setText("Error");
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	public int DialogWarning(String message) {
		MessageBox messageBox = new MessageBox(this.lShell,  SWT.OK | SWT.CANCEL);
		messageBox.setText("Warning");
		messageBox.setMessage(message);
		return messageBox.open();
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}