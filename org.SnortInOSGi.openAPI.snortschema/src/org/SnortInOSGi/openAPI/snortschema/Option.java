/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * class Option is a map for table opt in snort main database. 
 * Class Option should not be used direct, rather through IPheader class.
 */
@XmlRootElement(name = "Option")
public class Option {
	/**
	 * necessary attribtes of table opt
	 */
	public int id;
	public int proto;
	public int code;
	public int len;
	public String data;

	public Option() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Option [code=" + code + ", data=" + data + ", id=" + id
				+ ", len=" + len + ", proto=" + proto + "]";
	}
}
