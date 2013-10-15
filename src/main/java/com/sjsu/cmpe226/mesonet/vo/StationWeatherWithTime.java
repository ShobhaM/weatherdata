package com.sjsu.cmpe226.mesonet.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * The separate POJO class to handle the Composite Key of Station ID and 
 * Create_Time. This class object would then be added to the POJO class 
 * of the WeatherDataVO.
 * @author Krish, Jing
 *
 */
public class StationWeatherWithTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String STN;
	private Date YYMMDDHHMM;
	
	/**
	 * This method is overridden method to provide custom logic for validating 
	 * unique combination of Station_ID and Create_Time. 
	 * @param stationWeatherWithTimeObj
	 * @return
	 */
	public boolean equals(StationWeatherWithTime stationWeatherWithTimeObj){
		if((this.STN.equals(stationWeatherWithTimeObj.STN)) && 
				(this.YYMMDDHHMM.equals(stationWeatherWithTimeObj.YYMMDDHHMM))){
			return true;
		}else{
			return false;
		}
	}

	public String getSTN() {
		return STN;
	}

	public void setSTN(String sTN) {
		STN = sTN;
	}

	public Date getYYMMDDHHMM() {
		return YYMMDDHHMM;
	}

	public void setYYMMDDHHMM(Date yYMMDDHHMM) {
		YYMMDDHHMM = yYMMDDHHMM;
	}
	
	@Override
	public String toString(){
		return STN+":"+YYMMDDHHMM;
	}
}
