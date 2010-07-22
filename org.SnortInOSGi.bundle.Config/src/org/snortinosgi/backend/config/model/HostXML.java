/**
 * @author Andrej Frank, 2009, 2010 Copyright GNU GPL 2.0
 * TAE, Brunel University West London, University of Applied Science Esslingen
 *
 */
package org.snortinosgi.backend.config.model;

/**
 * class HostXML describe a host. This is her for maping the object to a xml-file and backward.
 * HostXML save all needed configuration data to access snort main database.
 */
public class HostXML {
	/**
	 * needed fields. Save important information: usernames, passwords, databasename, hostname, etc
	 */
	private String hostname;
	private String dbname;
	private String snortusername;
	private String snortuserpassword;
	private String rootusername;
	private String rootuserpassword;
	private String dburl;
	private int port;
	private String dbdriver;
	
	public HostXML() {
		
	}

	/**
	 * constructor to set all attribtues at once
	 * @param hostname	hostname of the database
	 * @param dbname	database name
	 * @param snortusername	snort user name (for read-only access)
	 * @param snortuserpassword	snort user password
	 * @param rootusername	mysql root name (for write-read access) (mostl "root")
	 * @param rootuserpassword	mysql root password
	 * @param dburl		database art: mysql or others
	 * @param port		database running port
	 * @param dbdriver	database driver as "com.mysql.jdbc.Driver"
	 */
	public HostXML(String hostname, String dbname, String snortusername,
			String snortuserpassword, String rootusername,
			String rootuserpassword, String dburl, int port, String dbdriver) {
		this.hostname = hostname;
		this.dbname = dbname;
		this.snortusername = snortusername;
		this.snortuserpassword = snortuserpassword;
		this.rootusername = rootusername;
		this.rootuserpassword = rootuserpassword;
		this.dburl = dburl;
		this.port = port;
		this.dbdriver = dbdriver;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the dbname
	 */
	public String getDbname() {
		return dbname;
	}

	/**
	 * @param dbname the dbname to set
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	/**
	 * @return the snortusername
	 */
	public String getSnortusername() {
		return snortusername;
	}

	/**
	 * @param snortusername the snortusername to set
	 */
	public void setSnortusername(String snortusername) {
		this.snortusername = snortusername;
	}

	/**
	 * @return the snortuserpassword
	 */
	public String getSnortuserpassword() {
		return snortuserpassword;
	}

	/**
	 * @param snortuserpassword the snortuserpassword to set
	 */
	public void setSnortuserpassword(String snortuserpassword) {
		this.snortuserpassword = snortuserpassword;
	}

	/**
	 * @return the rootusername
	 */
	public String getRootusername() {
		return rootusername;
	}

	/**
	 * @param rootusername the rootusername to set
	 */
	public void setRootusername(String rootusername) {
		this.rootusername = rootusername;
	}

	/**
	 * @return the rootuserpassword
	 */
	public String getRootuserpassword() {
		return rootuserpassword;
	}

	/**
	 * @param rootuserpassword the rootuserpassword to set
	 */
	public void setRootuserpassword(String rootuserpassword) {
		this.rootuserpassword = rootuserpassword;
	}

	/**
	 * @return the dburl
	 */
	public String getDburl() {
		return dburl;
	}

	/**
	 * @param dburl the dburl to set
	 */
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the dbdriver
	 */
	public String getDbdriver() {
		return dbdriver;
	}

	/**
	 * @param dbdriver the dbdriver to set
	 */
	public void setDbdriver(String dbdriver) {
		this.dbdriver = dbdriver;
	}
	
	/**
	 * get a "jdbc:dburl://hostname:port/dbname" url, from given attributes
	 * @return return the database url
	 */
	public String createDbUrl() {
		return "jdbc:"+dburl+"://"+hostname+":"+port+"/"+dbname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HostXML [dbdriver=" + dbdriver + ", dbname=" + dbname
				+ ", dburl=" + dburl + ", hostname=" + hostname + ", port="
				+ port + ", rootusername=" + rootusername
				+ ", rootuserpassword=" + rootuserpassword + ", snortusername="
				+ snortusername + ", snortuserpassword=" + snortuserpassword
				+ "]";
	}
}
