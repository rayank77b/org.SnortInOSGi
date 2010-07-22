/**
 * @author Andrej Frank, Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.dbanalyzer.rebuilddb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBreader;
import org.SnortInOSGi.openAPI.interfaces.backend.ISMDBwriter;
import org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB;
import org.SnortInOSGi.openAPI.snortschema.Alert;
import org.SnortInOSGi.openAPI.snortschema.Data;
import org.SnortInOSGi.openAPI.snortschema.DataException;
import org.SnortInOSGi.openAPI.snortschema.Packet;
import org.snortinosgi.bundle.dbconnector.DBconnector;

/**
 * RebuildDB create/delete tables/database on separated DB. 
 * Then copy (and delete) data from Snort main database to separate database
 */
public class RebuildDB implements IRebuildDB {

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
	public RebuildDB() throws ClassNotFoundException, SQLException {
		databaseconnector = new DBconnector(true, "localhost");
		databaseconnector.connect();
	}
	
	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyAlert(long, int, java.lang.String)
	 */
	@Override
	public int copyAlert(long cid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			String xml = reader.getAlert(cid, sid);
			Alert alert = (Alert) Data.getFromXML(xml, Alert.class);
			
			String sqlinsert;
			
			try {
				sqlinsert = alert.getSQLInsert(toSQL);
				//System.out.println(sqlinsert);
				setDatabase(dbname);
				databaseconnector.executeCommand(sqlinsert);
				
				return IRebuildDB.OK;
			} catch (DataException e) {
				
				return IRebuildDB.ERROR;
			} catch (SQLException e) {
				//e.printStackTrace();
				return IRebuildDB.ERROR_SQL;
			}			
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyAlertAndDelete(long, int)
	 */
	@Override
	public int copyAlertAndDelete(long cid, int sid, String toSQL, String dbname) {
		int ret = copyAlert(cid, sid, toSQL, dbname);
		if(ret!=IRebuildDB.OK)
			return ret;
		
		ISMDBwriter writer = Activator.getDefault().getWriter();
		if(writer!=null) {
			writer.deleteCID(cid, sid); // the delete is not sure, can fail
		}
		
		return IRebuildDB.OK;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyAlerts(long, long, int, java.lang.String)
	 */
	@Override
	public int copyAlerts(long fromcid, long tocid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			List<String> xmls = reader.getAlerts(fromcid,tocid, sid);
			
			setDatabase(dbname);
			
			for(String xml: xmls) {
				Alert alert = (Alert) Data.getFromXML(xml, Alert.class);
				String sqlinsert;
				
				try {
					sqlinsert = alert.getSQLInsert(toSQL);
					//System.out.println(sqlinsert);
					
					//copy alert
					databaseconnector.executeCommand(sqlinsert);
					
				} catch (DataException e) {
					
					return IRebuildDB.ERROR;
				} catch (SQLException e) {
					//e.printStackTrace();
					return IRebuildDB.ERROR_SQL;
				}
			}
			return IRebuildDB.OK;
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyAlertsAndDelete(long, long, int)
	 */
	@Override
	public int copyAlertsAndDelete(long fromcid, long tocid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			List<String> xmls = reader.getAlerts(fromcid,tocid, sid);
			
			setDatabase(dbname);
			
			for(String xml: xmls) {
				Alert alert = (Alert) Data.getFromXML(xml, Alert.class);
				String sqlinsert;
				
				try {
					sqlinsert = alert.getSQLInsert(toSQL);
					//System.out.println(sqlinsert);
					
					// copy alert
					databaseconnector.executeCommand(sqlinsert);
					
					ISMDBwriter writer = Activator.getDefault().getWriter();
					if(writer!=null) {
						writer.deleteCID(alert.event.cid, alert.event.sid); // the delete is not sure, can fail
					}
					
				} catch (DataException e) {
					
					return IRebuildDB.ERROR;
				} catch (SQLException e) {
					//e.printStackTrace();
					return IRebuildDB.ERROR_SQL;
				}
			}
			return IRebuildDB.OK;
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyPacket(long, int, java.lang.String)
	 */
	@Override
	public int copyPacket(long cid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			String xml = reader.getPacket(cid, sid);
			Packet packet = (Packet) Data.getFromXML(xml, Packet.class);
			
			String sqlinsert;
			
			try {
				sqlinsert = packet.getSQLInsert(toSQL);
				//System.out.println(sqlinsert);
				setDatabase(dbname);
				databaseconnector.executeCommand(sqlinsert);
				
				return IRebuildDB.OK;
			} catch (DataException e) {
				e.printStackTrace();
				return IRebuildDB.ERROR;
			} catch (SQLException e) {
				//e.printStackTrace();
				return IRebuildDB.ERROR_SQL;
			}			
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyPacketAndDelete(long, int)
	 */
	@Override
	public int copyPacketAndDelete(long cid, int sid, String toSQL, String dbname) {
		int ret = copyPacket(cid, sid, toSQL, dbname);
		if(ret!=IRebuildDB.OK)
			return ret;
		
		ISMDBwriter writer = Activator.getDefault().getWriter();
		if(writer!=null) {
			writer.deleteCID(cid, sid); // the delete is not sure, can fail
		}
		
		return IRebuildDB.OK;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyPackets(long, long, int, java.lang.String)
	 */
	@Override
	public int copyPackets(long fromcid, long tocid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			List<String> xmls = reader.getPackets(fromcid,tocid, sid);
			
			setDatabase(dbname);
			
			for(String xml: xmls) {
				Packet packet = (Packet) Data.getFromXML(xml, Packet.class);
				String sqlinsert;
				
				try {
					sqlinsert = packet.getSQLInsert(toSQL);
					//System.out.println(sqlinsert);
					
					databaseconnector.executeCommand(sqlinsert);
					
					
				} catch (DataException e) {
					
					return IRebuildDB.ERROR;
				} catch (SQLException e) {
					//e.printStackTrace();
					return IRebuildDB.ERROR_SQL;
				}
			}
			return IRebuildDB.OK;
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#copyPacketsAndDelete(long, long, int)
	 */
	@Override
	public int copyPacketsAndDelete(long fromcid, long tocid, int sid, String toSQL, String dbname) {
		ISMDBreader reader = Activator.getDefault().getReader();
		if(reader != null) {
			
			List<String> xmls = reader.getPackets(fromcid,tocid, sid);
			
			setDatabase(dbname);
			
			for(String xml: xmls) {
				Packet packet = (Packet) Data.getFromXML(xml, Packet.class);
				String sqlinsert;
				
				try {
					sqlinsert = packet.getSQLInsert(toSQL);
					//System.out.println(sqlinsert);
					
					// copy alert
					databaseconnector.executeCommand(sqlinsert);
					
					ISMDBwriter writer = Activator.getDefault().getWriter();
					if(writer!=null) {
						writer.deleteCID(packet.event.cid, packet.event.sid); // the delete is not sure, can fail
					}
					
				} catch (DataException e) {
					
					return IRebuildDB.ERROR;
				} catch (SQLException e) {
					//e.printStackTrace();
					return IRebuildDB.ERROR_SQL;
				}
			}
			return IRebuildDB.OK;
		}
		return IRebuildDB.ERROR;
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#createDatabase(java.lang.String)
	 */
	@Override
	public int createDatabase(String name) {
		String sql = "CREATE DATABASE "+name;
		try {
			//System.out.println(sql);
			databaseconnector.executeCommand(sql);
			return IRebuildDB.OK;
		} catch (SQLException e) {
			return IRebuildDB.ERROR;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#createTable(java.lang.String)
	 */
	@Override
	public int createTable(String sql) {
		//System.out.println(tosql);
		try {
			databaseconnector.executeCommand(sql);
			return IRebuildDB.OK;
		} catch (SQLException e) {
			//e.printStackTrace();
			return IRebuildDB.ERROR;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#createTable(java.lang.String, java.lang.String)
	 */
	@Override
	public int createTable(String dbname, String sql) {
		if( setDatabase(dbname) == IRebuildDB.ERROR )
			return IRebuildDB.ERROR;
		return createTable(sql);
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#deleteDatabase(java.lang.String)
	 */
	@Override
	public int deleteDatabase(String name) {
		String sql = "DROP DATABASE "+name;
		try {
			databaseconnector.executeCommand(sql);
			return IRebuildDB.OK;
		} catch (SQLException e) {
			return IRebuildDB.ERROR;
		}	
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#setDatabase(java.lang.String)
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
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#testTableExists(java.lang.String)
	 */
	@Override
	public int testTableExists(String tablename) {
		try {
			databaseconnector.executeCommand("DESCRIBE "+tablename);
			return IRebuildDB.EXISTS;
		} catch (SQLException e) {
			return IRebuildDB.ERROR;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#getMetadata(java.lang.String)
	 */
	@Override
	public String getMetadata(String dbname) {
		setDatabase("metadataDB");
		String sql="SELECT metadata.sqlstring FROM metadata WHERE dbname='"+dbname+"'";
		StringBuffer buffer = new StringBuffer();
		
		try {
			ResultSet rs = databaseconnector.executetSQL(sql);
			
			while ( rs.next() ) 
				buffer.append(rs.getString("sqlstring")+"|");
			
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

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#insertMetdata(java.lang.String, java.lang.String)
	 */
	@Override
	public int insertMetadata(String dbname, String sqlstring) {
		setDatabase("metadataDB");
		
		// db exsists yet 
		String tmp = getMetadata(dbname);
		if (tmp != null) {
			String []tmp2 = tmp.split("\\|");
			String testdbname = sqlstring.split(":")[0];
			// the returned string is ala  "tablename:field1;field2|tablename2:field1;field2;field3|tablename3:field1"
			// we must split it with "|" and the split with ":" then we get the tablename
			for( int i=0; i< tmp2.length; i++ ) {
				//System.out.println(" split tmp3: "+tmp2[i]);
				String []tmp3 = tmp2[i].split(":");
				if( testdbname.equalsIgnoreCase(tmp3[0]))
					return IRebuildDB.EXISTS;
			}
		}
		
		String sql = "INSERT INTO metadata VALUES('"+dbname+"', '"+sqlstring+"')";
		try {
			databaseconnector.executeCommand(sql);
			return IRebuildDB.OK;
		} catch (SQLException e) {
			return IRebuildDB.ERROR;
		}
	}

	/* (non-Javadoc)
	 * @see org.SnortInOSGi.openAPI.interfaces.dbanalyzer.IRebuildDB#getMetadata(java.lang.String, java.lang.String)
	 */
	@Override
	public String getMetadata(String dbname, String tablename) {
		// db exsists yet 
		String tmp = getMetadata(dbname);
		if (tmp != null) {
			String []tmp2 = tmp.split("\\|");
			// the returned string is ala  "tablename:field1;field2|tablename2:field1;field2;field3|tablename3:field1"
			// we must split it with "|" and the split with ":" then we get the tablename
			for( int i=0; i< tmp2.length; i++ ) {
				//System.out.println(" split tmp3: "+tmp2[i]);
				String []tmp3 = tmp2[i].split(":");
				if( tablename.equalsIgnoreCase(tmp3[0]))
					return tmp2[i];
			}
		}
		return null;
	}
	



}
