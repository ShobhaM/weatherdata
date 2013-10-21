package com.sjsu.cmpe226.mesonet.hibernate.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.sjsu.cmpe226.mesonet.util.HibernateUtil;
import com.sjsu.cmpe226.mesonet.vo.StationWeatherWithTime;
import com.sjsu.cmpe226.mesonet.vo.WeatherDataVO;
//import com.sjsu.cmpe226.mesonet.vo.WeatherMetaDataVO;
import com.sjsu.cmpe226.mesonet.vo.WeatherMetaDataVO;

/**
 * @author Bhargav
 */
public class WeatherDataDao {
	
    public synchronized void saveDataContent(HashMap<String,String> contentInfo, Integer counter) {
    	
    	//Session session = HibernateUtil.getDataSessionFactory().openSession();
       // System.out.println("inside saveDataContent");
        StationWeatherWithTime swtObj = new StationWeatherWithTime();
        
        //Hibernate session creation.
     	Session session = HibernateUtil.getDataSessionFactory().openSession();
     	Transaction txn = null;
    	 
        //session.beginTransaction();
        if(session != null){
        	txn = session.beginTransaction();
         	try{
         		WeatherDataVO metaOutDataObj = new WeatherDataVO();

         		swtObj.setSTN(contentInfo.get("STN"));
            
         		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
         		Date dt = null;
         		try {
         			dt = df.parse(contentInfo.get("YYMMDD/HHMM"));
         		} catch (ParseException e) {
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		}
            
         		swtObj.setYYMMDDHHMM(dt);
         		metaOutDataObj.setSwtObj(swtObj);
         		metaOutDataObj.setMNET(Double.parseDouble(contentInfo.get("MNET")));
         		metaOutDataObj.setSLAT(Double.parseDouble(contentInfo.get("SLAT")));
         		metaOutDataObj.setSLON(Double.parseDouble(contentInfo.get("SLON")));
         		metaOutDataObj.setSELV(Double.parseDouble(contentInfo.get("SELV")));
         		metaOutDataObj.setTMPF(Double.parseDouble(contentInfo.get("TMPF")));
         		metaOutDataObj.setSKNT(Double.parseDouble(contentInfo.get("SKNT")));
         		metaOutDataObj.setDRCT(Double.parseDouble(contentInfo.get("DRCT")));
         		metaOutDataObj.setGUST(Double.parseDouble(contentInfo.get("GUST")));
         		metaOutDataObj.setPMSL(Double.parseDouble(contentInfo.get("PMSL")));
         		metaOutDataObj.setALTI(Double.parseDouble(contentInfo.get("ALTI")));
         		metaOutDataObj.setDWPF(Double.parseDouble(contentInfo.get("DWPF")));
         		metaOutDataObj.setRELH(Double.parseDouble(contentInfo.get("RELH")));
         		metaOutDataObj.setWTHR(Double.parseDouble(contentInfo.get("WTHR")));
         		metaOutDataObj.setP24I(Double.parseDouble(contentInfo.get("P24I")));
         		metaOutDataObj.setINSERT_DATE(new Date());
         		metaOutDataObj.setLAST_UPDATE_DATE(new Date());
         		
         		session.save(metaOutDataObj);
         		
        		if(txn != null){
        			txn.commit();
        		}            		
        	}catch(ConstraintViolationException ce){
        		//System.out.println("Stuck with this ConstraintViolationException exception...");
        		try{
        			if(txn != null){
            			//System.out.println("Rolling back the recent transaction...");
            			txn.rollback();            			
            			//Call the updateExistingWeatherRecord() method.
                    	updateExistingWeatherRecord(contentInfo, swtObj, session, txn);  
                    	//System.out.println("The WeatherData record with STN : "+swtObj.getSTN()+" and "
                    			//+ "CREATE_TIME : "+swtObj.getYYMMDDHHMM()+" has been updated.");
            		}
        		}catch(HibernateException he){
        			he.printStackTrace();
        		}        		
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}finally{
        		if(session.isOpen())
        			HibernateUtil.shutdownData();
        	}
        } 
    }
    
    /**
     * The method to update any existing weather record. The method would be called when encountered with a record with 
     * the same Station ID and Create_time. The method will update the older record.
     * 
     */
    public synchronized void updateExistingWeatherRecord(HashMap<String,String> contentInfo, StationWeatherWithTime swtObj, 
    	 Session pSession, Transaction txn) throws HibernateException{
    	if(pSession.isOpen())
    		txn = pSession.beginTransaction();
       		
    	//The Weather meta data record to be updated.
       	WeatherDataVO metaOutDataObj = (WeatherDataVO) pSession.get(WeatherDataVO.class, swtObj);
        	
       	//Update the required column values.
       	
       	metaOutDataObj.setMNET(Double.parseDouble(contentInfo.get("MNET")));
        metaOutDataObj.setSLAT(Double.parseDouble(contentInfo.get("SLAT")));
        metaOutDataObj.setSLON(Double.parseDouble(contentInfo.get("SLON")));
        metaOutDataObj.setSELV(Double.parseDouble(contentInfo.get("SELV")));
        metaOutDataObj.setTMPF(Double.parseDouble(contentInfo.get("TMPF")));
        metaOutDataObj.setSKNT(Double.parseDouble(contentInfo.get("SKNT")));
        metaOutDataObj.setDRCT(Double.parseDouble(contentInfo.get("DRCT")));
        metaOutDataObj.setGUST(Double.parseDouble(contentInfo.get("GUST")));
        metaOutDataObj.setPMSL(Double.parseDouble(contentInfo.get("PMSL")));
        metaOutDataObj.setALTI(Double.parseDouble(contentInfo.get("ALTI")));
        metaOutDataObj.setDWPF(Double.parseDouble(contentInfo.get("DWPF")));
        metaOutDataObj.setRELH(Double.parseDouble(contentInfo.get("RELH")));
        metaOutDataObj.setWTHR(Double.parseDouble(contentInfo.get("WTHR")));
        metaOutDataObj.setP24I(Double.parseDouble(contentInfo.get("P24I")));
           
        metaOutDataObj.setLAST_UPDATE_DATE(new Date());
               
        pSession.update(metaOutDataObj);
        txn.commit();
     }
}
