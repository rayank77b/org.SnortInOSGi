/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.SnortInOSGi.openAPI.snortschema;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * Data is the basis class for all other snort tables object class. Data has no attributes.
 * It has only needed methods which derived class have all together or should have.
 */
public abstract class Data {

	/**
	 * get the attributes of object, selected by string
	 * @param value	name of the attribute
	 * @return	return a string array of fieldname and value
	 * @throws DataException
	 */
	public abstract String[] getSQLValues(String value) throws DataException;
	
	/**
	 * getSQLCreateValue() return a string as fieldname and the databasetype for the field.
	 * example: "eventsid INT UNSIGNED NOT NULL"
	 * @param value
	 * @return	a string as fieldname and his datatype in database
	 * @throws DataException
	 */
	public abstract String getSQLCreateValue(String value) throws DataException;
	
	/**
	 * toXML() convert an object to a xml-string. This is done with JAXB. 
	 * @return a xml-string of a object (attributes stored in xml-values)
	 * @throws JAXBException
	 */
	public String toXML() throws JAXBException{		
		JAXBContext context = JAXBContext.newInstance( this.getClass() );
		Marshaller m = context.createMarshaller(); 
		m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			
		StringWriter st = new StringWriter();
		m.marshal(this , st);
		
		return st.toString(); 
	}
	
	/**
	 * getFromXML() convert a xml-string in an object.
	 * @param xml	object saved as a xml-string
	 * @param cl	the class of the object
	 * @return		return the converted object of class cl.
	 */
	public static Object getFromXML(String xml, Class<?> cl) {
		if(xml==null)
			return null;
		
		Object obj = null;
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(cl);
			Unmarshaller um = context.createUnmarshaller(); 
			
			StringBuffer xmlStr = new StringBuffer(xml );
			
			obj = um.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		} 
		return obj;
	}
	
	/**
	 * create from a "tablename:value1,value2,val3,..." string a sql-statement insertion string.
	 * @param tosql metadata string as "tablename:value1,value2,val3,..."
	 * @return	a sql-statement insertion string
	 * @throws DataException
	 */
	public String getSQLInsert(String tosql) throws DataException {
		
		String []tmp = tosql.split(":");
		String tablename = tmp[0];
		String []values = tmp[1].split(";");
		
		String sql1 = "INSERT INTO "+tablename+"(";
		String sql2 = " VALUES(";
		for(String value : values) {
			
			String []tmp2 = this.getSQLValues(value);

			sql1 = sql1 + tmp2[0] + ",";
			sql2 = sql2 + tmp2[1] + ",";
		}
		
		// let get the last "," away
		sql1 = sql1.substring(0, sql1.lastIndexOf(','));
		sql2 = sql2.substring(0, sql2.lastIndexOf(','));
		
		return sql1+")"+sql2+")";
	}
	
	/**
	 * create from a "tablename:value1,value2,val3,..." string a sql-statement create table string.
	 * @param tosql	metadata string as "tablename:value1,value2,val3,..."
	 * @return	sql-statement create table string
	 * @throws DataException
	 */
	public String getSQLCreate(String tosql) throws DataException {
		String []tmp = tosql.split(":");
		String tablename = tmp[0];
		String []values = tmp[1].split(";");
		
		String sql = "CREATE TABLE "+tablename+"(";
		
		for(String value : values) {
			
			//System.out.print(value+"  :  ");
			String tmp2 = this.getSQLCreateValue(value);
			//System.out.println(tmp2);
			
			sql = sql + tmp2 + ",";
		}
		
		// let get the last "," away
		sql = sql.substring(0, sql.lastIndexOf(','));
		
		return sql+")";
	}
}
