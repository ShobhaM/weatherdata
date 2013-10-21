package com.sjsu.cmpe226.mesonet.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.util.DBConnMgr;
import com.sjsu.cmpe226.mesonet.vo.DateMasterVO;

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
		
		//System.out.println("Inside the WeatherDataJDBCDao.saveWeatherDataToDB() method...");
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
			//System.out.println("I am here ONE");
			se.printStackTrace();
			try{
				if(conn != null){
					//System.out.println("Rolling back the transaction.");
					conn.rollback();
					if(se.getMessage().toLowerCase().contains("duplicate key value")){
						updateIfDuplicateRecordFound(contentDataInfo, conn);
					}
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}catch(Exception ex){
			//System.out.println("I am here TWO");
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
		
		//System.out.println("Exiting the WeatherDataJDBCDao.saveWeatherDataToDB() method...");
	}
	
	/**
	 * The method to update any existing record in case of discovery of a duplicate or updated record.
	 */
	public synchronized void updateIfDuplicateRecordFound(HashMap<String, String> contentDataInfo, Connection conn)
		throws SQLException{
		PreparedStatement pstmt = null;
		
		//System.out.println("Inside the WeatherDataJDBCDao.updateIfDuplicateRecordFound() method...");
		
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
		/*if (i > 0)
			System.out.println("The record with STN : "+contentDataInfo.get("STN")+" and "
					+ "CURRENT_TIME : "+contentDataInfo.get("YYMMDD/HHMM")+" got updated.");*/
		
		conn.commit();
		//System.out.println("Exiting the WeatherDataJDBCDao.updateIfDuplicateRecordFound() method...");
	}
	
	public synchronized void getAvgTempForStnsWithinTimeRange(Date fromDate, Date toDate){
		PreparedStatement pstmt = null;
		int avgTemp = 0;
		String stn = "";
		HashMap<Integer, String> hmResult = new HashMap<Integer, String>();
		ResultSet rs = null;		
		
		try{
			conn = DBConnectionMgr.getConnection();
			if(conn != null && !conn.isClosed())
				pstmt = conn.prepareStatement(WeatherDataConstants.SELECT_AVGTEMP_FORSTN_WITHDATERANGE);
			
			pstmt.setDate(1, fromDate);
			pstmt.setDate(2, toDate);
			
			rs = pstmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					avgTemp = rs.getInt(1);
					stn = rs.getString(2);
					
					hmResult.put(avgTemp, stn);
				}
			}
			
			if(hmResult.size() > 0)
				System.out.println("The content in HashMap :"+hmResult.size());
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method would demonstrate the use of date master table that we have in our schema. The date master table
	 * has records for dates such week, month, quarter, etc. We wanted to show how effectively we can provide a feature
	 * to get weather report based on avg temperature for any quarter or week. Using date master simplifies the task of 
	 * further query computations.
	 */
	public synchronized void demoWithDateMasterTableInSchema(String pStn, Date fromDate, Date toDate){
		PreparedStatement pstmt = null;
		Double avgTemp = 0.0;
		DateMasterVO dateMasterVOObj = null;
		HashMap<String, DateMasterVO> hm = new HashMap<String, DateMasterVO>();
		ResultSet rs = null;
		int count = 0;
		
		try{
			conn = DBConnectionMgr.getConnection();
			
			if(conn != null && !conn.isClosed())
				pstmt = conn.prepareStatement(WeatherDataConstants.SELECT_DEMO_QUERY1);
			
			pstmt.setString(1, pStn);
			pstmt.setDate(2, fromDate);
			pstmt.setDate(3, toDate);
			pstmt.setDate(4, fromDate);
			pstmt.setDate(5, toDate);
			
			System.out.println("");
			
			rs = pstmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					avgTemp = rs.getDouble(1);
					dateMasterVOObj = new DateMasterVO();
					dateMasterVOObj.setWeekOfYear(rs.getInt(2));
					dateMasterVOObj.setYear(rs.getInt(3));
					count++;
					
					hm.put(""+count+" : "+avgTemp, dateMasterVOObj);
				}
			}
			//count = 0;
			
			Iterator it = hm.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				System.out.println("Average Temp : "+pairs.getKey().toString()+" "
						+ "Weak Of Year : "+((DateMasterVO)pairs.getValue()).getWeekOfYear()+" Year : "+((DateMasterVO)pairs.getValue()).getYear());
				//it.remove();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	/**
	 * The method to query normalized schema to get the AVG temp grouped by station id by providing only the longitude.
	 */
	public void getAvgTempGroupByStationNorm1(String queryType){
		PreparedStatement pstmt = null;
		conn = DBConnectionMgr.getConnection();
		HashMap<Double, String> hm = new HashMap<Double, String>();
		double avgTemp = 0.0;
		String stn = "";
		ResultSet rs = null;
		try{
			if(conn != null && !conn.isClosed()){
				if(queryType.equals("normalizedOnlyLat"))
					pstmt = conn.prepareStatement(WeatherDataConstants.GET_AVGTEMP_NORM1);
				else if(queryType.equals("normalizedLatLon"))
					pstmt = conn.prepareStatement(WeatherDataConstants.GET_AVGTEMP_NORM2);
				else if(queryType.equals("denormalizedOnlyLat"))
					pstmt = conn.prepareStatement(WeatherDataConstants.GET_AVGTEMP_NORM3);
				else if(queryType.equals("denormalizedLatLon"))
					pstmt = conn.prepareStatement(WeatherDataConstants.GET_AVGTEMP_NORM4);
			}
			
			rs = pstmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					avgTemp = rs.getDouble(1);
					stn = rs.getString(2);
					hm.put(avgTemp, stn);
				}
			}
			
			System.out.println("Total HashMap Size "+hm.size());
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			try{
				if(pstmt != null)
					pstmt.close();
				if(conn != null && !conn.isClosed())
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * The sample functionality using Date Master Table design in schema.
	 */
	public void testWeatherReportAppUsingDateMaster(){
		PreparedStatement pstmt = null;
		conn = DBConnectionMgr.getConnection();
		HashMap<Double, DateMasterVO> hm = new HashMap<Double, DateMasterVO>();
		double avgTemp = 0.0;
		DateMasterVO dateMasterVO = null;
		ResultSet rs = null;
		try{
			if(conn != null && !conn.isClosed()){
				pstmt = conn.prepareStatement(WeatherDataConstants.GET_APPQUERY_DATEMASTER);
			}
			
			rs = pstmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					avgTemp = rs.getDouble(1);
					dateMasterVO = new DateMasterVO();
					dateMasterVO.setWeekOfYear(rs.getInt(2));
					dateMasterVO.setYear(rs.getInt(3));
					
					hm.put(avgTemp, dateMasterVO);
				}
			}
			
			System.out.println("Total HashMap Size "+hm.size());
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
			try{
				if(pstmt != null)
					pstmt.close();
				if(conn != null && !conn.isClosed())
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
}
