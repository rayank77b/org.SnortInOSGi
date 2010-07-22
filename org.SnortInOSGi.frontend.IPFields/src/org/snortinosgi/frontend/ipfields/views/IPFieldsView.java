package org.snortinosgi.frontend.ipfields.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class IPFieldsView extends ViewPart {
	public static final String ID = "org.SnortInOSGi.frontend.IPFields.view";

	public int sid;

	private Composite top;
	
	private Text txtSrcFrom1, txtSrcFrom2, txtSrcFrom3, txtSrcFrom4;
	private Text txtSrcTo1, txtSrcTo2, txtSrcTo3, txtSrcTo4;
	private Text txtDstFrom1, txtDstFrom2, txtDstFrom3, txtDstFrom4;
	private Text txtDstTo1, txtDstTo2, txtDstTo3, txtDstTo4;
	
	private Button send;	
	
	private Canvas drawPlace;
	private boolean drawData=false;
	
	int [][] datas = new int[8][6];
	
	@Override
	public void createPartControl(Composite parent) {
		GridData gd;
		
		String secID = this.getViewSite().getSecondaryId();
		
		String []secIDs = secID.split("_");
		
		sid = Integer.valueOf(secIDs[1]);
		
		this.setPartName("IPFields: "+sid);
		
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
		layout.numColumns = 9;
		banner.setLayout(layout);
		
		Label l = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=4;
		l.setLayoutData(gd);
		l.setText("Source From"); 
		
		l = new Label(banner, SWT.NONE);
		l.setText(""); 
		
		l = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=4;
		l.setLayoutData(gd);
		l.setText("Source To");

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
		
		txtSrcFrom1 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcFrom1.setLayoutData(gd);
		txtSrcFrom1.setText("001");
		txtSrcFrom1.addListener(SWT.Verify, digitListener);
		
		txtSrcFrom2 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcFrom2.setLayoutData(gd);
		txtSrcFrom2.setText("000");
		txtSrcFrom2.addListener(SWT.Verify, digitListener);
		
		txtSrcFrom3 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcFrom3.setLayoutData(gd);
		txtSrcFrom3.setText("000");
		txtSrcFrom3.addListener(SWT.Verify, digitListener);
		
		txtSrcFrom4 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcFrom4.setLayoutData(gd);
		txtSrcFrom4.setText("000");
		txtSrcFrom4.addListener(SWT.Verify, digitListener);
		
		l = new Label(banner, SWT.NONE);
		l.setText(" - "); 
		
        txtSrcTo1 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcTo1.setLayoutData(gd);
		txtSrcTo1.setText("255");
		txtSrcTo1.addListener(SWT.Verify, digitListener);
		
		txtSrcTo2 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcTo2.setLayoutData(gd);
		txtSrcTo2.setText("255");
		txtSrcTo2.addListener(SWT.Verify, digitListener);
		
		txtSrcTo3 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcTo3.setLayoutData(gd);
		txtSrcTo3.setText("255");
		txtSrcTo3.addListener(SWT.Verify, digitListener);
		
		txtSrcTo4 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtSrcTo4.setLayoutData(gd);
		txtSrcTo4.setText("255");
		txtSrcTo4.addListener(SWT.Verify, digitListener);
		
		///////////////////////////////////////////////////////////
		// next line
		l = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=4;
		l.setLayoutData(gd);
		l.setText("Destination From"); 
		
		l = new Label(banner, SWT.NONE);
		l.setText(""); 
		
		l = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=4;
		l.setLayoutData(gd);
		l.setText("Destination To");
		
		txtDstFrom1 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstFrom1.setLayoutData(gd);
		txtDstFrom1.setText("001");
		txtDstFrom1.addListener(SWT.Verify, digitListener);
		
		txtDstFrom2 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstFrom2.setLayoutData(gd);
		txtDstFrom2.setText("000");
		txtDstFrom2.addListener(SWT.Verify, digitListener);
		
		txtDstFrom3 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstFrom3.setLayoutData(gd);
		txtDstFrom3.setText("000");
		txtDstFrom3.addListener(SWT.Verify, digitListener);
		
		txtDstFrom4 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstFrom4.setLayoutData(gd);
		txtDstFrom4.setText("000");
		txtDstFrom4.addListener(SWT.Verify, digitListener);
		
		l = new Label(banner, SWT.NONE);
		l.setText(" - "); 
		
		txtDstTo1 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstTo1.setLayoutData(gd);
		txtDstTo1.setText("255");
		txtDstTo1.addListener(SWT.Verify, digitListener);
		
		txtDstTo2 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstTo2.setLayoutData(gd);
		txtDstTo2.setText("255");
		txtDstTo2.addListener(SWT.Verify, digitListener);
		
		txtDstTo3 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstTo3.setLayoutData(gd);
		txtDstTo3.setText("255");
		txtDstTo3.addListener(SWT.Verify, digitListener);
		
		txtDstTo4 =  new Text(banner, SWT.SINGLE |SWT.BORDER);
		gd = new GridData(40, 20);
		txtDstTo4.setLayoutData(gd);
		txtDstTo4.setText("255");
		txtDstTo4.addListener(SWT.Verify, digitListener);

		l = new Label(banner, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan=8;
		l.setLayoutData(gd);
		l.setText(" "); 
		
		send = new Button(banner, SWT.PUSH);
		send.setText("  Send  ");
		send.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  // add action here 
		    	  
		    	  drawPlace.redraw();
		    	 
		        }
	      });
		
		/////////////////////////////
		// the draw area
		
		drawPlace = new Canvas(top, SWT.None);
		gd = new GridData(810, 610);
		drawPlace.setLayoutData(gd);
		drawPlace.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				//System.out.println("-");
	           e.gc.setBackground(top.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	           // draw the axis
	           //e.gc.drawLine(20,20,20,580);
	           //e.gc.drawLine(20,580,780,580);
	           
	           // draw the data
	           //e.gc.drawLine(20,20,780,580);
	           Display display=top.getDisplay();
	           Font font = new Font(display,"Arial",14,SWT.BOLD | SWT.ITALIC); 
    		   e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
    		   e.gc.setFont(font); 
	           for(int x=0; x<8; x++) {
	        	   for(int y=0; y<6; y++) {
	        		   int cnt=datas[x][y];
	        		   e.gc.drawRectangle(x*100, 600-y*100, (x+1)*100,600-(y+1)*100);
	        		   e.gc.drawText(Integer.toString(cnt), x*100+50, 600-(y*100+50));
	        	   }
	           }
	           font.dispose();
				
			}
	    });
		
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
