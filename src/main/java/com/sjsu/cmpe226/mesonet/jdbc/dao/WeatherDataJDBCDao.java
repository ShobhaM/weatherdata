package com.sjsu.cmpe226.mesonet.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.util.DBConnMgr;

public class WeatherDataJDBCDao {

	//Get the connection mgr instance.
	private DBConnMgr DBConnectionMgr = DBConnMgr.getDBConnMgr();
	private Connection conn = null;
		
	/**
	 * The public empty constructor.
	 */
	public WeatherDataJDBCDao(){
		
	}
	
	/**
	 * The method to load waether data into the DB.
	 */
	public synchronized void saveWeatherDataToDB(HashMap<String,String> contentDataInfo){
		PreparedStatement pstmt = null;
		
		System.out.println("Inside the WeatherDataJDBCDao.saveWeatherDataToDB() method...");
		try{
			if(conn == null || conn.isClosed()){
				conn = DBConnectionMgr.getConnection();
			}
			
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(WeatherDataConstants.INSERT_WAETHERDATA_QUERY);
			
			pstmt.setString(1, contentDataInfo.get("STN"));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
            java.util.Date dt1 = null;
            java.sql.Date dt = null;
            try {
    			dt1 = df.parse(contentDataInfo.get("YYMMDD/HHMM"));
    			dt = new java.sql.Date(dt1.getTime());
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			
            pstmt.setDate(2, dt);
            pstmt.setDouble(3, Double.parseDouble(contentDataInfo.get("MNET")));
            pstmt.setDouble(4, Double.parseDouble(contentDataInfo.get("SLAT")));
            pstmt.setDouble(5, Double.parseDouble(contentDataInfo.get("SLON")));
            pstmt.setDouble(6, Double.parseDouble(contentDataInfo.get("SELV")));
            pstmt.setDouble(7, Double.parseDouble(contentDataInfo.get("TMPF")));
			pstmt.setDouble(8, Double.parseDouble(contentDataInfo.get("SKNT")));
			pstmt.setDouble(9, Double.parseDouble(contentDataInfo.get("DRCT")));
			pstmt.setDouble(10, Double.parseDouble(contentDataInfo.get("GUST")));
			pstmt.setDouble(11, Double.parseDouble(contentDataInfo.get("PMSL")));
			pstmt.setDouble(12, Double.parseDouble(contentDataInfo.get("ALTI")));
			pstmt.setDouble(13, Double.parseDouble(contentDataInfo.get("DWPF")));
			pstmt.setDouble(14, Double.parseDouble(contentDataInfo.get("RELH")));
			pstmt.setDouble(15, Double.parseDouble(contentDataInfo.get("WTHR")));
			pstmt.setDouble(16, Double.parseDouble(contentDataInfo.get("P24I")));
			pstmt.setDate(17, new Date(System.currentTimeMillis()));
			pstmt.setDate(18, new Date(System.currentTimeMillis()));
			
			//Execute the insert query.
			pstmt.executeUpdate();
			
			//Commit the transaction.
			conn.commit();
		}catch(SQLException se){
			System.out.println("I am here ONE");
			se.printStackTrace();
			try{
				if(conn != null){
					System.out.println("Rolling back the transaction.");
					conn.rollback();
					if(se.getMessage().toLowerCase().contains("duplicate key value")){
						updateIfDuplicateRecordFound(contentDataInfo, conn);
					}
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}catch(Exception ex){
			System.out.println("I am here TWO");
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		System.out.println("Exiting the WeatherDataJDBCDao.saveWeatherDataToDB() method...");
	}
	
	/**
	 * The method to update any existing record in case of discovery of a duplicate or updated record.
	 */
	public synchronized void updateIfDuplicateRecordFound(HashMap<String, String> contentDataInfo, Connection conn)
		throws SQLException{
		PreparedStatement pstmt = null;
		
		System.out.println("Inside the WeatherDataJDBCDao.updateIfDuplicateRecordFound() method...");
		
		if(conn != null){
			pstmt = conn.prepareStatement(WeatherDataConstants.UPDATE_WEATHERDATA_QUERY);
		}
		
		pstmt.setDouble(1, Double.parseDouble(contentDataInfo.get("MNET")));
        pstmt.setDouble(2, Double.parseDouble(contentDataInfo.get("SLAT")));
        pstmt.setDouble(3, Double.parseDouble(contentDataInfo.get("SLON")));
        pstmt.setDouble(4, Double.parseDouble(contentDataInfo.get("SELV")));
        pstmt.setDouble(5, Double.parseDouble(contentDataInfo.get("TMPF")));
		pstmt.setDouble(6, Double.parseDouble(contentDataInfo.get("SKNT")));
		pstmt.setDouble(7, Double.parseDouble(contentDataInfo.get("DRCT")));
		pstmt.setDouble(8, Double.parseDouble(contentDataInfo.get("GUST")));
		pstmt.setDouble(9, Double.parseDouble(contentDataInfo.get("PMSL")));
		pstmt.setDouble(10, Double.parseDouble(contentDataInfo.get("ALTI")));
		pstmt.setDouble(11, Double.parseDouble(contentDataInfo.get("DWPF")));
		pstmt.setDouble(12, Double.parseDouble(contentDataInfo.get("RELH")));
		pstmt.setDouble(13, Double.parseDouble(contentDataInfo.get("WTHR")));
		pstmt.setDouble(14, Double.parseDouble(contentDataInfo.get("P24I")));
		pstmt.setDate(15, new Date(System.currentTimeMillis()));
		
		pstmt.setString(16, contentDataInfo.get("STN"));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
		java.util.Date dt1 = null;
        java.sql.Date dt = null;
        try {
			dt1 = df.parse(contentDataInfo.get("YYMMDD/HHMM"));
			dt = new java.sql.Date(dt1.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        pstmt.setDate(17, dt);
        
		//Execute the update.
		int i = pstmt.executeUpdate();
		if (i > 0)
			System.out.println("The record with STN : "+contentDataInfo.get("STN")+" and "
					+ "CURRENT_TIME : "+contentDataInfo.get("YYMMDD/HHMM")+" got updated.");
		
		conn.commit();
		System.out.println("Exiting the WeatherDataJDBCDao.updateIfDuplicateRecordFound() method...");
	}
}
