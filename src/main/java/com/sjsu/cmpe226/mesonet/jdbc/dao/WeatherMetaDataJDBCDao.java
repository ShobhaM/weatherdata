package com.sjsu.cmpe226.mesonet.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.util.DBConnMgr;

/**
 * This JDBC DAO class is to carry out all database related transactions using plain
 * Java-JDBC approach.
 * @author krish
 *
 */
public class WeatherMetaDataJDBCDao {

	//Get the connection mgr instance.
	private DBConnMgr DBConnectionMgr = DBConnMgr.getDBConnMgr();
	private Connection conn = null;
	
	/**
	 * The public empty constructor.
	 */
	public WeatherMetaDataJDBCDao(){
		
	}
	
	/**
	 * The method to load data into the DB.
	 */
	public synchronized void saveMetaDataToDB(HashMap<String,String> metaInfo){
		PreparedStatement pstmt = null;
		
		System.out.println("Inside the WeatherMetaDataJDBCDao.saveMetaDataToDB() method...");
		try{
			if(conn == null || conn.isClosed()){
				conn = DBConnectionMgr.getConnection();
			}
			
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(WeatherDataConstants.INSERT_METADATA_QUERY);
			
			pstmt.setString(1, metaInfo.get("primary_id"));
			pstmt.setString(2, metaInfo.get("secondary_id"));
			pstmt.setString(3, metaInfo.get("station_name"));
			pstmt.setString(4, metaInfo.get("state"));
			pstmt.setString(5, metaInfo.get("country"));
			pstmt.setDouble(6, Double.parseDouble(metaInfo.get("latitude")));
			pstmt.setDouble(7, Double.parseDouble(metaInfo.get("longitude")));
			/*pstmt.setDouble(8, Double.parseDouble(metaInfo.get("elevation")));
			pstmt.setInt(9, Integer.parseInt(metaInfo.get("mesowest_network_id")));
			pstmt.setString(10, metaInfo.get("network_name"));
			pstmt.setString(11, metaInfo.get("status"));
			pstmt.setInt(12, Integer.parseInt(metaInfo.get("primary_provider_id")));
			pstmt.setString(13, metaInfo.get("primary_provider"));
			pstmt.setInt(14, Integer.parseInt(metaInfo.get("secondary_provider_id")));
			pstmt.setString(15, metaInfo.get("secondary_provider"));
			pstmt.setInt(16, Integer.parseInt(metaInfo.get("tertiary_provider_id")));
			pstmt.setString(17, metaInfo.get("tertiary_provider"));
			pstmt.setInt(18, Integer.parseInt(metaInfo.get("wims_id")));*/
			pstmt.setDate(8, new Date(System.currentTimeMillis()));
			pstmt.setDate(9, new Date(System.currentTimeMillis()));
			
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
						updateIfDuplicateRecordFound(metaInfo, conn);
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
		
		System.out.println("Exiting the WeatherMetaDataJDBCDao.saveMetaDataToDB() method...");
	}
	
	/**
	 * The method to update any existing record in case of discovery of a duplicate or updated record.
	 */
	public synchronized void updateIfDuplicateRecordFound(HashMap<String, String> metaInfo, Connection conn)
		throws SQLException{
		PreparedStatement pstmt = null;
		
		System.out.println("Inside the WeatherMetaDataJDBCDao.updateIfDuplicateRecordFound() method...");
		
		if(conn != null){
			pstmt = conn.prepareStatement(WeatherDataConstants.UPDATE_METADATA_QUERY);
		}
		
		pstmt.setString(1, metaInfo.get("secondary_id"));
		pstmt.setString(2, metaInfo.get("station_name"));
		pstmt.setString(3, metaInfo.get("state"));
		pstmt.setString(4, metaInfo.get("country"));
		pstmt.setDouble(5, Double.parseDouble(metaInfo.get("latitude")));
		pstmt.setDouble(6, Double.parseDouble(metaInfo.get("longitude")));
		pstmt.setDate(7, new Date(System.currentTimeMillis()));
		pstmt.setString(8, metaInfo.get("primary_id"));
		
		//Execute the update.
		int i = pstmt.executeUpdate();
		if (i > 0)
			System.out.println("The record with primary_id : "+metaInfo.get("primary_id")+" got updated.");
		
		conn.commit();
	}
}
