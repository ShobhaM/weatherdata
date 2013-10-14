package com.sjsu.cmpe226.mesonet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import com.sjsu.cmpe226.mesonet.vo.WeatherDataVO;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
public class WeatherDataShardSelectionStrategy implements ShardSelectionStrategy 
{
    public ShardId selectShardIdForNewObject(Object obj) {
       if (obj instanceof WeatherDataVO) {
            int shardId = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            
            try
            {
            	Date date = (Date) formatter.parse("27-09-2013");
                if(((WeatherDataVO)obj).getYYMMDDHHMM().before(date)) {
                	shardId = 0;
                }
                else {
                	shardId = 1;
                }         	
            } catch (ParseException e) {
        		e.printStackTrace();
        	} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

            return new ShardId(shardId);
        }
        throw new IllegalArgumentException();
    }
}
