package com.sjsu.cmpe226.mesonet.hibernate.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.util.HibernateUtil;
import com.sjsu.cmpe226.mesonet.vo.WeatherMetaDataVO;

/**
 * @author Bhargav
 */
public class WeatherMetaDataDao {
		
    public synchronized void saveDataHeader(HashMap<String,String> metaInfo, Integer count){
        
    	//Hibernate Session creation.
    	Session session = HibernateUtil.getMetaSessionFactory().openSession();
    	Transaction txn = null;
        
        //session.beginTransaction();
    	if(session != null){
        	txn = session.beginTransaction();
        	
        	try{
        		WeatherMetaDataVO metaDataObj = new WeatherMetaDataVO();
        		//metaDataObj.setId(counter);
        		metaDataObj.setPrimary_id(metaInfo.get("primary_id"));
        		metaDataObj.setSecondary_id(metaInfo.get("secondary_id"));
        		metaDataObj.setStation_name(metaInfo.get("station_name"));
        		metaDataObj.setState(metaInfo.get("state"));
        		metaDataObj.setCountry(metaInfo.get("country"));
        		metaDataObj.setLatitude(Double.parseDouble(metaInfo.get("latitude")));
        		metaDataObj.setLongitude(Double.parseDouble(metaInfo.get("longitude")));
        		metaDataObj.setINSERT_DATE(new Date());
        		metaDataObj.setLAST_UPDATE_DATE(new Date());
        		
        		session.save(metaDataObj);
        		/*session.getTransaction().commit();
        		HibernateUtil.shutdownHeader();*/
        		
        		//Catching exception for any duplicate records
        		//try{
        		//session.getTransaction().commit();
        		if(txn != null){
        			txn.commit();
        		}            		
        	}catch(ConstraintViolationException cx){
        		//System.out.println("Stuck with this ConstraintViolationException exception...");
        		try{
        			if(txn != null){
        				//System.out.println("Rolling back the recent transaction...");
        				//Call the updateExistingRecord() method.
                		updateExistingRecord(metaInfo, session, txn);
                		//System.out.println("The MetaData record with primary_id : "+metaInfo.get("primary_id")+" has been updated.");
        			}
        		}catch(HibernateException he){
        			he.printStackTrace();
        		}
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}finally{
        		if(session.isOpen())
        			HibernateUtil.shutdownHeader();
        	}
    	}
    }
    
    /**
     * The method to update any existing record. The method would be called when encountered with a record with 
     * the same primary_id (station id). The method will update the older record.
     * 
     */
     public synchronized void updateExistingRecord(HashMap<String,String> metaInfo, Session pSession, Transaction txn)
     throws HibernateException {
    	if(pSession.isOpen())
    		txn = pSession.beginTransaction();

    	//The Weather meta data record to be updated.
       	WeatherMetaDataVO metaDataObj = (WeatherMetaDataVO) pSession.get(WeatherMetaDataVO.class, 
       				metaInfo.get("primary_id"));
        			
       	//Update the required column values.
       		
       	metaDataObj.setSecondary_id(metaInfo.get("secondary_id"));
       	metaDataObj.setStation_name(metaInfo.get("station_name"));
       	metaDataObj.setState(metaInfo.get("state"));
        metaDataObj.setCountry(metaInfo.get("country"));
        metaDataObj.setLatitude(Double.parseDouble(metaInfo.get("latitude")));
        metaDataObj.setLongitude(Double.parseDouble(metaInfo.get("longitude")));
               
        metaDataObj.setLAST_UPDATE_DATE(new Date());
                   
        pSession.update(metaDataObj);
        txn.commit();
    }
}
