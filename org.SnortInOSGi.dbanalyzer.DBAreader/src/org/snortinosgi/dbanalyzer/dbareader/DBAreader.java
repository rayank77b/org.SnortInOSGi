/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.dbanalyzer.dbareader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.snortschema.HelpFunctions;
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 *	DBAreader implementation of read-only access to db analyzer data. Each access should have the sql-statement as parameter.
 */
public class DBAreader implements IDBAreader{
	
	
	
	/**
	 * store the connection to separate database
	 */
	DBconnector databaseconnector;
	
	/**
	 * connect to the DBAnalyser database.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public DBAreader() throws ClassNotFoundException, SQLException {
		databaseconnector = new DBconnector(true, "localhost");  
		databaseconnector.connect();
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader#getAllMetadataInfos()
	 */
	@Override
	public String getAllMetadataInfos() {
		setDatabase("metadataDB");
		String sql="SELECT metadata.dbname, metadata.sqlstring FROM metadata";
		StringBuffer buffer = new StringBuffer();
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) {
				buffer.append(rs.getString("dbname")+":");
				String tmp = rs.getString("sqlstring");
				String []tmp2 = tmp.split(":");
				buffer.append(tmp2[0]+"|");
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			String ret = buffer.toString();
			if(ret.length()!= 0) 
				return ret.substring(0, ret.length()-1);
			else
				return null;
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * generate from "table:field1;field2;field3" a sql-statement "SELECT field1, field2, field3 FROM table"
	 * @param sqlstring The backupstring "table:field1;field2;field3"
	 * @return return a sql-select-statement
	 */
	public String generateSelectString(String sqlstring) {
		String []tmp = sqlstring.split(":");
		
		if(tmp.length!=2)
			return null;
		
		String tablename = tmp[0];
		String []fields = tmp[1].split(";");
		if(fields.length==0)
			fields[0]=tmp[1];
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		int i=0;
		for(i=0; i< fields.length-1; i++ ) {
			buffer.append(fields[i]+", ");
		}
		buffer.append(fields[i]+" ");
		
		buffer.append("FROM "+tablename);
		return buffer.toString();
	}
	
	/**
	 * get the count of the fields stored in "table:field1;field2;field3" string
	 * @param sqlstring 	sql string as "table:field1;field2;field3" string
	 * @return number of the fields
	 */
	public int getFieldsCount(String sqlstring) {
		String []tmp = sqlstring.split(":");
		
		if(tmp.length!=2)
			return 0;
		
		String []fields = tmp[1].split(";");
		if(fields.length==0)
			return 1;
		
		return fields.length;
	}

	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader#getDataForCid(long, java.lang.String, java.lang.String)
	 */
	@Override
	public String getDataForCid(long cid, String sqlstring, String dbname) {
		
		
		String sql= generateSelectString(sqlstring);
		//int fieldscount = getFieldsCount(sqlstring);
		
		//System.out.println("sql: " +sql+"    cnt: "+fieldscount);
		setDatabase(dbname);
		try {
			ResultSet rs = databaseconnector.executetSQL(sql+" WHERE eventcid='"+cid+"'");
			
			String result=null;
			while ( rs.next() ) {
				result = HelpFunctions.getField(rs, sqlstring);
			}
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			return result;
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader#setDatabase(java.lang.String)
	 */
	@Override
	public int setDatabase(String dbname) {
		String sql = "USE " + dbname;
		try {
			databaseconnector.executeCommand(sql);
			return IRebuildDB.OK;
		} catch (SQLException e) {
			return IRebuildDB.ERROR;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader#getDataForCids(long, long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getDataForCids(long fromcid, long tocid, String sqlstring,
			String dbname) {
		
		int diff = (int)(tocid-fromcid);
		
		List<String> buffer = new ArrayList<String>();
		
		for(int i=0; i<diff; i++) {
			String ret = getDataForCid(fromcid+i, sqlstring, dbname);
			if(ret!=null)
				buffer.add(ret);
		}
		
		return buffer;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IDBAreader#getMetadata(java.lang.String, java.lang.String)
	 */
	@Override
	public String getMetadata(String dbname, String tablename) {
		setDatabase("metadataDB");
		String sql="SELECT * FROM metadata WHERE " +
				"dbname='"+dbname+"' AND sqlstring LIKE '"+tablename+":%'";
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			String buffer=null;
			while ( rs.next() ) 
				buffer = rs.getString("sqlstring");
			
			Statement st = rs.getStatement();
			rs.close();
			st.close();
			
			if(buffer==null)
				return null;
			
			String []tmp=buffer.split(":");
			if(tmp!=null && tmp.length>0)
				return tmp[1];
			
			return null;
		} catch (SQLException e) {
			//e.printStackTrace();
			return null;
		}
	}

}
