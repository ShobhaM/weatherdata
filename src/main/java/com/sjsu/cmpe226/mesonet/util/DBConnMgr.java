package com.sjsu.cmpe226.mesonet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;

/**
 * 
 * @author <a href="Bhargav@leantaas.com">Bhargav</a>
 */
public class DBConnMgr {
    private static DBConnMgr DBConnMgrInstance;
    Connection               conn = null;
    /* initialize config file hook */
    //Properties                         prop                   = PropertyLoader.createConfigFileHook();

    protected DBConnMgr() {
    }

    public static DBConnMgr getDBConnMgr() {
        if (DBConnMgrInstance == null)
            DBConnMgrInstance = new DBConnMgr();
        return DBConnMgrInstance;
    }

    public Connection getConnection() {

        /*String url = prop.getProperty("url");
        String driver = prop.getProperty("driver");
        String userName = prop.getProperty("userName");
        String password = prop.getProperty("password");
        String dbName = prop.getProperty("dbName");*/

    	//Establishing Database Connection.
        try {
            Class.forName(WeatherDataConstants.DB_DRIVER).newInstance();
            conn = DriverManager.getConnection(WeatherDataConstants.DB_URL, 
            		WeatherDataConstants.DB_USER_NAME, WeatherDataConstants.DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    
    public Connection getTestConnection() {

        /*String url = prop.getProperty("url");
        String driver = prop.getProperty("driver");
        String userName = prop.getProperty("testUserName");
        String password = prop.getProperty("testPassword");
        String dbName = prop.getProperty("testDbName");*/
    	
        //Establishing Database Connection.
        try {
            Class.forName(WeatherDataConstants.DB_DRIVER).newInstance();
            conn = DriverManager.getConnection(WeatherDataConstants.DB_URL, 
            		WeatherDataConstants.DB_USER_NAME, WeatherDataConstants.DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
