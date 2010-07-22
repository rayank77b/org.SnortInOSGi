/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.junittests.openapi.snortschema;

import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;

/**
 *
 */
public class testSplit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String tmp = "event(234:12:3:1234567)";
		System.out.println(tmp);
		
		String []s1 = tmp.split("event");
		System.out.println(s1[0]+" -- "+s1[1]);
		
		tmp = s1[1].replaceFirst("\\(", "");
		System.out.println(tmp);
		
		String []s2 = s1[1].split("\\)");
		System.out.println(s2[0]);
		
		String [] ev = s2[0].split(":");
		
		System.out.println(ev[3]);

		System.out.println("_-----------------------------------------------___");
		System.out.println("192.168.17.1  "+HelpFunctions.String2ipLong("192.168.17.1"));
		System.out.println("1.168.17.1  "+HelpFunctions.String2ipLong("1.168.17.1"));
		System.out.println("192.1.0.1  "+HelpFunctions.String2ipLong("192.1.0.1"));
		
		System.out.println("192.168.1  "+HelpFunctions.String2ipLong("192.168.1"));
		System.out.println("2567.168.17.1  "+HelpFunctions.String2ipLong("2567.168.17.1"));
		System.out.println("256.168.17.1  "+HelpFunctions.String2ipLong("256.168.17.1"));
		System.out.println("25.168.-17.1  "+HelpFunctions.String2ipLong("25.168.-17.1"));

		System.out.println("192.168.17.1   : "+HelpFunctions.String2ipLong("192.168.17.1")+"  :  "+HelpFunctions.ipLong2String(HelpFunctions.String2ipLong("192.168.17.1")));
	
	}

}
