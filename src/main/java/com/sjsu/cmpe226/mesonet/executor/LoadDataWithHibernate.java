/**
 * 
 */
package com.sjsu.cmpe226.mesonet.executor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

import com.sjsu.cmpe226.mesonet.hibernate.dao.*;
import com.sjsu.cmpe226.mesonet.util.ReadFromFile;

/**
 * @author bhargav_sjsu
 * 
 */
public class LoadDataWithHibernate {

	private static Logger logger = Logger.getLogger(LoadDataWithHibernate.class
			.getName());
	private StringBuilder data = null;
	private DataInputStream in = null;
	private BufferedReader br = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Shobha update three
		// ReadFromFile readObj = new ReadFromFile();

		// StringBuilder fileData =
		// readObj.readFromFileMethod("/Users/bhargav_sjsu/Documents/weather_data/wd_meta/mesowest_csv.tbl");
		// Iterator itrFileData = fileData.iterator();
		// While(){

		// }
		// LoadDataWithHibernate obj = new LoadDataWithHibernate();
		//obj.readFromFileMethod("/Users/shobha/desktop/226/data/mesowest_csv.tbl");

		LoadDataWithHibernate obj2 = new LoadDataWithHibernate();
		obj2.readFromOutDataFileMethod("/Users/shobha/desktop/226/Project1/data/mesowest.out");

	}

	/**
	 * Method used to read data from file
	 * 
	 * @param data
	 */
	public StringBuilder readFromFileMethod(String fileName) {
		try {
			// Initialize the file input stream
			FileInputStream fstream = new FileInputStream(fileName);
			int count = 0;
			// Get DataInputStream object
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			WeatherMetaDataDao weatherMetaDao = new WeatherMetaDataDao();

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// append the content to StringBulder
				count++;

				if (count == 1)
					continue;

				HashMap<String, String> metaInfo = new HashMap<String, String>();
				String[] lineArray = strLine.split(",");
				if (lineArray.length == 18) {
					metaInfo.put("primary_id", lineArray[0]);
					metaInfo.put("secondary_id", lineArray[1]);
					metaInfo.put("station_name", lineArray[2]);
					metaInfo.put("state", lineArray[3]);
					metaInfo.put("country", lineArray[4]);
					metaInfo.put("latitude", lineArray[5]);
					metaInfo.put("longitude", lineArray[6]);
					metaInfo.put("elevation", lineArray[7]);
					metaInfo.put("mesowest_network_id", lineArray[8]);
					metaInfo.put("network_name", lineArray[9]);
					metaInfo.put("status", lineArray[10]);
					metaInfo.put("primary_provider_id", lineArray[11]);
					metaInfo.put("primary_provider", lineArray[12]);
					metaInfo.put("secondary_provider_id", lineArray[13]);
					metaInfo.put("secondary_provider", lineArray[14]);
					metaInfo.put("tertiary_provider_id", lineArray[15]);
					metaInfo.put("tertiary_provider", lineArray[16]);
					metaInfo.put("wims_id", lineArray[17]);
				}
				weatherMetaDao.saveDataHeader(metaInfo, count);
			}

			// Close the input stream
			in.close();

		} catch (Exception e) {
			logger.severe("Issue with reading file " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
		}
		// System.out.println("I am the one who is throwing " + data);
		return data;
	}

	public void readFromOutDataFileMethod(String filename) {
		try {
			FileInputStream fipstream = new FileInputStream(filename);
			System.out.println("Total size of the file to read "
					+ fipstream.available());

			int count = 0;
			in = new DataInputStream(fipstream);
			br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			WeatherDataDao weatherDataDao = new WeatherDataDao();

			while ((strLine = br.readLine()) != null) {
				count++;

				if (count <= 4)
					continue;

				HashMap<String, String> contentDataInfo = new HashMap<String, String>();
				
				
				Scanner sc = new Scanner(strLine);
				ArrayList<String> lineArray = new ArrayList<String>();
				
				while(sc.hasNext())
				{
					lineArray.add(sc.next());
				}
				
				
				System.out.println(lineArray.size());
				if (lineArray.size() == 16) {
					contentDataInfo.put("STN", lineArray.get(0));
					contentDataInfo.put("YYMMDD/HHMM", lineArray.get(1));
					contentDataInfo.put("MNET", lineArray.get(2));
					contentDataInfo.put("SLAT", lineArray.get(3));
					contentDataInfo.put("SLON", lineArray.get(4));
					contentDataInfo.put("SELV", lineArray.get(5));
					contentDataInfo.put("TMPF", lineArray.get(6));
					contentDataInfo.put("SKNT", lineArray.get(7));
					contentDataInfo.put("DRCT", lineArray.get(8));
					contentDataInfo.put("GUST", lineArray.get(9));
					contentDataInfo.put("PMSL", lineArray.get(10));
					contentDataInfo.put("ALTI", lineArray.get(11));
					contentDataInfo.put("DWPF", lineArray.get(12));
					contentDataInfo.put("RELH", lineArray.get(13));
					contentDataInfo.put("WTHR", lineArray.get(14));
					contentDataInfo.put("P24I", lineArray.get(15));
				}
				System.out.println("completed forming hashmap successfully");
				weatherDataDao.saveDataContent(contentDataInfo, count);
			}

			in.close();

		} catch (Exception e) {
			logger.severe("Issue with reading file " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.severe("Issue with reading file " + e.getMessage());
				}
			}
		}
	}

}
