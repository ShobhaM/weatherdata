package com.sjsu.cmpe226.mesonet.hibernate.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Session;

import com.sjsu.cmpe226.mesonet.constants.WeatherDataConstants;
import com.sjsu.cmpe226.mesonet.util.HibernateUtil;
import com.sjsu.cmpe226.mesonet.vo.WeatherMetaDataVO;

/**
 * @author Bhargav
 */
public class WeatherMetaDataDao {

    public synchronized void saveDataHeader(HashMap<String,String> metaInfo,Integer counter){
        
        Session session = HibernateUtil.getMetaSessionFactory().openSession();
        
        session.beginTransaction();
        WeatherMetaDataVO metaDataObj = new WeatherMetaDataVO();
        metaDataObj.setId(counter);
        metaDataObj.setPrimary_id(metaInfo.get("primary_id"));
        metaDataObj.setSecondary_id(metaInfo.get("secondary_id"));
        metaDataObj.setStation_name(metaInfo.get("station_name"));
        metaDataObj.setState(metaInfo.get("state"));
        metaDataObj.setCountry(metaInfo.get("country"));
        metaDataObj.setINSERT_DATE(new Date());
        metaDataObj.setLAST_UPDATE_DATE(new Date());
        
        session.save(metaDataObj);
        session.getTransaction().commit();
        HibernateUtil.shutdownHeader();
    }

}
