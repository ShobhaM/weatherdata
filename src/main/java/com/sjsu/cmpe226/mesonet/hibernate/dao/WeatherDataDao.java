package com.sjsu.cmpe226.mesonet.hibernate.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Session;

import com.sjsu.cmpe226.mesonet.util.HibernateUtil;
import com.sjsu.cmpe226.mesonet.vo.WeatherDataVO;
//import com.sjsu.cmpe226.mesonet.vo.WeatherMetaDataVO;

/**
 * @author Bhargav
 */
public class WeatherDataDao {

    public synchronized void saveDataContent(HashMap<String,String> contentInfo, Integer counter) {
    	
    	 Session session = HibernateUtil.getDataSessionFactory().openSession();
         System.out.println("inside saveDataContent");
    	 
         session.beginTransaction();
         WeatherDataVO metaOutDataObj = new WeatherDataVO();
         metaOutDataObj.setId(counter);
         metaOutDataObj.setSTN(contentInfo.get("STN"));
         
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/hhmm");
         Date dt = null;
		try {
			dt = df.parse(contentInfo.get("YYMMDD/HHMM"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         metaOutDataObj.setYYMMDDHHMM(dt);
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
         session.getTransaction().commit();
         HibernateUtil.shutdownData();
    }
    
  
}
