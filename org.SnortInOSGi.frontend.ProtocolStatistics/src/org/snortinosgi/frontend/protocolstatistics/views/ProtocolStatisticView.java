package org.snortinosgi.frontend.protocolstatistics.views;

import java.awt.Color;
import java.awt.Font;
import java.sql.Timestamp;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.snortschema.IPheader;
import org.SnortInOSGi.openAPI.snortschema.Signature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.snortinosgi.frontend.protocolstatistics.Activator;


public class ProtocolStatisticView extends ViewPart {
	public static final String ID = "org.SnortInOSGi.frontend.ProtocolStatistics.View";

	private JFreeChart chart;

	public Label lFromCidStat, lToCidStat, lFromCidPrio, lToCidPrio, lFromHour, lToHour;
	public Label lFromHourInformation, lToHourInformation;
	public ChartComposite frameStatistic, framePriority;
	public Slider sliderFromStat, sliderToStat, sliderFromPrio, sliderToPrio;
	public Slider sliderFromHour, sliderToHour;
	
	public void createPartControl(Composite parent) {
		GridData gd;
		GridLayout layout;
		Group group;
		
		
		long latestcid = getLatestCid();
		if(latestcid<0)
			latestcid=100;
		
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth  = 5;
		layout.numColumns   = 2;
		parent.setLayout(layout);
		
		Composite compProtocols = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		compProtocols.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		compProtocols.setLayoutData(gd);
		
		
		Composite compPriority = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		compPriority.setLayout(layout);
		gd = new GridData(GridData.FILL_BOTH);
		compPriority.setLayoutData(gd);
				
		
		// fill the protocol statistics
		chart = createPieChart(null, null);
		frameStatistic = new ChartComposite(compProtocols, SWT.NONE, chart, true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		frameStatistic.setLayoutData(gd);
		
		// create the from buttons
		group = new Group(compProtocols, SWT.BORDER);
		group.setLayout (new GridLayout(2, false));
	    group.setText("From cid");
		
	    // From Stat
	    sliderFromStat = new Slider(group,SWT.HORIZONTAL);
	    sliderFromStat.setMinimum(0);
	    sliderFromStat.setMaximum((int)latestcid);
	    sliderFromStat.setIncrement(5);
	    sliderFromStat.setPageIncrement(50);
	    sliderFromStat.setSelection(0);
	    sliderFromStat.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromStat.getSelection();
	    		int to   = sliderToStat.getSelection();
	    		if(from<to)
	    			lFromCidStat.setText(String.valueOf(sliderFromStat.getSelection()));
	    		else {
	    			sliderFromStat.setSelection(to);
	    			lFromCidStat.setText(String.valueOf(to));
	    		}
	    	}
	    });
	    
		lFromCidStat = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 100;
		lFromCidStat.setLayoutData(gd);
		lFromCidStat.setAlignment(SWT.RIGHT);
		lFromCidStat.setText(String.valueOf(latestcid-100));
		
		
		
		
		// create the To buttons
		group = new Group(compProtocols, SWT.BORDER);
		group.setLayout (new GridLayout(2, false));
	    group.setText("To cid");
		
	    // To Stat
	    sliderToStat = new Slider(group,SWT.HORIZONTAL);
	    sliderToStat.setMinimum(0);
	    sliderToStat.setMaximum((int)latestcid);
	    sliderToStat.setIncrement(5);
	    sliderToStat.setPageIncrement(50);
	    sliderToStat.setSelection((int)latestcid);
	    sliderToStat.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromStat.getSelection();
	    		int to   = sliderToStat.getSelection();
	    		if(from<to)
	    			lToCidStat.setText(String.valueOf(sliderToStat.getSelection()));
	    		else {
	    			sliderToStat.setSelection(from);
	    			lToCidStat.setText(String.valueOf(from));
	    		}
	    	    
	    	}
	    });
		
		lToCidStat = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 100;
		lToCidStat.setLayoutData(gd);
		lToCidStat.setAlignment(SWT.RIGHT);
		lToCidStat.setText(String.valueOf(latestcid));
		
		
		
		/////////////////////////////////////////////////////////////////////////
		// fill the priority statistics
		framePriority = new ChartComposite(compPriority, SWT.NONE, chart, true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		framePriority.setLayoutData(gd);
		
		// create the from buttons
		group = new Group(compPriority, SWT.BORDER);
		group.setLayout (new GridLayout(2, false));
	    group.setText("From cid");
		
	    // From Prio
	    sliderFromPrio = new Slider(group,SWT.HORIZONTAL);
	    sliderFromPrio.setMinimum(0);
	    sliderFromPrio.setMaximum((int)latestcid);
	    sliderFromPrio.setIncrement(5);
	    sliderFromPrio.setPageIncrement(50);
	    sliderFromPrio.setSelection(0);
	    sliderFromPrio.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromPrio.getSelection();
	    		int to   = sliderToPrio.getSelection();
	    		if(from<to)
	    			lFromCidPrio.setText(String.valueOf(sliderFromPrio.getSelection()));
	    		else {
	    			sliderFromPrio.setSelection(to);
	    			lFromCidPrio.setText(String.valueOf(to));
	    		}
	    	}
	    });
		
		lFromCidPrio = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 100;
		lFromCidPrio.setLayoutData(gd);
		lFromCidPrio.setAlignment(SWT.RIGHT);
		lFromCidPrio.setText(String.valueOf(latestcid-100));
		
		
		// create the To buttons
		group = new Group(compPriority, SWT.BORDER);
		group.setLayout (new GridLayout(2, false));
	    group.setText("To cid");
		
	    // To Stat
	    sliderToPrio = new Slider(group,SWT.HORIZONTAL);
	    sliderToPrio.setMinimum(0);
	    sliderToPrio.setMaximum((int)latestcid);
	    sliderToPrio.setIncrement(5);
	    sliderToPrio.setPageIncrement(50);
	    sliderToPrio.setSelection((int)latestcid);
	    sliderToPrio.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromPrio.getSelection();
	    		int to   = sliderToPrio.getSelection();
	    		if(from<to)
	    			lToCidPrio.setText(String.valueOf(sliderToPrio.getSelection()));
	    		else {
	    			sliderToPrio.setSelection(from);
	    			lToCidPrio.setText(String.valueOf(from));
	    		}
	    	    
	    	}
	    });
		
		lToCidPrio = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 100;
		lToCidPrio.setLayoutData(gd);
		lToCidPrio.setAlignment(SWT.RIGHT);
		lToCidPrio.setText(String.valueOf(latestcid));
		
		
		// fill the timeline of protocols statistics
		chart = createChartBar(null);
		final ChartComposite frameTimeline = new ChartComposite(parent, SWT.NONE, chart, true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		frameTimeline.setLayoutData(gd);
		
		
		// create the from hour 
		group = new Group(parent, SWT.BORDER);
		group.setLayout (new GridLayout(2, false));
	    group.setText("From hour");
		
	    // From hour
	    sliderFromHour = new Slider(group,SWT.HORIZONTAL);
	    sliderFromHour.setMinimum(0);
	    sliderFromHour.setMaximum(130);
	    sliderFromHour.setIncrement(1);
	    sliderFromHour.setPageIncrement(6);
	    sliderFromHour.setSelection(124);
	    sliderFromHour.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromHour.getSelection();
	    		int to   = sliderToHour.getSelection();
	    		if(from<to)
	    			lFromHour.setText("-"+String.valueOf(120-sliderFromHour.getSelection()));
	    		else {
	    			sliderFromHour.setSelection(to);
	    			lFromHour.setText("-"+String.valueOf(120-to));
	    		}
	    	}
	    });
		
		lFromHour = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 50;
		lFromHour.setLayoutData(gd);
		lFromHour.setAlignment(SWT.RIGHT);
		lFromHour.setText("-6");
		
		lFromHourInformation = new Label(parent, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 300;
		lFromHourInformation.setLayoutData(gd);
		lFromHourInformation.setAlignment(SWT.RIGHT);
		lFromHourInformation.setText("...");
		
		
		// create the To hour
		group = new Group(parent, SWT.BORDER);
		group.setLayout (new GridLayout(5, false));
	    group.setText("To hour");
		
	    // To hour
	    sliderToHour = new Slider(group,SWT.HORIZONTAL);
	    sliderToHour.setMinimum(0);
	    sliderToHour.setMaximum(130);
	    sliderToHour.setIncrement(1);
	    sliderToHour.setPageIncrement(6);
	    sliderToHour.setSelection(120);
	    sliderToHour.addSelectionListener( new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		int from = sliderFromHour.getSelection();
	    		int to   = sliderToHour.getSelection();
	    		if(from<to)
	    			lToHour.setText("-"+String.valueOf(120-sliderToHour.getSelection()));
	    		else {
	    			sliderToHour.setSelection(from);
	    			lToHour.setText("-"+String.valueOf(120-from));
	    		}
	    	    
	    	}
	    });
		
		lToHour = new Label(group, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 50;
		lToHour.setLayoutData(gd);
		lToHour.setAlignment(SWT.RIGHT);
		lToHour.setText("0");
		
		lToHourInformation = new Label(parent, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.minimumWidth = 300;
		lToHourInformation.setLayoutData(gd);
		lToHourInformation.setAlignment(SWT.RIGHT);
		lToHourInformation.setText("...");
		
		Button renew = new Button(parent, SWT.PUSH);
		renew.setText("renew data");
		gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.END;
		renew.setLayoutData(gd);
		renew.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				chart = createPieChart(createDatasetLayer4(), "TCP/UDP/ICMP stat");
				frameStatistic.setChart(chart);
				chart.fireChartChanged();
				chart = createPieChart(createDatasetPriority(), "Signature Priority");
				framePriority.setChart(chart);
				chart.fireChartChanged();
				chart = createChartBar(createDatasetTimeline());
				frameTimeline.setChart(chart);
				chart.fireChartChanged();
			}
		});

	}

	public void setFocus() {
	}



	private PieDataset createDatasetLayer4() {
		DefaultPieDataset dataset = null; 
		
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			int sid = 1;
			dataset = new DefaultPieDataset();
				
			long firstcid = Long.valueOf(lFromCidStat.getText());
			long lastcid  = Long.valueOf(lToCidStat.getText());
				
			//System.out.println("firstcid: "+firstcid+ "   lastcid: "+lastcid);
				
			float all = reader.getCountCID(firstcid, lastcid, sid);
			float tcpcnt = reader.getCountCID(firstcid, lastcid, sid, IPheader.TCP);
			float udpcnt = reader.getCountCID(firstcid, lastcid, sid, IPheader.UDP);
			float icmpcnt = reader.getCountCID(firstcid, lastcid, sid, IPheader.ICMP);
				
			dataset.setValue("TCP", new Double(tcpcnt));
			dataset.setValue("UDP", new Double(udpcnt));
			dataset.setValue("ICMP", new Double(icmpcnt));
			dataset.setValue("rest", new Double((all-tcpcnt-udpcnt-icmpcnt)));
			return dataset;
		} else
			return null;
	}
	
	private PieDataset createDatasetPriority() {
		DefaultPieDataset dataset = null; 
		
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			int sid = 1;
			dataset = new DefaultPieDataset();
						
			long firstcid = Long.valueOf(lFromCidPrio.getText());
			long lastcid  = Long.valueOf(lToCidPrio.getText());
				
			float low = reader.getSignaturePriorityCount(Signature.PRIO_LOW, firstcid, lastcid, sid);
			float middle = reader.getSignaturePriorityCount(Signature.PRIO_MIDDLE, firstcid, lastcid, sid);
			float high = reader.getSignaturePriorityCount(Signature.PRIO_HIGH, firstcid, lastcid, sid);
				
			dataset.setValue("High", new Double(high));
			dataset.setValue("Middle", new Double(middle));
			dataset.setValue("Low", new Double(low));
				
			return dataset;
		} else
			return null;		
	}

	private JFreeChart createPieChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, // chart title
				dataset, // data
				false, // include legend
				true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		return chart;

	}
	
	private CategoryDataset createDatasetTimeline() {
		DefaultCategoryDataset dataset = null;
		
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			int sid = 1;
			dataset = new DefaultCategoryDataset();
			
			// row keys...
			String []series = {"TCP", "UDP", "ICMP" };		
			int []protocol = {IPheader.TCP, IPheader.UDP, IPheader.ICMP };
		
			long now = reader.getFirstTimeOfCid(reader.getLastCid(sid), sid);

			// fromhour and tohour are negative!
			int fromhour = Integer.valueOf(lFromHour.getText());
			int tohour = Integer.valueOf(lToHour.getText());
			
			long firstcid = reader.getFirstCidOfTime((now+(fromhour)*1000*60*60), sid);
			long lastcid = reader.getLastCidOfTime((now+tohour*1000*60*60), sid);
				
			lFromHourInformation.setText("from cid: "+firstcid+ "  ("+new Timestamp((now+fromhour*1000*60*60))+")");
			lToHourInformation.setText("to cid: "+lastcid+ "  ("+new Timestamp((now+tohour*1000*60*60))+")");
				
				
			for(int hour=(-1*fromhour); hour>=(-1*tohour); hour--) {
				long fromcid = reader.getFirstCidOfTime((now-(hour+1)*1000*60*60), sid);
				long tocid = reader.getLastCidOfTime((now-hour*1000*60*60), sid);

				//System.out.println("fromcid: "+fromcid+"   tocid: "+tocid);
				
				for(int proto=0; proto<3; proto++) {						
					float cnt = reader.getCountCID(fromcid, tocid, sid, protocol[proto]);
					//System.out.println("hour: "+hour+"   series: "+series[proto]+"   count: "+cnt);
					dataset.addValue(cnt, series[proto], (String.valueOf(-1*hour)));
				}
			}

			return dataset;
		} else
			return null; 
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            dataset.
	 * 
	 * @return A chart.
	 */
	private JFreeChart createChartBar(CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart("Protocols count timeline", // chart
				// title
				"Timeline", // domain axis label
				"Counts", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);
		return chart;

	}

	
	private long getLatestCid() {		
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			int sid = 1;
			return reader.getLastCid(sid);	
		} else
			return -1;
	}
}
