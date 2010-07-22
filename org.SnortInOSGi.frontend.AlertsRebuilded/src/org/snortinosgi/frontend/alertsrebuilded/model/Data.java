/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.frontend.alertsrebuilded.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data provide a simple transformation from a sql-metadata string to list of strings
 */
public class Data {
	public String data;
	public String []datas;
	public int sid;
	
	public Data(String d, int sid) {
		data=d;
		datas=data.split("\\|");
		this.sid=sid;
	}

	
	public static List<Data> String2DataList(List<String> list, int sid) {
		List<Data> datas = new ArrayList<Data>();
		for(String str : list) {
			datas.add(new Data(str, sid));
		}
		return datas;
	}
}
